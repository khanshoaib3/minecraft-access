package com.github.khanshoaib3.minecraft_access.utils;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Initializes all the keybindings used in the mod.
 */
public class KeyBindingsHandler {
    public KeyBinding healthNHungerNarrationKey;
    public KeyBinding lockingHandlerKey;
    public KeyBinding positionNarrationKey;
    public KeyBinding narratorMenuKey;
    public KeyBinding directionNarrationKey;

    public KeyBinding cameraControlsUp;
    public KeyBinding cameraControlsRight;
    public KeyBinding cameraControlsDown;
    public KeyBinding cameraControlsLeft;

    public KeyBinding inventoryControlsGroupKey;
    public KeyBinding inventoryControlsUpKey;
    public KeyBinding inventoryControlsRightKey;
    public KeyBinding inventoryControlsDownKey;
    public KeyBinding inventoryControlsLeftKey;
    public KeyBinding inventoryControlsLeftMouseClickKey;
    public KeyBinding inventoryControlsRightMouseClickKey;
    public KeyBinding inventoryControlsSwitchTabKey;
    public KeyBinding inventoryControlsToggleCraftableKey;

    private static final String OTHER_GROUP_TRANSLATION_KEY = "minecraft_access.keys.other.group_name";
    private static final String CAMERA_CONTROLS_TRANSLATION_KEY = "minecraft_access.keys.camera_controls.group_name";
    private static final String INVENTORY_CONTROLS_TRANSLATION_KEY = "minecraft_access.keys.inventory_controls.group_name";

    /**
     * Constructor that initializes all the keybindings.
     */
    public KeyBindingsHandler() {
        initializeCameraControlsKeybindings();

        initializeInventoryControlsKeybindings();

        initializeOtherKeybindings();
    }

    /**
     * Initializes keybindings related to inventory controls.<br><br>
     * Related key binds and combinations:-<br>
     * 1) Up Key (default: I) = Focus to slot above.<br>
     * 2) Right Key (default: L) = Focus to slot right.<br>
     * 3) Down Key (default: K) = Focus to slot down.<br>
     * 4) Left Key (default: J) = Focus to slot left.<br>
     * 5) Group Key (default: C) = Select next group.<br>
     * 6) Left Shift + Group Key = Select previous group.<br>
     * 7) Switch Tab Key (default: V) = Select next tab (only for creative inventory screen and inventory/crafting screen).<br>
     * 8) Left Shift + Switch Tab Key = Select previous tab (only for creative inventory screen and inventory/crafting screen).<br>
     * 9) Toggle Craftable Key (default: R) = Toggle between show all and show only craftable recipes in inventory/crafting screen.<br>
     * 10) Left Mouse Click Sim Key (default: [) = Simulates left mouse click.<br>
     * 11) Right Mouse Click Sim Key (default: ]) = Simulates right mouse click.<br>
     * 12) T Key (not re-mappable) = Select the search box.<br>
     * 13) Enter Key (not re-mappable) = Deselect the search box.<br>
     */
    private void initializeInventoryControlsKeybindings() {
        inventoryControlsGroupKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.change_group_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsGroupKey);

        inventoryControlsLeftMouseClickKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.left_mouse_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsLeftMouseClickKey);

        inventoryControlsRightMouseClickKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.right_mouse_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsRightMouseClickKey);

        inventoryControlsUpKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.up_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsUpKey);

        inventoryControlsRightKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.right_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsRightKey);

        inventoryControlsDownKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.down_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsDownKey);

        inventoryControlsLeftKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.left_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsLeftKey);

        inventoryControlsSwitchTabKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.switch_tabs_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsSwitchTabKey);

        inventoryControlsToggleCraftableKey = new KeyBinding(
                "minecraft_access.keys.inventory_controls.toggle_craftable_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                INVENTORY_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(inventoryControlsToggleCraftableKey);
    }

    /**
     * Initializes keybindings related to camera controls.<br><br>
     * Related key binds and combinations:-<br>
     * 1) Look Up Key (default=i): Moves the camera vertically up by the normal rotating angle (default=22.5).<br>
     * 2) Look Right Key (default=l): Moves the camera vertically right by the normal rotating angle (default=22.5).<br>
     * 3) Look Down Key (default=k): Moves the camera vertically down by the normal rotating angle (default=22.5).<br>
     * 4) Look Left Key (default=j): Moves the camera vertically left by the normal rotating angle (default=22.5).<br>
     * 5) Left Alt + Look Up Key: Moves the camera vertically up by the modified rotating angle (default=11.25).<br>
     * 6) Left Alt + Look Right Key: Moves the camera vertically right by the modified rotating angle (default=11.25).<br>
     * 7) Left Alt + Look Down Key: Moves the camera vertically down by the modified rotating angle (default=11.25).<br>
     * 8) Left Alt + Look Left Key: Moves the camera vertically left by the modified rotating angle (default=11.25).<br>
     * 9) Right Alt + Look Up Key: Snaps the camera to the north block.<br>
     * 10) Right Alt + Look Right Key: Snaps the camera to the east block.<br>
     * 11) Right Alt + Look Down Key: Snaps the camera to the south block.<br>
     * 12) Right Alt + Look Left Key: Snaps the camera to the west block.<br>
     */
    private void initializeCameraControlsKeybindings() {
        cameraControlsUp = new KeyBinding(
                "minecraft_access.keys.camera_controls.up_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsUp);

        cameraControlsRight = new KeyBinding(
                "minecraft_access.keys.camera_controls.right_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsRight);

        cameraControlsDown = new KeyBinding(
                "minecraft_access.keys.camera_controls.down_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsDown);

        cameraControlsLeft = new KeyBinding(
                "minecraft_access.keys.camera_controls.left_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsLeft);
    }

    /**
     * Initializes all the other keybindings.<br><br>
     * Related key binds and combinations:-<br>
     * 1) Locking Key (default: Y) = Locks onto the nearest entity or block<br>
     * 2) Alt key + Locking Key = Unlocks from the currently locked entity or block<br>
     * 3) Speak Facing Direction Key (default: H) = Speaks the player facing direction.
     * 4) Speak Player Health and Hunger Key (default: R) = Speaks the health and hunger.
     * 5) Narrator Menu Key (default: F4) = Opens a menu with few options.
     * 6) Speak Player Position Key (default: G) = Speaks the player's x y and z position.<br>
     * 7) Left Alt + X = Speaks only the x position.<br>
     * 8) Left Alt + C = Speaks only the y position.<br>
     * 9) Left Alt + Z = Speaks only the z position.<br>
     */
    private void initializeOtherKeybindings() {
        healthNHungerNarrationKey = new KeyBinding(
                "minecraft_access.keys.other.health_n_hunger_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(healthNHungerNarrationKey);

        lockingHandlerKey = new KeyBinding(
                "minecraft_access.keys.other.locking_handler_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(lockingHandlerKey);

        positionNarrationKey = new KeyBinding(
                "minecraft_access.keys.other.player_position_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_G,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(positionNarrationKey);

        narratorMenuKey = new KeyBinding(
                "minecraft_access.keys.other.narrator_menu_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(narratorMenuKey);

        directionNarrationKey = new KeyBinding(
                "minecraft_access.keys.other.facing_direction_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(directionNarrationKey);
    }

    /**
     * Checks whether the given keybinding is currently pressed or not. This works even if the keybinding is duplicate i.e. another keybinding has the sane key bound to it.
     *
     * @param keyBindingToCheck The keybinding we want to check.
     * @return Returns true if the keybinding is currently pressed else false.
     */
    public boolean isPressed(KeyBinding keyBindingToCheck) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return false;

        return InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey(keyBindingToCheck.getBoundKeyTranslationKey()).getCode()
        );
    }

    public boolean isF3KeyPressed() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return false;

        return InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.GLFW_KEY_F3
        );
    }
}
