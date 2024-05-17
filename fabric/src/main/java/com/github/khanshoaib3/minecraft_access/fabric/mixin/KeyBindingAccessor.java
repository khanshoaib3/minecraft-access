package com.github.khanshoaib3.minecraft_access.fabric.mixin;

import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.Map;

/**
 * Copied from fabric-api 0.93.1+1.20.4
 * This file is under the Apache-2.0 license
 */
@Mixin(KeyBinding.class)
public interface KeyBindingAccessor {
    @Accessor("CATEGORY_ORDER_MAP")
    static Map<String, Integer> ma_getCategoryMap() {
        throw new AssertionError();
    }
}