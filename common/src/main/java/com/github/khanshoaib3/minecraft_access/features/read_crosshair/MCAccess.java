package com.github.khanshoaib3.minecraft_access.features.read_crosshair;

import com.github.khanshoaib3.minecraft_access.utils.NarrationUtils;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;

public class MCAccess implements CrosshairNarrator {
    private static MCAccess INSTANCE;

    private MCAccess() {}

    public static MCAccess getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new MCAccess();
        }
        return INSTANCE;
    }

    @Override
    public String narrate(BlockHitResult hit, ClientWorld world, boolean speakSide) {
        String side = speakSide ? hit.getSide().getName() : "";
        return NarrationUtils.narrateBlockForContentChecking(hit.getBlockPos(), side).getLeft();
    }

    @Override
    public String narrate(EntityHitResult hit) {
        return NarrationUtils.narrateEntity(hit.getEntity());
    }
}
