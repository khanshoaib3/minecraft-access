package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPositionUtils;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.util.math.Vec3d;

import java.util.Timer;
import java.util.TimerTask;

/**
 * This feature adds the following key binds to control the camera.<br><br>
 * Key binds and combinations:-<br>
 * 1) Look Up Key (default=i, alternate=keypad 8): Moves the camera vertically up by the normal rotating angle (default=22.5).<br>
 * 2) Look Right Key (default=l, alternate=keypad 6): Moves the camera vertically right by the normal rotating angle (default=22.5).<br>
 * 3) Look Down Key (default=k, alternate=keypad 2): Moves the camera vertically down by the normal rotating angle (default=22.5).<br>
 * 4) Look Left Key (default=j, alternate=keypad 4): Moves the camera vertically left by the normal rotating angle (default=22.5).<br>
 * 5) Left Alt + Look Up Key: Moves the camera vertically up by the modified rotating angle (default=11.25).<br>
 * 6) Left Alt + Look Right Key: Moves the camera vertically right by the modified rotating angle (default=11.25).<br>
 * 7) Left Alt + Look Down Key: Moves the camera vertically down by the modified rotating angle (default=11.25).<br>
 * 8) Left Alt + Look Left Key: Moves the camera vertically left by the modified rotating angle (default=11.25).<br>
 * 9) Right Alt + Look Up Key or Look North Key (default=keypad 7): Snaps the camera to the north block.<br>
 * 10) Right Alt + Look Right Key or Look East Key (default=keypad 9): Snaps the camera to the east block.<br>
 * 11) Right Alt + Look Down Key or Look South Key (default=keypad 3): Snaps the camera to the south block.<br>
 * 12) Right Alt + Look Left Key or Look West Key (default=keypad 1): Snaps the camera to the west block.<br>
 * 13) Center Camera Key (default=keypad 5): Snaps the camera to the closest cardinal direction and center it.<br>
 * 14) Left Alt + Center Camera Key : Snaps the camera to the closest opposite cardinal direction and center it.<br>
 */
@Environment(EnvType.CLIENT)
public class CameraControls {
    private MinecraftClient minecraftClient;

    private float normalRotatingDeltaAngle;
    private float modifiedRotatingDeltaAngle;

    private boolean shouldRun = true;
    private int delay;

    public CameraControls() {
        loadConfigurations();
    }

    public void update() {
        if (!this.shouldRun) return;
        try {
            this.minecraftClient = MinecraftClient.getInstance();

            if (minecraftClient == null) return;
            if (minecraftClient.currentScreen != null) return; //Prevent running if any screen is opened

            loadConfigurations();

            boolean wasAnyKeyPressed = keyListener();

            // Pause the execution of this feature for 250 milliseconds
            // TODO Remove Timer
            if (wasAnyKeyPressed) {
                shouldRun = false;
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        shouldRun = true;
                    }
                };
                new Timer().schedule(timerTask, delay);
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered in Camera Controls feature.");
            e.printStackTrace();
        }
    }

    /**
     * Loads the configs from config.json
     */
    private void loadConfigurations() {
        float delta90Degrees = 600f; // 90 / 0.15

        delay = MainClass.config.getConfigMap().getCameraControlsConfigMap().getDelayInMilliseconds();
        float normalRotatingAngle = MainClass.config.getConfigMap().getCameraControlsConfigMap().getNormalRotatingAngle();
        float modifiedRotatingAngle = MainClass.config.getConfigMap().getCameraControlsConfigMap().getModifiedRotatingAngle();
        normalRotatingDeltaAngle = delta90Degrees / (90 / normalRotatingAngle);
        modifiedRotatingDeltaAngle = delta90Degrees / (90 / modifiedRotatingAngle);
    }

    /**
     * Handles the key inputs
     *
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

        boolean isUpKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsUp)
                || KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsAlternateUp);
        boolean isRightKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsRight)
                || KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsAlternateRight);
        boolean isDownKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsDown)
                || KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsAlternateDown);
        boolean isLeftKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsLeft)
                || KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsAlternateLeft);
        boolean isNorthKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsNorth)
                || (isUpKeyPressed && isRightAltPressed && !isLeftAltPressed);
        boolean isEastKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsEast)
                || (isRightKeyPressed && isRightAltPressed && !isLeftAltPressed);
        boolean isWestKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsWest)
                || (isLeftKeyPressed && isRightAltPressed && !isLeftAltPressed);
        boolean isSouthKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsSouth)
                || (isDownKeyPressed && isRightAltPressed && !isLeftAltPressed);
        boolean isCenterCameraKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.cameraControlsCenterCamera);

        if (isNorthKeyPressed) {
            lookNorth();
            return true;
        }

        if (isEastKeyPressed) {
            lookEast();
            return true;
        }

        if (isWestKeyPressed) {
            lookWest();
            return true;
        }

        if (isSouthKeyPressed) {
            lookSouth();
            return true;
        }

        if (isUpKeyPressed) {
            upKeyHandler(isLeftAltPressed);
            return true;
        }

        if (isRightKeyPressed) {
            rightKeyHandler(isLeftAltPressed);
            return true;
        }

        if (isDownKeyPressed) {
            downKeyHandler(isLeftAltPressed);
            return true;
        }

        if (isLeftKeyPressed) {
            leftKeyHandler(isLeftAltPressed);
            return true;
        }

        if (isCenterCameraKeyPressed) {
            centerCamera(isLeftAltPressed);
            return true;
        }

        return false;
    }

    /**
     * Executes when up key is pressed.
     */
    private void upKeyHandler(boolean isLeftAltPressed) {
        rotateCamera(0, (isLeftAltPressed) ? -modifiedRotatingDeltaAngle : -normalRotatingDeltaAngle, false);
    }

    /**
     * Executes when right key is pressed.
     */
    private void rightKeyHandler(boolean isLeftAltPressed) {
        rotateCamera((isLeftAltPressed) ? modifiedRotatingDeltaAngle : normalRotatingDeltaAngle, 0, true);
    }

    /**
     * Executes when down key is pressed.
     */
    private void downKeyHandler(boolean isLeftAltPressed) {
        rotateCamera(0, (isLeftAltPressed) ? modifiedRotatingDeltaAngle : normalRotatingDeltaAngle, false);
    }

    /**
     * Executes when left key is pressed.
     */
    private void leftKeyHandler(boolean isLeftAltPressed) {
        rotateCamera((isLeftAltPressed) ? -modifiedRotatingDeltaAngle : -normalRotatingDeltaAngle, 0, true);
    }

    /**
     * Rotates the player's camera.
     *
     * @param deltaX               The amount of rotation in X axis (in degrees).
     * @param deltaY               The amount of rotation in Y axis (in degrees).
     * @param isRotatingHorizontal Whether the rotation is horizontal or vertical
     */
    private void rotateCamera(float deltaX, float deltaY, boolean isRotatingHorizontal) {
        if (minecraftClient.player == null) return;

        minecraftClient.player.changeLookDirection(deltaX, deltaY);

        MainClass.infoLog("Rotating camera by x:%d y:%d".formatted((int) deltaX, (int) deltaY));

        PlayerPositionUtils pUtil = new PlayerPositionUtils(this.minecraftClient);
        String horizontalDirection = pUtil.getHorizontalFacingDirectionInCardinal();
        String verticalDirection = pUtil.getVerticalFacingDirectionInWords();

        if (isRotatingHorizontal && horizontalDirection != null)
            MainClass.speakWithNarrator(horizontalDirection, true);
        else if (!isRotatingHorizontal && verticalDirection != null)
            MainClass.speakWithNarrator(verticalDirection, true);
    }

    /**
     * Snaps the camera to the north block
     */
    private void lookNorth() {
        lookAtRelativeBlock(0, -1);
    }

    /**
     * Snaps the camera to the east block
     */
    private void lookEast() {
        lookAtRelativeBlock(1, 0);
    }

    /**
     * Snaps the camera to the west block
     */
    private void lookWest() {
        lookAtRelativeBlock(-1, 0);
    }

    /**
     * Snaps the camera to the south block
     */
    private void lookSouth() {
        lookAtRelativeBlock(0, 1);
    }

    /**
     * Snaps the camera to the north-east block
     */
    private void lookNorthEast() {
        lookAtRelativeBlock(1, -1);
    }

    /**
     * Snaps the camera to the north-west block
     */
    private void lookNorthWest() {
        lookAtRelativeBlock(-1, -1);
    }

    /**
     * Snaps the camera to the south-east block
     */
    private void lookSouthEast() {
        lookAtRelativeBlock(1, 1);
    }

    /**
     * Snaps the camera to the south-west block
     */
    private void lookSouthWest() {
        lookAtRelativeBlock(-1, 1);
    }

    /**
     * Snaps the camera at a block relative to the player's position.
     *
     * @param deltaX The relative X position of the block.
     * @param deltaZ The relative Z position of the block.
     */
    private void lookAtRelativeBlock(int deltaX, int deltaZ) {
        if (minecraftClient.player == null) return;

        Vec3d playerBlockPosition = minecraftClient.player.getPos();
        Vec3d relativeBlockPosition = new Vec3d(playerBlockPosition.x + deltaX, playerBlockPosition.y + 0, playerBlockPosition.z + deltaZ);

        minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.FEET, relativeBlockPosition);
        MainClass.speakWithNarrator(new PlayerPositionUtils(minecraftClient).getHorizontalFacingDirectionInCardinal(), true);
    }

    /**
     * Snaps the camera to the closest cardinal direction and centers it.
     *
     * @param lookOpposite Whether to snap the opposite cardinal direction or not and centers it.
     */
    private void centerCamera(boolean lookOpposite) {
        if (minecraftClient.player == null) return;

        String direction = new PlayerPositionUtils(minecraftClient).getHorizontalFacingDirectionInCardinal(true, lookOpposite);

        switch (direction) {
            case "north" -> lookNorth();
            case "east" -> lookEast();
            case "west" -> lookWest();
            case "south" -> lookSouth();
            case "north_east" -> lookNorthEast();
            case "north_west" -> lookNorthWest();
            case "south_east" -> lookSouthEast();
            case "south_west" -> lookSouthWest();
        }
    }
}
