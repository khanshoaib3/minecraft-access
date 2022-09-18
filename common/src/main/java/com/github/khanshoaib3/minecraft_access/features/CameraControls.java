package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityUtils;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This feature adds key binds to control the camera.
 */
@Environment(EnvType.CLIENT)
public class CameraControls {
    public final KeyBinding up; //TODO create a separate class for initializing key binds
    public final KeyBinding right;
    public final KeyBinding down;
    public final KeyBinding left;
    private MinecraftClient minecraftClient;

    private final float delta90Degrees = 600f; // 90 / 0.15
    private final float normalRotatingAngle = 22.5f; //TODO add this to config
    private final float normalRotatingDeltaAngle = delta90Degrees / (90 / normalRotatingAngle);
    private final float modifiedRotatingAngle = 11.25f; //TODO add this to config
    private final float modifiedRotatingDeltaAngle = delta90Degrees / (90 / modifiedRotatingAngle);
    private boolean shouldRun = true;

    /**
     * Initializes the key binds.
     */
    public CameraControls() {
        String categoryTranslationKey = "Camera Controls"; //TODO add translation key instead

        up = new KeyBinding(
                "Up", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                categoryTranslationKey
        );

        right = new KeyBinding(
                "Right", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                categoryTranslationKey
        );

        down = new KeyBinding(
                "Down", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                categoryTranslationKey
        );

        left = new KeyBinding(
                "Left", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                categoryTranslationKey
        );

        KeyMappingRegistry.register(up);
        KeyMappingRegistry.register(right);
        KeyMappingRegistry.register(down);
        KeyMappingRegistry.register(left);
    }

    public void update() {
        if (!this.shouldRun) return;
        try {
            this.minecraftClient = MinecraftClient.getInstance();

            if (minecraftClient == null) return;
            if (minecraftClient.currentScreen != null) return; //Prevent running if any screen is opened

            boolean wasAnyKeyPressed = keyListener();

            // Pause the execution of this feature for 250 milliseconds
            if (wasAnyKeyPressed) {
                shouldRun = false;
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        shouldRun = true;
                    }
                };
                new Timer().schedule(timerTask, 250);
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered in Camera Controls feature.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the key inputs
     * @return True if any key is pressed.
     */
    private boolean keyListener() {
        // https://minecraft.fandom.com/wiki/Key_codes
        boolean isLeftAltPressed = InputUtil.isKeyPressed(
                MinecraftClient.getInstance().getWindow().getHandle(),
                InputUtil.fromTranslationKey("key.keyboard.left.alt").getCode()
        );

        boolean isRightAltPressed = InputUtil.isKeyPressed(
                MinecraftClient.getInstance().getWindow().getHandle(),
                InputUtil.fromTranslationKey("key.keyboard.right.alt").getCode()
        );

        boolean isUpKeyPressed = InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey(up.getBoundKeyTranslationKey()).getCode()
        );

        boolean isRightKeyPressed = InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey(right.getBoundKeyTranslationKey()).getCode()
        );

        boolean isDownKeyPressed = InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey(down.getBoundKeyTranslationKey()).getCode()
        );

        boolean isLeftKeyPressed = InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey(left.getBoundKeyTranslationKey()).getCode()
        );


        if (isUpKeyPressed) {
            upKeyHandler(isLeftAltPressed, isRightAltPressed);
            return true;
        }

        if (isRightKeyPressed) {
            rightKeyHandler(isLeftAltPressed, isRightAltPressed);
            return true;
        }

        if (isDownKeyPressed) {
            downKeyHandler(isLeftAltPressed, isRightAltPressed);
            return true;
        }

        if (isLeftKeyPressed) {
            leftKeyHandler(isLeftAltPressed, isRightAltPressed);
            return true;
        }

        return false;
    }

    /**
     * Executes when up key is pressed.
     */
    private void upKeyHandler(boolean isLeftAltPressed, boolean isRightAltPressed) {
        if (minecraftClient.player == null) return;

        if (isRightAltPressed && !isLeftAltPressed) {
            lookNorth();
        } else {
            if (isLeftAltPressed)
                minecraftClient.player.changeLookDirection(0, -modifiedRotatingDeltaAngle);
            else
                minecraftClient.player.changeLookDirection(0, -normalRotatingDeltaAngle);

            MainClass.infoLog("Rotating %f degrees upwards.".formatted((isLeftAltPressed) ? modifiedRotatingAngle : normalRotatingAngle));
            if(ClientPlayerEntityUtils.getVerticalFacingDirectionInWords()!=null)
                MainClass.speakWithNarrator(ClientPlayerEntityUtils.getVerticalFacingDirectionInWords(), true);
        }
    }

    /**
     * Executes when right key is pressed.
     */
    private void rightKeyHandler(boolean isLeftAltPressed, boolean isRightAltPressed) {
        if (minecraftClient.player == null) return;

        if (isRightAltPressed && !isLeftAltPressed) {
            lookEast();
        } else {
            if (isLeftAltPressed)
                minecraftClient.player.changeLookDirection(modifiedRotatingDeltaAngle, 0);
            else
                minecraftClient.player.changeLookDirection(normalRotatingDeltaAngle, 0);

            MainClass.infoLog("Rotating %f degrees rightwards.".formatted((isLeftAltPressed) ? modifiedRotatingAngle : normalRotatingAngle));
            if(ClientPlayerEntityUtils.getHorizontalFacingDirectionInWords(true)!=null)
                MainClass.speakWithNarrator(ClientPlayerEntityUtils.getHorizontalFacingDirectionInWords(true), true);
        }
    }

    /**
     * Executes when down key is pressed.
     */
    private void downKeyHandler(boolean isLeftAltPressed, boolean isRightAltPressed) {
        if (minecraftClient.player == null) return;

        if (isRightAltPressed && !isLeftAltPressed) {
            lookSouth();
        } else {
            if (isLeftAltPressed)
                minecraftClient.player.changeLookDirection(0, modifiedRotatingDeltaAngle);
            else
                minecraftClient.player.changeLookDirection(0, normalRotatingDeltaAngle);

            MainClass.infoLog("Rotating %f degrees downwards.".formatted((isLeftAltPressed) ? modifiedRotatingAngle : normalRotatingAngle));
            if(ClientPlayerEntityUtils.getVerticalFacingDirectionInWords()!=null)
                MainClass.speakWithNarrator(ClientPlayerEntityUtils.getVerticalFacingDirectionInWords(), true);
        }
    }

    /**
     * Executes when left key is pressed.
     */
    private void leftKeyHandler(boolean isLeftAltPressed, boolean isRightAltPressed) {
        if (minecraftClient.player == null) return;

        if (isRightAltPressed && !isLeftAltPressed) {
            lookWest();
        } else {
            if (isLeftAltPressed)
                minecraftClient.player.changeLookDirection(-modifiedRotatingDeltaAngle, 0);
            else
                minecraftClient.player.changeLookDirection(-normalRotatingDeltaAngle, 0);

            MainClass.infoLog("Rotating %f degrees leftwards.".formatted((isLeftAltPressed) ? modifiedRotatingAngle : normalRotatingAngle));
            if(ClientPlayerEntityUtils.getHorizontalFacingDirectionInWords(true)!=null)
                MainClass.speakWithNarrator(ClientPlayerEntityUtils.getHorizontalFacingDirectionInWords(true), true);
        }
    }

    /**
     * Snaps the camera to the north block
     */
    private void lookNorth() {
        if (minecraftClient.player == null) return;

        Vec3d playerBlockPosition = minecraftClient.player.getPos();
        Vec3d northBlockPosition = new Vec3d(playerBlockPosition.x + 0, playerBlockPosition.y + 0, playerBlockPosition.z - 1);

        minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, northBlockPosition);
        MainClass.infoLog("Looking north");
        MainClass.speakWithNarrator("North", true); //TODO use i18n instead
    }

    /**
     * Snaps the camera to the east block
     */
    private void lookEast() {
        if (minecraftClient.player == null) return;

        Vec3d playerBlockPosition = minecraftClient.player.getPos();
        Vec3d eastBlockPosition = new Vec3d(playerBlockPosition.x + 1, playerBlockPosition.y + 0, playerBlockPosition.z + 0);

        minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, eastBlockPosition);
        MainClass.infoLog("Looking east");
        MainClass.speakWithNarrator("East", true); //TODO use i18n instead
    }

    /**
     * Snaps the camera to the west block
     */
    private void lookWest() {
        if (minecraftClient.player == null) return;

        Vec3d playerBlockPosition = minecraftClient.player.getPos();
        Vec3d westBlockPosition = new Vec3d(playerBlockPosition.x - 1, playerBlockPosition.y + 0, playerBlockPosition.z + 0);

        minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, westBlockPosition);
        MainClass.infoLog("Looking west");
        MainClass.speakWithNarrator("West", true); //TODO use i18n instead
    }

    /**
     * Snaps the camera to the south block
     */
    private void lookSouth() {
        if (minecraftClient.player == null) return;

        Vec3d playerBlockPosition = minecraftClient.player.getPos();
        Vec3d southBlockPosition = new Vec3d(playerBlockPosition.x + 0, playerBlockPosition.y + 0, playerBlockPosition.z + 1);

        minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, southBlockPosition);
        MainClass.infoLog("Looking south");
        MainClass.speakWithNarrator("South", true); //TODO use i18n instead
    }
}
