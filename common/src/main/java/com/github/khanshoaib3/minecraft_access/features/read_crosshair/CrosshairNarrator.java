package com.github.khanshoaib3.minecraft_access.features.read_crosshair;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public interface CrosshairNarrator {
    String narrate(BlockHitResult hit, ClientWorld world, boolean speakSide);

    String narrate(EntityHitResult hit);
}
