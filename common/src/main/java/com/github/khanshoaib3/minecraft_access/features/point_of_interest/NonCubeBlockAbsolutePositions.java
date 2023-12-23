package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.google.common.collect.ImmutableSet;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;

import java.util.Map;

/**
 * The natural position of the block is generally considered to be the center of the block (x.5,y.5,z.5).
 * Some blocks do not occupy the entire cube space, and for those of them that can be interacted with (thus should be locked),
 * we manually calculate the locking position of these blocks (which are usually not the center of the block) by these methods.
 */
public class NonCubeBlockAbsolutePositions {
    public static Vec3d getTrapDoorAbsolutePosition(MinecraftClient client, Vec3d blockPos) {
        if (client.world == null) return blockPos;

        BlockState blockState = client.world.getBlockState(BlockPos.ofFloored(blockPos.x, blockPos.y, blockPos.z));
        ImmutableSet<Map.Entry<Property<?>, Comparable<?>>> entries = blockState.getEntries().entrySet();

        String half = "", facing = "", open = "";

        for (Map.Entry<Property<?>, Comparable<?>> i : entries) {

            if (i.getKey().getName().equalsIgnoreCase("half")) {
                half = i.getValue().toString();
            } else if (i.getKey().getName().equalsIgnoreCase("facing")) {
                facing = i.getValue().toString();
            } else if (i.getKey().getName().equalsIgnoreCase("open")) {
                open = i.getValue().toString();
            }

        }

        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        if (open.equalsIgnoreCase("true")) {
            if (facing.equalsIgnoreCase("north"))
                z += 0.4;
            else if (facing.equalsIgnoreCase("south"))
                z -= 0.4;
            else if (facing.equalsIgnoreCase("west"))
                x += 0.4;
            else if (facing.equalsIgnoreCase("east"))
                x -= 0.4;
        } else if (open.equalsIgnoreCase("false")) {
            if (half.equalsIgnoreCase("bottom"))
                y -= 0.4;
            else if (half.equalsIgnoreCase("top"))
                y += 0.4;
        }

        return new Vec3d(x, y, z);
    }

    public static Vec3d getLeversAbsolutePosition(MinecraftClient client, Vec3d blockPos) {
        if (client.world == null) return blockPos;

        BlockState blockState = client.world.getBlockState(BlockPos.ofFloored(blockPos.x, blockPos.y, blockPos.z));
        ImmutableSet<Map.Entry<Property<?>, Comparable<?>>> entries = blockState.getEntries().entrySet();

        String face = "", facing = "";

        for (Map.Entry<Property<?>, Comparable<?>> i : entries) {

            if (i.getKey().getName().equalsIgnoreCase("face")) {
                face = i.getValue().toString();

            } else if (i.getKey().getName().equalsIgnoreCase("facing")) {
                facing = i.getValue().toString();
            }

        }

        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        if (face.equalsIgnoreCase("floor")) {
            y -= 0.3;
        } else if (face.equalsIgnoreCase("ceiling")) {
            y += 0.3;
        } else if (face.equalsIgnoreCase("wall")) {
            if (facing.equalsIgnoreCase("north"))
                z += 0.3;
            else if (facing.equalsIgnoreCase("south"))
                z -= 0.3;
            else if (facing.equalsIgnoreCase("east"))
                x -= 0.3;
            else if (facing.equalsIgnoreCase("west"))
                x += 0.3;
        }

        return new Vec3d(x, y, z);
    }

    public static Vec3d getLaddersAbsolutePosition(MinecraftClient client, Vec3d blockPos) {
        if (client.world == null) return blockPos;

        BlockState blockState = client.world.getBlockState(BlockPos.ofFloored(blockPos.x, blockPos.y, blockPos.z));
        ImmutableSet<Map.Entry<Property<?>, Comparable<?>>> entries = blockState.getEntries().entrySet();

        String facing = "";

        for (Map.Entry<Property<?>, Comparable<?>> i : entries) {

            if (i.getKey().getName().equalsIgnoreCase("facing")) {
                facing = i.getValue().toString();
                break;
            }

        }

        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        if (facing.equalsIgnoreCase("north"))
            z += 0.35;
        else if (facing.equalsIgnoreCase("south"))
            z -= 0.35;
        else if (facing.equalsIgnoreCase("west"))
            x += 0.35;
        else if (facing.equalsIgnoreCase("east"))
            x -= 0.35;

        return new Vec3d(x, y, z);
    }

    public static Vec3d getButtonsAbsolutePosition(MinecraftClient client, Vec3d blockPos) {
        if (client.world == null) return blockPos;

        BlockState blockState = client.world.getBlockState(BlockPos.ofFloored(blockPos.x, blockPos.y, blockPos.z));
        ImmutableSet<Map.Entry<Property<?>, Comparable<?>>> entries = blockState.getEntries().entrySet();

        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        String face = "", facing = "";

        for (Map.Entry<Property<?>, Comparable<?>> i : entries) {

            if (i.getKey().getName().equalsIgnoreCase("face")) {
                face = i.getValue().toString();

            } else if (i.getKey().getName().equalsIgnoreCase("facing")) {
                facing = i.getValue().toString();
            }
        }

        if (face.equalsIgnoreCase("floor")) {
            y -= 0.4;
        } else if (face.equalsIgnoreCase("ceiling")) {
            y += 0.4;
        } else if (face.equalsIgnoreCase("wall")) {
            if (facing.equalsIgnoreCase("north"))
                z += 0.4;
            else if (facing.equalsIgnoreCase("south"))
                z -= 0.4;
            else if (facing.equalsIgnoreCase("east"))
                x -= 0.4;
            else if (facing.equalsIgnoreCase("west"))
                x += 0.4;
        }

        return new Vec3d(x, y, z);
    }

    public static Vec3d getDoorAbsolutePosition(MinecraftClient client, Vec3d blockPos) {
        if (client.world == null) return blockPos;

        BlockState blockState = client.world.getBlockState(BlockPos.ofFloored(blockPos.x, blockPos.y, blockPos.z));
        ImmutableSet<Map.Entry<Property<?>, Comparable<?>>> entries = blockState.getEntries().entrySet();

        String facing = "", hinge = "", open = "";

        for (Map.Entry<Property<?>, Comparable<?>> i : entries) {

            if (i.getKey().getName().equalsIgnoreCase("facing"))
                facing = i.getValue().toString();
            else if (i.getKey().getName().equalsIgnoreCase("hinge"))
                hinge = i.getValue().toString();
            else if (i.getKey().getName().equalsIgnoreCase("open"))
                open = i.getValue().toString();

        }

        double x = blockPos.getX();
        double y = blockPos.getY();
        double z = blockPos.getZ();

        if (open.equalsIgnoreCase("false")) {
            if (facing.equalsIgnoreCase("north"))
                z += 0.35;
            else if (facing.equalsIgnoreCase("south"))
                z -= 0.35;
            else if (facing.equalsIgnoreCase("east"))
                x -= 0.35;
            else if (facing.equalsIgnoreCase("west"))
                x += 0.35;
        } else {
            if (hinge.equalsIgnoreCase("right")) {
                if (facing.equalsIgnoreCase("north"))
                    x += 0.35;
                else if (facing.equalsIgnoreCase("south"))
                    x -= 0.35;
                else if (facing.equalsIgnoreCase("east"))
                    z += 0.35;
                else if (facing.equalsIgnoreCase("west"))
                    z -= 0.35;
            } else {
                if (facing.equalsIgnoreCase("north"))
                    x -= 0.35;
                else if (facing.equalsIgnoreCase("south"))
                    x += 0.35;
                else if (facing.equalsIgnoreCase("east"))
                    z -= 0.35;
                else if (facing.equalsIgnoreCase("west"))
                    z += 0.35;
            }
        }

        return new Vec3d(x, y, z);
    }
}
