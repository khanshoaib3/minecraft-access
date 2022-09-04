package com.github.khanshoaib3.minecraft_access.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.jetbrains.annotations.Nullable;

/**
 * Contains some functions related to the player entity
 */
@Environment(EnvType.CLIENT)
public class ClientPlayerEntityUtils {
    /**
     * Get the vertical direction of the player.
     * @return the vertical direction. -999 on error.
     */
    public static int getVerticalFacingDirection() {
        if (MinecraftClient.getInstance() == null) return -999;
        if (MinecraftClient.getInstance().player == null) return -999;

        return (int) MinecraftClient.getInstance().player.getRotationClient().x;
    }

    /**
     * Get the vertical direction in words.
     * @return the vertical direction in words. null on error.
     */
    public static @Nullable String getVerticalFacingDirectionInWords() {
        if (MinecraftClient.getInstance() == null) return null;
        if (MinecraftClient.getInstance().player == null) return null;

        int angle = getVerticalFacingDirection();
        if (angle == -999) return null;

        String angleInWords = null;

        if (angle >= -2 && angle <= 2) angleInWords = "Straight";
        else if (angle <= -88 && angle >= -90) angleInWords = "Up";
        else if (angle >= 88 && angle <= 90) angleInWords = "Down";

        return angleInWords; //TODO use i18n instead
    }

    /**
     * Get the horizontal direction of the player.
     * @return the horizontal direction. -999 on error.
     */
    public static int getHorizontalFacingDirection() {
        if (MinecraftClient.getInstance() == null) return -999;
        if (MinecraftClient.getInstance().player == null) return -999;

        int angle = (int) MinecraftClient.getInstance().player.getRotationClient().y;

        while (angle >= 360) angle -= 360;
        while (angle <= -360) angle += 360;

        return angle;
    }

    /**
     * Get the horizontal direction in words.
     * @param precise whether to only say the direction when in range or not
     * @return the horizontal direction in words. null on error.
     */
    public static @Nullable String getHorizontalFacingDirectionInWords(boolean precise) {
        if (MinecraftClient.getInstance() == null) return null;
        if (MinecraftClient.getInstance().player == null) return null;

        int angle = getHorizontalFacingDirection();
        if (angle == -999) return null;

        String angleInWords = MinecraftClient.getInstance().player.getHorizontalFacing().asString();

        if ((angle >= -140 && angle <= -130) || (angle >= 220 && angle <= 230)) {
            angleInWords = "North East";
        } else if ((angle >= -50 && angle <= -40) || (angle >= 310 && angle <= 320)) {
            angleInWords = "South East";
        } else if ((angle >= 40 && angle <= 50) || (angle >= -320 && angle <= -310)) {
            angleInWords = "South West";
        } else if ((angle >= 130 && angle <= 140) || (angle >= -230 && angle <= -220)) {
            angleInWords = "North West";
        } else if (precise) {
            if ((angle >= -185 && angle <= -175) || (angle >= 175 && angle <= 185)) {
                angleInWords = "North";
            } else if ((angle >= -95 && angle <= -85) || (angle >= 265 && angle <= 275)) {
                angleInWords = "East";
            } else if ((angle >= -5 && angle <= 5)) {
                angleInWords = "South";
            } else if ((angle >= 85 && angle <= 95) || (angle >= -275 && angle <= -265)) {
                angleInWords = "West";
            } else {
                angleInWords = null;
            }
        }

        return angleInWords; //TODO use i18n instead
    }
}
