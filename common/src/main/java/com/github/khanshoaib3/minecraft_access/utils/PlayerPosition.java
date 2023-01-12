package com.github.khanshoaib3.minecraft_access.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.Vec3d;

public class PlayerPosition {
    private final PlayerEntity player;

    public PlayerPosition(MinecraftClient client) {
        this.player = client.player;
    }

    public static String getNarratableNumber(double d) {
        return d >= 0 ? String.valueOf(d) : I18n.translate("minecraft_access.other.negative", -d);
    }

    public double getX() {
        assert player != null;
        Vec3d pos = player.getPos();

        String tempPosX = pos.x + "";
        tempPosX = tempPosX.substring(0, tempPosX.indexOf(".") + 2);

        return Double.parseDouble(tempPosX);
    }

    public double getY() {
        assert player != null;
        Vec3d pos = player.getPos();

        String tempPosY;
        tempPosY = pos.y + "";
        tempPosY = tempPosY.substring(0, tempPosY.indexOf(".") + 2);

        return Double.parseDouble(tempPosY);
    }

    public double getZ() {
        assert player != null;
        Vec3d pos = player.getPos();

        String tempPosZ;
        tempPosZ = pos.z + "";
        tempPosZ = tempPosZ.substring(0, tempPosZ.indexOf(".") + 2);

        return Double.parseDouble(tempPosZ);
    }

    public String getNarratableXPos() {
        return getNarratableNumber(getX()) + "x";
    }

    public String getNarratableYPos() {
        return getNarratableNumber(getY()) + "y";
    }

    public String getNarratableZPos() {
        return getNarratableNumber(getZ()) + "z";
    }

    public int getVerticalFacingDirection() {
        assert player != null;
        return (int) player.getRotationClient().x;
    }

    public int getHorizontalFacingDirectionInDegrees() {
        assert player != null;
        int angle = (int) player.getRotationClient().y;

        while (angle >= 360) angle -= 360;
        while (angle <= -360) angle += 360;

        return angle;
    }

    public String getHorizontalFacingDirectionInCardinal() {
        assert player != null;

        int angle = getHorizontalFacingDirectionInDegrees();
        String direction;

        if ((angle >= -150 && angle <= -120) || (angle >= 210 && angle <= 240)) {
            // Looking North East
            direction = "north_east";
        } else if ((angle >= -60 && angle <= -30) || (angle >= 300 && angle <= 330)) {
            // Looking South East
            direction = "south_east";
        } else if ((angle >= 30 && angle <= 60) || (angle >= -330 && angle <= -300)) {
            // Looking South West
            direction = "south_west";
        } else if ((angle >= 120 && angle <= 150) || (angle >= -240 && angle <= -210)) {
            // Looking North West
            direction = "north_west";
        } else {
            direction = player.getHorizontalFacing().asString().toLowerCase();
        }

        return I18n.translate("minecraft_access.direction.horizontal_angle_" + direction);
    }
}
