package com.github.khanshoaib3.minecraft_access.utils;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
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
    public KeyBinding narratorMenuHotKey;
    public KeyBinding directionNarrationKey;

    public KeyBinding cameraControlsUp;
    public KeyBinding cameraControlsRight;
    public KeyBinding cameraControlsDown;
    public KeyBinding cameraControlsLeft;
    public KeyBinding cameraControlsAlternateUp;
    public KeyBinding cameraControlsAlternateRight;
    public KeyBinding cameraControlsAlternateDown;
    public KeyBinding cameraControlsAlternateLeft;
    public KeyBinding cameraControlsNorth;
    public KeyBinding cameraControlsEast;
    public KeyBinding cameraControlsWest;
    public KeyBinding cameraControlsSouth;
    public KeyBinding cameraControlsCenterCamera;
    public KeyBinding cameraControlsStraightUp;
    public KeyBinding cameraControlsStraightDown;

    public KeyBinding inventoryControlsGroupKey;
    public KeyBinding inventoryControlsUpKey;
    public KeyBinding inventoryControlsRightKey;
    public KeyBinding inventoryControlsDownKey;
    public KeyBinding inventoryControlsLeftKey;
    public KeyBinding inventoryControlsLeftMouseClickKey;
    public KeyBinding inventoryControlsRightMouseClickKey;
    public KeyBinding inventoryControlsSwitchTabKey;
    public KeyBinding inventoryControlsToggleCraftableKey;

    public KeyBinding mouseSimulationLeftMouseKey;
    public KeyBinding mouseSimulationRightMouseKey;
    public KeyBinding mouseSimulationMiddleMouseKey;
    public KeyBinding mouseSimulationScrollUpKey;
    public KeyBinding mouseSimulationScrollDownKey;

    public KeyBinding areaMapMenuKey;

    private static final String OTHER_GROUP_TRANSLATION_KEY = "minecraft_access.keys.other.group_name";
    private static final String CAMERA_CONTROLS_TRANSLATION_KEY = "minecraft_access.keys.camera_controls.group_name";
    private static final String INVENTORY_CONTROLS_TRANSLATION_KEY = "minecraft_access.keys.inventory_controls.group_name";
    private static final String MOUSE_SIMULATION_KEY = "minecraft_access.keys.mouse_simulation.group_name";
    private static final String AREA_MAP_GROUP_KEY = "minecraft_access.keys.area_map.group_name";

    private static final KeyBindingsHandler instance;

    /**
     * Constructor that initializes all the keybindings.
     */
    private KeyBindingsHandler() {
        initializeCameraControlsKeybindings();
        initializeInventoryControlsKeybindings();
        initializeMouseSimulationKeybindings();
        initializeAreaMapKeybindings();
        initializeOtherKeybindings();
    }

    static {
        try {
            instance = new KeyBindingsHandler();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating KeyBindingsHandler instance", e);
        }
    }

    public static KeyBindingsHandler getInstance() {
        return instance;
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
     * 15) Right Alt + double Look Up Key or Look Straight Up Key (default: Keypad 0): Snaps the camera to the look above head direction.<br>
     * 16) Right Alt + double Look Down Key or Look Straight Down Key (default: Keypad .): Snaps the camera to the look down at feet direction.
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

        cameraControlsAlternateUp = new KeyBinding(
                "minecraft_access.keys.camera_controls.alternate_up_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_8,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsAlternateUp);

        cameraControlsAlternateRight = new KeyBinding(
                "minecraft_access.keys.camera_controls.alternate_right_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_6,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsAlternateRight);

        cameraControlsAlternateDown = new KeyBinding(
                "minecraft_access.keys.camera_controls.alternate_down_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_2,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsAlternateDown);

        cameraControlsAlternateLeft = new KeyBinding(
                "minecraft_access.keys.camera_controls.alternate_left_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_4,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsAlternateLeft);

        cameraControlsNorth = new KeyBinding(
                "minecraft_access.keys.camera_controls.north_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_7,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsNorth);

        cameraControlsEast = new KeyBinding(
                "minecraft_access.keys.camera_controls.east_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_9,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsEast);

        cameraControlsWest = new KeyBinding(
                "minecraft_access.keys.camera_controls.west_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_1,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsWest);

        cameraControlsSouth = new KeyBinding(
                "minecraft_access.keys.camera_controls.south_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_3,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsSouth);

        cameraControlsStraightUp = new KeyBinding(
                "minecraft_access.keys.camera_controls.straight_up_camera_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_0,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsStraightUp);

        cameraControlsStraightDown = new KeyBinding(
                "minecraft_access.keys.camera_controls.straight_down_camera_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_DECIMAL,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsStraightDown);

        cameraControlsCenterCamera = new KeyBinding(
                "minecraft_access.keys.camera_controls.center_camera_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_KP_5,
                CAMERA_CONTROLS_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(cameraControlsCenterCamera);
    }

    /**
     * Initializes all mouse simulation keybindings.<br><br>
     * Related key binds and combinations:-<br>
     * 1) Left Mouse Sim Key (default: [) = Simulates left mouse key.<br>
     * 2) Right Mouse Sim Key (default: ]) = Simulates right mouse key.<br>
     * 3) Mouse Wheel Scroll Up (default: \) = Simulates middle mouse key.<br>
     * 4) Mouse Wheel Scroll Up (default: ;) = Simulates mouse wheel scroll up.<br>
     * 5) Mouse Wheel Scroll Down (default: ') = Simulates mouse wheel scroll down.\
     */
    private void initializeMouseSimulationKeybindings() {
        mouseSimulationLeftMouseKey = new KeyBinding(
                "minecraft_access.keys.mouse_simulation.left_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                MOUSE_SIMULATION_KEY
        );
        KeyMappingRegistry.register(mouseSimulationLeftMouseKey);

        mouseSimulationRightMouseKey = new KeyBinding(
                "minecraft_access.keys.mouse_simulation.right_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                MOUSE_SIMULATION_KEY
        );
        KeyMappingRegistry.register(mouseSimulationRightMouseKey);

        mouseSimulationMiddleMouseKey = new KeyBinding(
                "minecraft_access.keys.mouse_simulation.middle_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_BACKSLASH,
                MOUSE_SIMULATION_KEY
        );
        KeyMappingRegistry.register(mouseSimulationMiddleMouseKey);

        mouseSimulationScrollUpKey = new KeyBinding(
                "minecraft_access.keys.mouse_simulation.scroll_up_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_SEMICOLON,
                MOUSE_SIMULATION_KEY
        );
        KeyMappingRegistry.register(mouseSimulationScrollUpKey);

        mouseSimulationScrollDownKey = new KeyBinding(
                "minecraft_access.keys.mouse_simulation.scroll_down_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_APOSTROPHE,
                MOUSE_SIMULATION_KEY
        );
        KeyMappingRegistry.register(mouseSimulationScrollDownKey);
    }

    /**
     * 1. Open the AreaMap menu (default: F6)
     */
    private void initializeAreaMapKeybindings() {
        areaMapMenuKey = new KeyBinding(
                "minecraft_access.keys.area_map.menu_key",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F6,
                AREA_MAP_GROUP_KEY
        );
        KeyMappingRegistry.register(areaMapMenuKey);
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

        narratorMenuHotKey = new KeyBinding(
                "minecraft_access.keys.other.narrator_menu_hot_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_B,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(narratorMenuHotKey);

        directionNarrationKey = new KeyBinding(
                "minecraft_access.keys.other.facing_direction_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_H,
                OTHER_GROUP_TRANSLATION_KEY
        );
        KeyMappingRegistry.register(directionNarrationKey);
    }

}
