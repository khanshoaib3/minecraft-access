package com.github.khanshoaib3.minecraft_access.features.read_crosshair;

import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public interface CrosshairNarrator {
    String narrate(BlockHitResult hit, boolean speakSide);
    String narrate(EntityHitResult hit);
}
