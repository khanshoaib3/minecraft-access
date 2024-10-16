package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

// the reason for this existing is Java being weird and not having a built-in functional interface that takes 2 parameters and returns a single value
@FunctionalInterface
public interface BlockFilter {
    public Boolean apply(BlockState block, BlockPos position);
}
