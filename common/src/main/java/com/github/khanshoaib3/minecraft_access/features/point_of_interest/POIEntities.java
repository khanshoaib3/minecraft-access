package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.config.config_maps.POIEntitiesConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
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
import net.minecraft.entity.vehicle.VehicleEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.util.List;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Scans the area for entities, groups them and plays a sound at their location.
 */
@Slf4j
public class POIEntities {
    /**
     * Entity types to be scanned for.
     */
    private static final Set<Predicate<Entity>> INTERESTED_ENTITY_TYPES = Set.of(
            // LivingEntity = MobEntity + PlayerEntity + ArmorStandEntity
            e -> e instanceof MobEntity,
            e -> e instanceof PlayerEntity,
            // For notifying dropped items
            e -> e instanceof ItemEntity,
            // For auto-locking eye of ender
            e -> e instanceof EyeOfEnderEntity,
            // boat + mine cart
            e -> e instanceof VehicleEntity
    );
    private static final Predicate<Entity> IS_INTERESTED_ENTITY_TYPE = INTERESTED_ENTITY_TYPES.stream().reduce(Predicate::or).get();

    private TreeMap<Double, Entity> passiveEntity = new TreeMap<>();
    private TreeMap<Double, Entity> hostileEntity = new TreeMap<>();
    private TreeMap<Double, Entity> markedEntities = new TreeMap<>();
    private TreeMap<Double, Entity> vehicleEntities = new TreeMap<>();

    private int range;
    private boolean playSound;
    private float volume;
    private Interval interval;
    private boolean enabled;

    private static final POIEntities instance;
    private boolean onPOIMarkingNow = false;
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

    public void update(boolean onMarking, Entity markedEntity) {
        this.onPOIMarkingNow = onMarking;
        if (onPOIMarkingNow) setMarkedEntity(markedEntity);
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
            vehicleEntities = new TreeMap<>();

            log.debug("POIEntities started...");

            for (Entity i : minecraftClient.world.getEntities()) {
                // Only scan interested entity types
                if (!IS_INTERESTED_ENTITY_TYPE.test(i)) continue;
                // Exclude player self
                if (i == minecraftClient.player) continue;

                double distance = minecraftClient.player.distanceTo(i);
                if (distance > range) continue;

                BlockPos blockPos = i.getBlockPos();

                if (this.markedEntity.test(i)) {
                    markedEntities.put(distance, i);
                    if (i instanceof HostileEntity) {
                        this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 2f);
                    } else {
                        this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                    }
                }

                if (onPOIMarkingNow && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled()) {
                    log.debug("POIEntities end early by POI marking feature.");
                    return;
                }

                if (i instanceof PassiveEntity) {
                    passiveEntity.put(distance, i);
                    this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                } else if (i instanceof HostileEntity) {
                    hostileEntity.put(distance, i);
                    this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 2f);
                } else if (i instanceof WaterCreatureEntity) {
                    passiveEntity.put(distance, i);
                    this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                } else if (i instanceof ItemEntity itemEntity && itemEntity.isOnGround()) {
                    this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_METAL_PRESSURE_PLATE_CLICK_ON, 2f);
                } else if (i instanceof PlayerEntity) {
                    passiveEntity.put(distance, i);
                    this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                } else if (i instanceof VehicleEntity) {
                    vehicleEntities.put(distance, i);
                    this.playSoundAtBlockPos(blockPos, SoundEvents.BLOCK_NOTE_BLOCK_BELL.value(), 0f);
                }
            }
            log.debug("POIEntities end.");

        } catch (Exception e) {
            log.error("An error occurred while executing POIEntities", e);
        }
    }

    private void playSoundAtBlockPos(BlockPos blockPos, SoundEvent soundEvent, float pitch) {
        if (!playSound || volume == 0f) return;
        log.debug("Play sound at [x:%d y:%d z%d]".formatted(blockPos.getX(), blockPos.getY(), blockPos.getZ()));
        WorldUtils.playSoundAtPosition(soundEvent, volume, pitch, blockPos.toCenterPos());
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

    private void setMarkedEntity(Entity entity) {
        if (entity == null) {
            this.markedEntity = e -> false;
        } else {
            // Mark an entity = mark the type of entity (class type)
            Class<? extends Entity> clazz = entity.getClass();
            this.markedEntity = clazz::isInstance;
        }
    }

    public List<TreeMap<Double, Entity>> getLockingCandidates() {
        if (onPOIMarkingNow) {
            if (POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled()) {
                return List.of(markedEntities);
            } else {
                return List.of(markedEntities, hostileEntity, passiveEntity, vehicleEntities);
            }
        } else {
            return List.of(hostileEntity, passiveEntity, vehicleEntities);
        }
    }
}