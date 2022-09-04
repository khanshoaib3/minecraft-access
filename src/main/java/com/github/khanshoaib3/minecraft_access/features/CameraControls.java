package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class CameraControls {
    private KeyBinding up, right, down, left;
    private MinecraftClient minecraftClient;
    private final float delta90Degrees = 600f; // 90 / 0.15
    private final float normalRotatingAngle = 22.5f; //TODO add this to config
    private final float normalRotatingDeltaAngle = delta90Degrees / (90 / normalRotatingAngle);
    private final float modifiedRotatingAngle = 11.25f; //TODO add this to config
    private final float modifiedRotatingDeltaAngle = delta90Degrees / (90 / modifiedRotatingAngle);

    public CameraControls() {
        init();
    }

    private void init() {
        String categoryTranslationKey = "Camera Controls"; //TODO add translation key instead

        up = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Up", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                categoryTranslationKey
        ));

        right = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Right", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                categoryTranslationKey
        ));

        down = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Down", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                categoryTranslationKey
        ));

        left = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Left", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                categoryTranslationKey
        ));
    }

    public void update(MinecraftClient minecraftClient) {
        try {
            if (minecraftClient == null) return;
            if (minecraftClient.currentScreen != null) return; //Prevent running if any screen is opened
            this.minecraftClient = minecraftClient;

            boolean isLeftAltPressed = InputUtil.isKeyPressed(
                    MinecraftClient.getInstance().getWindow().getHandle(),
                    InputUtil.fromTranslationKey("key.keyboard.left.alt").getCode()
            );

            if (isLeftAltPressed) {
                MainClass.infoLog("Left alt key pressed");
            }

            if (up.wasPressed()) {
                MainClass.infoLog("up key pressed");
                upKeyHandler(isLeftAltPressed);
            }

            if (right.wasPressed()) {
                MainClass.infoLog("right key pressed");
                rightKeyHandler(isLeftAltPressed);
            }

            if (down.wasPressed()) {
                MainClass.infoLog("down key pressed");
                downKeyHandler(isLeftAltPressed);
            }

            if (left.wasPressed()) {
                MainClass.infoLog("left key pressed");
                leftKeyHandler(isLeftAltPressed);
            }
        } catch (Exception e) {
            MainClass.errorLog("Error encountered in Camera Controls feature.\n");
            e.printStackTrace();
        }
    }

    private void upKeyHandler(boolean altKey) {
        if (minecraftClient.player == null) return;

        if (altKey)
            minecraftClient.player.changeLookDirection(0, -modifiedRotatingDeltaAngle);
        else
            minecraftClient.player.changeLookDirection(0, -normalRotatingDeltaAngle);
    }

    private void rightKeyHandler(boolean altKey) {
        if (minecraftClient.player == null) return;

        if (altKey)
            minecraftClient.player.changeLookDirection(modifiedRotatingDeltaAngle, 0);
        else
            minecraftClient.player.changeLookDirection(normalRotatingDeltaAngle, 0);
    }

    private void downKeyHandler(boolean altKey) {
        if (minecraftClient.player == null) return;

        if (altKey)
            minecraftClient.player.changeLookDirection(0, modifiedRotatingDeltaAngle);
        else
            minecraftClient.player.changeLookDirection(0, normalRotatingDeltaAngle);
    }

    private void leftKeyHandler(boolean altKey) {
        if (minecraftClient.player == null) return;

        if (altKey)
            minecraftClient.player.changeLookDirection(-modifiedRotatingDeltaAngle, 0);
        else
            minecraftClient.player.changeLookDirection(-normalRotatingDeltaAngle, 0);
    }
}
