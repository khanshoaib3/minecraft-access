package com.github.khanshoaib3.minecraft_access.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Environment(EnvType.CLIENT)
@Mixin(LoomScreen.class)
public interface LoomScreenAccessor {
    @Accessor
    boolean isCanApplyDyePattern();

    @Accessor
    int getVisibleTopRow();
}
