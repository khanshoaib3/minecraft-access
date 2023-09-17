package com.github.khanshoaib3.minecraft_access.utils.system;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.stream.IntStream;

/**
 * For Alt (left OR right), Shift (left OR right), Control (left OR right) pressing conditions,
 * use Screen.has[Alt|Shift|Control]Down() methods instead.
 */
public class KeyUtils {
    /**
     * Pass any number of key codes (they are registered as constants: GLFW.GLFW_*)
     */
    public static boolean isAnyPressed(int... keyCodes) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return false;
        long handle = minecraftClient.getWindow().getHandle();
        if (keyCodes.length == 1) {
            return InputUtil.isKeyPressed(handle, keyCodes[0]);
        } else {
            return IntStream.of(keyCodes).anyMatch(c -> InputUtil.isKeyPressed(handle, c));
        }
    }

    public static boolean isAllPressed(int... keyCodes) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return false;
        long handle = minecraftClient.getWindow().getHandle();
        return IntStream.of(keyCodes).allMatch(c -> InputUtil.isKeyPressed(handle, c));
    }

    /**
     * This works even if the keybinding is duplicate i.e. another keybinding has the same key bound to it.<br>
     * <a href="https://minecraft.fandom.com/wiki/Key_codes">Vanilla Keybinding instance translation keys in Minecraft</a><br>
     * Get these instances via InputUtil.fromTranslationKey({key})
     */
    public static boolean isAnyPressed(KeyBinding... keyBindings) {
        if (keyBindings.length == 1) {
            return isAnyPressed(InputUtil.fromTranslationKey(keyBindings[0].getBoundKeyTranslationKey()).getCode());
        } else {
            int[] array = Arrays.stream(keyBindings)
                    .mapToInt(b -> InputUtil.fromTranslationKey(b.getBoundKeyTranslationKey()).getCode())
                    .toArray();
            return isAnyPressed(array);
        }
    }

    @SuppressWarnings("unused")
    public static boolean isAllPressed(KeyBinding... keyBindings) {
        int[] array = Arrays.stream(keyBindings)
                .mapToInt(b -> InputUtil.fromTranslationKey(b.getBoundKeyTranslationKey()).getCode())
                .toArray();
        return isAllPressed(array);
    }

    public static boolean isF3Pressed() {
        return isAnyPressed(GLFW.GLFW_KEY_F3);
    }

    public static boolean isLeftShiftPressed() {
        return isAnyPressed(GLFW.GLFW_KEY_LEFT_SHIFT);
    }

    public static boolean isLeftAltPressed() {
        return isAnyPressed(GLFW.GLFW_KEY_LEFT_ALT);
    }

    public static boolean isRightAltPressed() {
        return isAnyPressed(GLFW.GLFW_KEY_RIGHT_ALT);
    }

    public static boolean isEnterPressed() {
        return isAnyPressed(GLFW.GLFW_KEY_ENTER, GLFW.GLFW_KEY_KP_ENTER);
    }

    public static boolean isSpacePressed() {
        return isAnyPressed(GLFW.GLFW_KEY_SPACE);
    }
}
