package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.Config;
import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.NarrationUtils;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.Registries;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.Arrays;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This feature reads the name of the targeted block or entity.<br>
 * It also gives feedback when a block is powered by a redstone signal or when a door is open similar cases.
 */
@Slf4j
public class ReadCrosshair {
    private double rayCastDistance = 6.0;
    private static ReadCrosshair instance;
    private Config.ReadCrosshair config;
    private String previousQuery = "";
    private Vec3d previousSoundPos = Vec3d.ZERO;
    private Interval repeatSpeakingInterval;
    private boolean partialSpeakingBlock;
    private boolean partialSpeakingEntity;

    private ReadCrosshair() {
        loadConfig();
    }

    public static ReadCrosshair getInstance() {
        if (Objects.isNull(instance)) {
            instance = new ReadCrosshair();
        }
        return instance;
    }

    public String getPreviousQuery() {
        if (repeatSpeakingInterval.isReady()) {
            this.previousQuery = "";
        }
        return this.previousQuery;
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            loadConfig();
            if (!config.enabled) return;

            this.rayCastDistance = PlayerUtils.getInteractionRange();
            HitResult hit = PlayerUtils.crosshairTarget(rayCastDistance);
            if (hit == null) return;
            checkForBlockAndEntityHit(hit);

        } catch (Exception e) {
            log.error("Error occurred in read block feature.", e);
        }
    }

    private void loadConfig() {
        config = Config.getInstance().readCrosshair;
        repeatSpeakingInterval = Interval.inMilliseconds(config.repeatSpeakingInterval, repeatSpeakingInterval);
        switch (config.partialSpeaking.targetMode) {
            case ALL -> {
                partialSpeakingBlock = true;
                partialSpeakingEntity = true;
            }
            case BLOCK -> {
                partialSpeakingBlock = true;
                partialSpeakingEntity = false;
            }
            case ENTITY -> {
                partialSpeakingBlock = false;
                partialSpeakingEntity = true;
            }
        }
    }

    private void checkForBlockAndEntityHit(HitResult blockHit) {
        switch (blockHit.getType()) {
            case MISS -> {
            }
            case BLOCK -> checkForBlocks((BlockHitResult) blockHit);
            case ENTITY -> checkForEntities((EntityHitResult) blockHit);
        }
    }

    private void checkForEntities(EntityHitResult hit) {
        try {
            Entity entity = hit.getEntity();

            if (config.partialSpeaking.enabled && partialSpeakingEntity) {
                if (checkIfPartialSpeakingFeatureDoesNotAllowsSpeakingThis(EntityType.getId(entity.getType()))) return;
            }

            String narration = NarrationUtils.narrateEntity(entity);
            speakIfFocusChanged(narration, narration, entity.getPos());
        } catch (Exception e) {
            log.error("Error occurred in ReadCrosshair, reading entity", e);
        }
    }

    /**
     * @param currentQuery for checking if focus is changed
     * @param toSpeak      text will be narrated (if focus has changed)
     */
    private void speakIfFocusChanged(String currentQuery, String toSpeak, Vec3d targetPosition) {
        boolean focusChanged = !getPreviousQuery().equalsIgnoreCase(currentQuery);
        if (focusChanged) {
            if (config.relativePositionSoundCue.enabled && !previousSoundPos.equals(targetPosition)) {
                WorldUtils.playRelativePositionSoundCue(targetPosition, rayCastDistance,
                        SoundEvents.BLOCK_NOTE_BLOCK_HARP, config.relativePositionSoundCue.minSoundVolume, config.relativePositionSoundCue.maxSoundVolume);
                this.previousSoundPos = targetPosition;
            }
            this.previousQuery = currentQuery;
            MainClass.speakWithNarrator(toSpeak, true);
        }
    }

    private void checkForBlocks(BlockHitResult hit) {
        String side = "";
        if (config.speakSide) {
            Direction d = hit.getSide();
            side = I18n.translate("minecraft_access.direction." + d.getName());
        }

        BlockPos blockPos = hit.getBlockPos();
        WorldUtils.BlockInfo blockInfo = WorldUtils.getBlockInfo(blockPos);
        // In Minecraft resource location format, for example, "oak_door" for Oak Door.
        // ref: https://minecraft.wiki/w/Java_Edition_data_values#Blocks
        Identifier blockId = Registries.BLOCK.getId(blockInfo.type());

        Pair<String, String> toSpeakAndCurrentQuery;
        if (config.partialSpeaking.enabled && partialSpeakingBlock && checkIfPartialSpeakingFeatureDoesNotAllowsSpeakingThis(blockId)) {
            toSpeakAndCurrentQuery = new Pair<>("", "");
        } else {
            toSpeakAndCurrentQuery = NarrationUtils.narrateBlockForContentChecking(blockPos, side);
        }

        String currentQuery = toSpeakAndCurrentQuery.getRight();
        String blockPosInString = blockPos.toString();
        // If "speakingConsecutiveBlocks" config is enabled, add position info to currentQuery,
        // so same blocks at different positions will be regard as different one then trigger the narrator.
        // Class name in production environment can be different
        if (!config.disableSpeakingConsecutiveBlocks) currentQuery += " " + blockPosInString;

        speakIfFocusChanged(currentQuery, toSpeakAndCurrentQuery.getLeft(), Vec3d.of(blockPos));
    }

    private boolean checkIfPartialSpeakingFeatureDoesNotAllowsSpeakingThis(Identifier id) {
        if (id == null) return false;
        String name = id.getPath();
        Predicate<String> p = config.partialSpeaking.fuzzy ? name::contains : name::equals;
        return config.partialSpeaking.whitelist ?
                Arrays.stream(config.partialSpeaking.targets).noneMatch(p) :
                Arrays.stream(config.partialSpeaking.targets).anyMatch(p);
    }
}
