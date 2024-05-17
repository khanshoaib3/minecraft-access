package com.github.khanshoaib3.minecraft_access.fabric.vendor;

import com.github.khanshoaib3.minecraft_access.fabric.mixin.KeyBindingAccessor;
import it.unimi.dsi.fastutil.objects.ReferenceArrayList;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;

import java.util.List;
import java.util.Map;
import java.util.Optional;

/**
 * Copied from fabric-api 0.93.1+1.20.4
 * This file is under the Apache-2.0 license
 */
public class KeyBindingRegistry {
    private static final List<KeyBinding> MODDED_KEY_BINDINGS = new ReferenceArrayList<>(); // ArrayList with identity based comparisons for contains/remove/indexOf etc., required for correctly handling duplicate keybinds

    private KeyBindingRegistry() {
    }

    private static Map<String, Integer> getCategoryMap() {
        return KeyBindingAccessor.ma_getCategoryMap();
    }

    public static void addCategory(String categoryTranslationKey) {
        Map<String, Integer> map = getCategoryMap();

        if (map.containsKey(categoryTranslationKey)) {
            return;
        }

        Optional<Integer> largest = map.values().stream().max(Integer::compareTo);
        int largestInt = largest.orElse(0);
        map.put(categoryTranslationKey, largestInt + 1);
    }

    public static void registerKeyBinding(KeyBinding binding) {
        if (MinecraftClient.getInstance().options != null) {
            throw new IllegalStateException("GameOptions has already been initialised");
        }

        for (KeyBinding existingKeyBindings : MODDED_KEY_BINDINGS) {
            if (existingKeyBindings == binding) {
                throw new IllegalArgumentException("Attempted to register a key binding twice: " + binding.getTranslationKey());
            } else if (existingKeyBindings.getTranslationKey().equals(binding.getTranslationKey())) {
                throw new IllegalArgumentException("Attempted to register two key bindings with equal ID: " + binding.getTranslationKey() + "!");
            }
        }

        // This will do nothing if the category already exists.
        addCategory(binding.getCategory());
        MODDED_KEY_BINDINGS.add(binding);
    }
}
