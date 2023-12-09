package com.github.khanshoaib3.minecraft_access.forge.mixin;

import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(InGameHud.class)
public interface InGameHudAccessor {
    @Accessor
    int getHeldItemTooltipFade();

    @Accessor
    ItemStack getCurrentStack();
}
