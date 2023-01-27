package com.github.khanshoaib3.minecraft_access.utils;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
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

        if (angle >= -2 && angle <= 2) angleInWords = I18n.translate("minecraft_access.direction.vertical_angle_straight");
        else if (angle <= -88 && angle >= -90) angleInWords = I18n.translate("minecraft_access.direction.vertical_angle_up");
        else if (angle >= 88 && angle <= 90) angleInWords = I18n.translate("minecraft_access.direction.vertical_angle_down");

        return angleInWords;
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

    // FIXME duplicate from new PlayerPositionUtils(minecraftClient).getHorizontalFacingDirectionInCardinal()
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
            angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_north_east");
        } else if ((angle >= -50 && angle <= -40) || (angle >= 310 && angle <= 320)) {
            angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_south_east");
        } else if ((angle >= 40 && angle <= 50) || (angle >= -320 && angle <= -310)) {
            angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_south_west");
        } else if ((angle >= 130 && angle <= 140) || (angle >= -230 && angle <= -220)) {
            angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_north_west");
        } else if (precise) {
            if ((angle >= -185 && angle <= -175) || (angle >= 175 && angle <= 185)) {
                angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_north");
            } else if ((angle >= -95 && angle <= -85) || (angle >= 265 && angle <= 275)) {
                angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_east");
            } else if ((angle >= -5 && angle <= 5)) {
                angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_south");
            } else if ((angle >= 85 && angle <= 95) || (angle >= -275 && angle <= -265)) {
                angleInWords = I18n.translate("minecraft_access.direction.horizontal_angle_west");
            } else {
                angleInWords = null;
            }
        }

        return angleInWords;
    }

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
