package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

import java.time.Clock;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;

public class FallDetector {
    private static final FallDetector instance;
    private final int delayInMilliseconds;
    private final Clock clock;
    private long previousTimeInMillis;
    MinecraftClient minecraftClient;
    private int count;

    static {
        try {
            instance = new FallDetector();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating FallDetector instance");
        }
    }

    private FallDetector() {
        delayInMilliseconds = 2500;
        clock = Clock.systemDefaultZone();
        minecraftClient = MinecraftClient.getInstance();
        previousTimeInMillis = clock.millis();
    }

    public static synchronized FallDetector getInstance() {
        return instance;
    }

    public void update() {
        try {
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
        int limit = 2;
        count = 0;

        toSearch.add(center);
        searched.add(center);

        while (!toSearch.isEmpty()) {
            BlockPos item = toSearch.poll();
            checkForFall(item);

            for (int i = 0; i < 4; i++) {
                BlockPos dir = new BlockPos(item.getX() + dirX[i], item.getY(), item.getZ() + dirZ[i]);

                if (isValid(dir, center, searched, limit)) {
                    toSearch.add(dir);
                    searched.add(dir);
                }
            }
        }
    }

    private boolean isValid(BlockPos dir, BlockPos center, HashSet<BlockPos> searched, int range) {
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
    }
}
