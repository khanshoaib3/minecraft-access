package com.github.khanshoaib3.minecraft_access.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;

/**
 * Contains some functions related to the player entity
 */
@Environment(EnvType.CLIENT)
public class ClientPlayerEntityUtils {
    public static int getExperienceLevel() {
        if (MinecraftClient.getInstance() == null) return -999;
        if (MinecraftClient.getInstance().player == null) return -999;

        return MinecraftClient.getInstance().player.experienceLevel;
    }

    public static int getExperienceProgress() {
        if (MinecraftClient.getInstance() == null) return -999;
        if (MinecraftClient.getInstance().player == null) return -999;

        return (int) (MinecraftClient.getInstance().player.experienceProgress * 100);
    }
}
