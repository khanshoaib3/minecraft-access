package com.github.khanshoaib3.minecraft_access.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

public class PlayerUtils {
    public static void lookAt(Vec3d position) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;
        client.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, position);
    }

    public static void lookAt(Entity entity) {
        double aboutEntityHeadHeight = entity.getY() + entity.getHeight() - 0.25;
        Vec3d aboutEntityHeadPosition = new Vec3d(entity.getX(), aboutEntityHeadHeight, entity.getZ());
        lookAt(aboutEntityHeadPosition);
    }
}
