package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.config.config_maps.POIEntitiesConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.mob.WaterCreatureEntity;
import net.minecraft.entity.passive.PassiveEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Scans the area for entities, groups them and plays a sound at their location.
 */
@Slf4j
public class POIEntities {

    public static TreeMap<Double, Entity> passiveEntity = new TreeMap<>();
    public static TreeMap<Double, Entity> hostileEntity = new TreeMap<>();
    public static TreeMap<Double, Entity> markedEntities = new TreeMap<>();

    private int range;
    private boolean playSound;
    private float volume;
    private Interval interval;
    private boolean enabled;

    private static final POIEntities instance;
    @Setter
    private boolean marking = false;
    private Predicate<Entity> markedEntity = e -> false;

    static {
        instance = new POIEntities();
    }

    public static POIEntities getInstance() {
        return instance;
    }

    private POIEntities() {
        loadConfigurations();
    }

    public void update() {
        loadConfigurations();

        if (!enabled) return;
        if (interval != null && !interval.isReady()) return;

        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();

            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.currentScreen != null) return; //Prevent running if any screen is opened

            passiveEntity = new TreeMap<>();
            hostileEntity = new TreeMap<>();
            markedEntities = new TreeMap<>();

           log.debug("POIEntities started...");

            for (Entity i : minecraftClient.world.getEntities()) {
                if (!(i instanceof MobEntity || i instanceof ItemEntity || i instanceof EyeOfEnderEntity || (i instanceof PlayerEntity && i != minecraftClient.player)))
                    continue;

                BlockPos blockPos = i.getBlockPos();

                Vec3d entityVec3d = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                Vec3d playerVec3d = new Vec3d(minecraftClient.player.getBlockPos().getX(), minecraftClient.player.getBlockPos().getY(),
                        minecraftClient.player.getBlockPos().getZ());
                Double distance = entityVec3d.distanceTo(playerVec3d);

                if (distance > range) continue;

                if (markedEntity.test(i)) {
                    markedEntities.put(distance, i);
                    if (i instanceof HostileEntity) {
                        this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 2f);
                    } else {
                        this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                    }
                }

                if (marking && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled()) {
                   log.debug("POIEntities end early by POI marking feature.");
                    return;
                }

                if (i instanceof PassiveEntity) {
                    passiveEntity.put(distance, i);
                    this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                } else if (i instanceof HostileEntity) {
                    hostileEntity.put(distance, i);
                    this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 2f);
                } else if (i instanceof WaterCreatureEntity) {
                    passiveEntity.put(distance, i);
                    this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                } else if (i instanceof ItemEntity itemEntity && itemEntity.isOnGround()) {
                    this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 2f);
                } else if (i instanceof PlayerEntity) {
                    passiveEntity.put(distance, i);
                    this.playSoundAtBlockPos(minecraftClient, blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                }
            }
           log.debug("POIEntities end.");

        } catch (Exception e) {
            log.error("An error occurred while executing POIEntities", e);
        }
    }

    private void playSoundAtBlockPos(MinecraftClient minecraftClient, BlockPos blockPos, SoundEvent soundEvent, float pitch) {
        if (minecraftClient.player == null) return;
        if (minecraftClient.world == null) return;
        if (!playSound || !(volume > 0f)) return;

       log.debug("{POIEntity} Playing sound at x:%d y:%d z%d".formatted(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        minecraftClient.world.playSound(minecraftClient.player, blockPos, soundEvent, SoundCategory.BLOCKS, volume, pitch);
    }

    /**
     * Loads the configs from config.json
     */
    private void loadConfigurations() {
        POIEntitiesConfigMap map = POIEntitiesConfigMap.getInstance();
        this.enabled = map.isEnabled();
        this.range = map.getRange();
        this.playSound = map.isPlaySound();
        this.volume = map.getVolume();
        this.interval = Interval.inMilliseconds(map.getDelay(), this.interval);
    }

    public void setMarkedEntity(Entity entity) {
        if (entity == null) {
            this.marking = false;
            this.markedEntity = e -> false;
        } else {
            this.marking = true;
            Class<? extends Entity> clazz = entity.getClass();
            this.markedEntity = clazz::isInstance;
        }
    }
}