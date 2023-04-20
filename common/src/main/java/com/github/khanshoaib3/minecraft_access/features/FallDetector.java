package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.FallDetectorConfigMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.BlockPos;

import java.time.Clock;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FallDetector {
    private static final FallDetector instance;
    private final Clock clock;
    private long previousTimeInMillis;
    MinecraftClient minecraftClient;
    private int count;

    private boolean enabled;
    private int range;
    private int depth;
//    private boolean playSound;
    private float volume;
    private int delayInMilliseconds;

    static {
        try {
            instance = new FallDetector();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating FallDetector instance");
        }
    }

    private FallDetector() {
        clock = Clock.systemDefaultZone();
        minecraftClient = MinecraftClient.getInstance();
        previousTimeInMillis = clock.millis();

        loadConfigurations();
    }

    public static synchronized FallDetector getInstance() {
        return instance;
    }

    public void update() {
        try {
            loadConfigurations();

            if (!enabled) return;

            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.currentScreen != null) return;
            if (minecraftClient.player.isFallFlying()) return;
            if (minecraftClient.player.isSubmergedInWater()) return;
            if (minecraftClient.player.isSwimming()) return;
            if (minecraftClient.player.isInSwimmingPose()) return;
            if (!minecraftClient.player.isOnGround()) return;

            long currentTimeInMillis = clock.millis();
            if (currentTimeInMillis - previousTimeInMillis < delayInMilliseconds) return;
            previousTimeInMillis = currentTimeInMillis;

            MainClass.infoLog("Searching for fall in nearby area...");
            SearchNearbyPositions();
            MainClass.infoLog("Searching ended.");
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in fall detector.");
            e.printStackTrace();
        }
    }

    private void SearchNearbyPositions() {
        if (minecraftClient.player == null) return;
        BlockPos center = minecraftClient.player.getBlockPos();

        Queue<BlockPos> toSearch = new LinkedList<>();
        HashSet<BlockPos> searched = new HashSet<>();
        int[] dirX = {-1, 0, 1, 0};
        int[] dirZ = {0, 1, 0, -1};
        count = 0;

        toSearch.add(center);
        searched.add(center);

        while (!toSearch.isEmpty()) {
            BlockPos item = toSearch.poll();
            checkForFall(item);

            for (int i = 0; i < 4; i++) {
                BlockPos dir = new BlockPos(item.getX() + dirX[i], item.getY(), item.getZ() + dirZ[i]);

                if (isValid(dir, center, searched)) {
                    toSearch.add(dir);
                    searched.add(dir);
                }
            }
        }
    }

    private boolean isValid(BlockPos dir, BlockPos center, HashSet<BlockPos> searched) {
        if (Math.abs(dir.getX() - center.getX()) > range)
            return false;

        if (Math.abs(dir.getZ() - center.getZ()) > range)
            return false;

        if (searched.contains(dir))
            return false;

        return true;
    }

    private void checkForFall(BlockPos toCheck) {
        MainClass.infoLog("%d) Checking fall for x:%d y:%d z:%d".formatted(++count, toCheck.getX(), toCheck.getY(), toCheck.getZ()));

        if (minecraftClient.world == null) return;
        if (!(minecraftClient.world.getBlockState(toCheck).isAir())) return;

        if (getDepth(toCheck, depth) < depth) return;

        minecraftClient.world.playSoundAtBlockCenter(toCheck, SoundEvents.BLOCK_ANVIL_HIT, SoundCategory.BLOCKS, volume, 1f, true);
    }

    private int getDepth(BlockPos blockPos, int maxDepth) {
        if (maxDepth <= 0)
            return 0;

        if (minecraftClient.world == null) return 0;
        if (!(minecraftClient.world.getBlockState(blockPos).isAir())) return 0;

        return 1 + getDepth(blockPos.down(), --maxDepth);
    }

    private void loadConfigurations() {
        FallDetectorConfigMap fallDetectorConfigMap = MainClass.config.getConfigMap().getFallDetectorConfigMap();
        enabled = fallDetectorConfigMap.isEnabled();
        range = fallDetectorConfigMap.getRange();
        depth = fallDetectorConfigMap.getDepth();
//        playSound = fallDetectorConfigMap.isPlaySound();
        volume = fallDetectorConfigMap.getVolume();
        delayInMilliseconds = fallDetectorConfigMap.getDelay();
    }
}
