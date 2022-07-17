package com.shoaib.minecraft_access.mixin;

import com.mojang.text2speech.Narrator;
import net.minecraft.client.util.NarratorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(NarratorManager.class)
public interface NarratorManagerAccessor {
    @Accessor("narrator")
    Narrator getNarrator();
}
