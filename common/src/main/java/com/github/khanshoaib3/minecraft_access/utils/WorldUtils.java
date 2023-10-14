package com.github.khanshoaib3.minecraft_access.utils;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3i;

import java.util.Collection;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class WorldUtils {
    public record BlockInfo(BlockPos pos, BlockState state, Block type, BlockEntity entity) {
    }

    public static Optional<BlockInfo> getBlockInfo(BlockPos pos) {
        Optional<ClientWorld> w = getClientWorld();
        if (w.isEmpty()) return Optional.empty();
        ClientWorld world = w.get();

        // Since Minecraft uses flyweight pattern for blocks and entities,
        // All same type of blocks share one singleton Block instance,
        // While every block keep their states with a BlockState instance.
        BlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        BlockEntity entity = world.getBlockEntity(pos);

        return Optional.of(new BlockInfo(pos, state, block, entity));
    }

    public static Optional<ClientWorld> getClientWorld() {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c == null) return Optional.empty();
        ClientWorld world = c.world;
        return world == null ? Optional.empty() : Optional.of(world);
    }

    public static Optional<ClientPlayerEntity> getClientPlayer() {
        MinecraftClient c = MinecraftClient.getInstance();
        if (c == null) return Optional.empty();
        ClientPlayerEntity p = c.player;
        return p == null ? Optional.empty() : Optional.of(p);
    }

    @SuppressWarnings("unused")
    public static Optional<Boolean> checkAllOfSurroundingBlocks(BlockPos pos, Collection<Vec3i> offsets, Predicate<BlockState> expected) {
        return checkAllOfBlocks(offsets.stream().map(pos::add).collect(Collectors.toList()), expected);
    }

    public static Optional<Boolean> checkAllOfBlocks(Collection<BlockPos> positions, Predicate<BlockState> expected) {
        for (BlockPos pos : positions) {
            Optional<BlockInfo> info = getBlockInfo(pos);
            if (info.isEmpty()) return Optional.empty();

            if (!expected.test(info.get().state)) return Optional.of(false);
        }
        return Optional.of(true);
    }

    public static Optional<Boolean> checkAnyOfSurroundingBlocks(BlockPos pos, Collection<Vec3i> offsets, Predicate<BlockState> expected) {
        return checkAnyOfBlocks(offsets.stream().map(pos::add).collect(Collectors.toList()), expected);
    }

    public static Optional<Boolean> checkAnyOfBlocks(Collection<BlockPos> positions, Predicate<BlockState> expected) {
        for (BlockPos pos : positions) {
            Optional<BlockInfo> info = getBlockInfo(pos);
            if (info.isEmpty()) return Optional.empty();

            if (expected.test(info.get().state)) return Optional.of(true);
        }
        return Optional.of(false);
    }

}
