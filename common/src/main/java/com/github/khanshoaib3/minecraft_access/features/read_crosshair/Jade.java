package com.github.khanshoaib3.minecraft_access.features.read_crosshair;

import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import snownee.jade.api.BlockAccessor;
import snownee.jade.api.EntityAccessor;
import snownee.jade.impl.Tooltip;
import snownee.jade.impl.WailaClientRegistration;

public class Jade implements CrosshairNarrator {
    private static Jade INSTANCE;

    private Jade() {}

    public static Jade getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Jade();
        }
        return INSTANCE;
    }

    @Override
    public String narrate(BlockHitResult hit, ClientWorld world, boolean speakSide) {
        BlockAccessor accessor = WailaClientRegistration.instance().blockAccessor()
                .blockState(world.getBlockState(hit.getBlockPos()))
                .blockEntity(world.getBlockEntity(hit.getBlockPos()))
                .hit(hit)
                .requireVerification()
                .build();
        Tooltip tooltip = new Tooltip();
        WailaClientRegistration.instance()
                .getAccessorHandler(accessor.getAccessorType())
                .gatherComponents(accessor, $ -> tooltip);
        return tooltip.getMessage();
    }

    @Override
    public String narrate(EntityHitResult hit) {
        EntityAccessor accessor = WailaClientRegistration.instance().entityAccessor()
                .hit(hit)
                .entity(hit.getEntity())
                .requireVerification()
                .build();
        Tooltip tooltip = new Tooltip();
        WailaClientRegistration.instance()
                .getAccessorHandler(accessor.getAccessorType())
                .gatherComponents(accessor, $ -> tooltip);
        return tooltip.getMessage();
    }
}
