package com.github.khanshoaib3.minecraft_access.features.read_crosshair;

import com.github.khanshoaib3.minecraft_access.utils.NarrationUtils;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;

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
    public @NotNull HitResult rayCast() {
        return PlayerUtils.crosshairTarget(PlayerUtils.getInteractionRange());
    }

    @Override
    public @Nullable Object deduplication(@NotNull ClientWorld world, boolean speakSide, boolean speakConsecutiveBlocks) {
        HitResult hit = rayCast();
        if (hit.getType() == HitResult.Type.MISS) {
            return null;
        }
        if (hit instanceof BlockHitResult blockHitResult) {
            String side = speakSide ? blockHitResult.getSide().getName() : "";
            return Arrays.asList(
                    NarrationUtils.narrateBlockForContentChecking(blockHitResult.getBlockPos(), side).getRight(),
                    speakConsecutiveBlocks ? blockHitResult.getBlockPos() : null
            );
        }
        if (hit instanceof EntityHitResult entityHitResult) {
            return NarrationUtils.narrateEntity(entityHitResult.getEntity());
        }
        return null;
    }

    @Override
    public @NotNull String narrate(@NotNull ClientWorld world, boolean speakSide) {
        HitResult hit = rayCast();
        if (hit instanceof BlockHitResult blockHitResult) {
            String side = speakSide ? I18n.translate(String.format("minecraft_access.direction.%s", blockHitResult.getSide().getName())) : "";
            return NarrationUtils.narrateBlockForContentChecking(blockHitResult.getBlockPos(), side).getLeft();
        }
        if (hit instanceof EntityHitResult entityHitResult) {
            return NarrationUtils.narrateEntity(entityHitResult.getEntity());
        }
        throw new IllegalStateException("Ray cast returned neither BlockHitResult nor EntityHitResult");
    }
}
