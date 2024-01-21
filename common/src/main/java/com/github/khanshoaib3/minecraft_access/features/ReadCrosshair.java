package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.RCPartialSpeakingConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.RCRelativePositionSoundCueConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.ReadCrosshairConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.NarrationUtils;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.Identifier;
import net.minecraft.util.Pair;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;

/**
 * This feature reads the name of the targeted block or entity.<br>
 * It also gives feedback when a block is powered by a redstone signal or when a door is open similar cases.
 */
@Slf4j
public class ReadCrosshair {
    private static final double RAY_CAST_DISTANCE = 6.0;
    private static ReadCrosshair instance;
    private boolean enabled;
    private String previousQuery;
    private boolean speakSide;
    private boolean speakingConsecutiveBlocks;
    private Interval repeatSpeakingInterval;
    private boolean enablePartialSpeaking;
    private boolean partialSpeakingWhitelistMode;
    private boolean partialSpeakingFuzzyMode;
    private List<String> partialSpeakingTargets;
    private boolean partialSpeakingBlock;
    private boolean partialSpeakingEntity;
    private boolean enableRelativePositionSoundCue;
    private double minSoundVolume;
    private double maxSoundVolume;

    private ReadCrosshair() {
        previousQuery = "";
        loadConfigurations();
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

            loadConfigurations();
            if (!enabled) return;

            Entity entity = minecraftClient.getCameraEntity();
            if (entity == null) return;

            HitResult blockHit = minecraftClient.crosshairTarget;
            HitResult fluidHit = entity.raycast(RAY_CAST_DISTANCE, 0.0F, true);

            if (blockHit == null) return;

            boolean playerIsInFluid = PlayerUtils.isInFluid();
            boolean playerLooksAtFluid = checkForFluidHit(minecraftClient, fluidHit);

            if (playerIsInFluid && playerLooksAtFluid) return;

            checkForBlockAndEntityHit(blockHit);
        } catch (Exception e) {
            log.error("Error occurred in read block feature.", e);
        }
    }

    private void loadConfigurations() {
        // It is best to get the config map from instance of Config class rather than directly from
        // the ReadCrosshairConfigMap class because in the case of an error in the config.json,
        // while it does get reset to default but the mod crashes as well. So to avoid the mod from crashing,
        // use the instance of Config class to get instances of other config maps.
        ReadCrosshairConfigMap rcMap = ReadCrosshairConfigMap.getInstance();
        RCPartialSpeakingConfigMap rcpMap = RCPartialSpeakingConfigMap.getInstance();
        RCRelativePositionSoundCueConfigMap rcrMap = RCRelativePositionSoundCueConfigMap.getInstance();

        this.enabled = rcMap.isEnabled();
        this.speakSide = rcMap.isSpeakSide();
        // affirmation for easier use
        this.speakingConsecutiveBlocks = !rcMap.isDisableSpeakingConsecutiveBlocks();
        long interval = rcMap.getRepeatSpeakingInterval();
        this.repeatSpeakingInterval = Interval.inMilliseconds(interval, this.repeatSpeakingInterval);
        this.enableRelativePositionSoundCue = rcrMap.isEnabled();
        this.minSoundVolume = rcrMap.getMinSoundVolume();
        this.maxSoundVolume = rcrMap.getMaxSoundVolume();

        this.enablePartialSpeaking = rcpMap.isEnabled();
        this.partialSpeakingFuzzyMode = rcpMap.isPartialSpeakingFuzzyMode();
        this.partialSpeakingWhitelistMode = rcpMap.isPartialSpeakingWhitelistMode();
        this.partialSpeakingTargets = rcpMap.getPartialSpeakingTargets();
        switch (rcpMap.getPartialSpeakingTargetMode()) {
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

            if (enablePartialSpeaking && partialSpeakingEntity) {
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
            if (this.enableRelativePositionSoundCue) {
                WorldUtils.playRelativePositionSoundCue(targetPosition, RAY_CAST_DISTANCE,
                        SoundEvents.BLOCK_NOTE_BLOCK_HARP, this.minSoundVolume, this.maxSoundVolume);
            }
            this.previousQuery = currentQuery;
            MainClass.speakWithNarrator(toSpeak, true);
        }
    }

    private void checkForBlocks(BlockHitResult hit) {
        String side = "";
        if (this.speakSide) {
            Direction d = hit.getSide();
            side = I18n.translate("minecraft_access.direction." + d.getName());
        }

        BlockPos blockPos = hit.getBlockPos();
        WorldUtils.BlockInfo blockInfo = WorldUtils.getBlockInfo(blockPos).orElseThrow();
        // In Minecraft resource location format, for example, "oak_door" for Oak Door.
        // ref: https://minecraft.wiki/w/Java_Edition_data_values#Blocks
        Identifier blockId = Registries.BLOCK.getId(blockInfo.type());

        Pair<String, String> toSpeakAndCurrentQuery;
        if (enablePartialSpeaking && partialSpeakingBlock && checkIfPartialSpeakingFeatureDoesNotAllowsSpeakingThis(blockId)) {
            toSpeakAndCurrentQuery = new Pair<>("", "");
        } else {
            toSpeakAndCurrentQuery = NarrationUtils.narrateBlockForContentChecking(blockPos, side);
        }

        String currentQuery = toSpeakAndCurrentQuery.getRight();
        String blockPosInString = blockPos.toString();
        // If "speakingConsecutiveBlocks" config is enabled, add position info to currentQuery,
        // so same blocks at different positions will be regard as different one then trigger the narrator.
        // Class name in production environment can be different
        if (this.speakingConsecutiveBlocks) currentQuery += " " + blockPosInString;

        speakIfFocusChanged(currentQuery, toSpeakAndCurrentQuery.getLeft(), Vec3d.of(blockPos));
    }

    private boolean checkForFluidHit(MinecraftClient minecraftClient, HitResult fluidHit) {
        if (minecraftClient == null) return false;
        if (minecraftClient.world == null) return false;
        if (minecraftClient.currentScreen != null) return false;

        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) fluidHit).getBlockPos();
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);

            String name = getFluidName(fluidState.getRegistryEntry());
            if (name.equals("block.minecraft.empty")) return false;
            if (name.contains("block.minecraft."))
                name = name.replace("block.minecraft.", ""); // Remove `block.minecraft.` for unsupported languages

            String currentQuery = name + blockPos;

            int level = fluidState.getLevel();
            String levelString = "";
            if (level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString;

            speakIfFocusChanged(currentQuery, toSpeak, Vec3d.of(blockPos));
            return true;
        }
        return false;
    }

    /**
     * Gets the fluid name from registry entry
     *
     * @param fluid the fluid's registry entry
     * @return the fluid's name
     */
    public static String getFluidName(RegistryEntry<Fluid> fluid) {
        return I18n.translate(getFluidTranslationKey(fluid));
    }

    /**
     * Gets the fluid translation key from registry entry
     *
     * @param fluid the fluid's registry entry
     * @return the fluid's translation key
     */
    private static String getFluidTranslationKey(RegistryEntry<Fluid> fluid) {
        return fluid.getKeyOrValue().map(
                (fluidKey) -> "block." + fluidKey.getValue().getNamespace() + "." + fluidKey.getValue().getPath(),
                (fluidValue) -> "[unregistered " + fluidValue + "]" // For unregistered fluid
        );
    }

    private boolean checkIfPartialSpeakingFeatureDoesNotAllowsSpeakingThis(Identifier id) {
        if (id == null) return false;
        String name = id.getPath();
        Predicate<String> p = partialSpeakingFuzzyMode ? name::contains : name::equals;
        return partialSpeakingWhitelistMode ?
                partialSpeakingTargets.stream().noneMatch(p) :
                partialSpeakingTargets.stream().anyMatch(p);
    }
}
