package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIEntitiesConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.TimeUtils;
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

/**
 * Scans the area for entities, groups them and plays a sound at their location.
 */
public class POIEntities {

    public static TreeMap<Double, Entity> passiveEntity = new TreeMap<>();
    public static TreeMap<Double, Entity> hostileEntity = new TreeMap<>();

    private int range;
    private boolean playSound;
    private float volume;
    private TimeUtils.Interval interval;

    public POIEntities() {
        loadConfigurations();
    }

    public void update() {
        if (interval != null && !interval.isReady()) return;

        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();

            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.currentScreen != null) return; //Prevent running if any screen is opened

            loadConfigurations();

            passiveEntity = new TreeMap<>();
            hostileEntity = new TreeMap<>();

            for (Entity i : minecraftClient.world.getEntities()) {
                if (!(i instanceof MobEntity || i instanceof ItemEntity || i instanceof EyeOfEnderEntity || (i instanceof PlayerEntity && i != minecraftClient.player)))
                    continue;

                BlockPos blockPos = i.getBlockPos();

                Vec3d entityVec3d = new Vec3d(blockPos.getX(), blockPos.getY(), blockPos.getZ());
                Vec3d playerVec3d = new Vec3d(minecraftClient.player.getBlockPos().getX(), minecraftClient.player.getBlockPos().getY(),
                        minecraftClient.player.getBlockPos().getZ());
                Double distance = entityVec3d.distanceTo(playerVec3d);

                if (distance > range) continue;

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

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void playSoundAtBlockPos(MinecraftClient minecraftClient, BlockPos blockPos, SoundEvent soundEvent, float pitch) {
        if (minecraftClient.player == null) return;
        if (minecraftClient.world == null) return;
        if (!playSound || !(volume > 0f)) return;

        MainClass.infoLog("{POIEntity} Playing sound at x:%d y:%d z%d".formatted(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        minecraftClient.world.playSound(minecraftClient.player, blockPos, soundEvent, SoundCategory.BLOCKS, volume, pitch);
    }

    /**
     * Loads the configs from config.json
     */
    private void loadConfigurations() {
        POIEntitiesConfigMap map = MainClass.config.getConfigMap().getPoiConfigMap().getEntitiesConfigMap();
        this.range = map.getRange();
        this.playSound = map.isPlaySound();
        this.volume = map.getVolume();
        this.interval = TimeUtils.Interval.inMilliseconds(map.getDelay(), this.interval);
    }
}