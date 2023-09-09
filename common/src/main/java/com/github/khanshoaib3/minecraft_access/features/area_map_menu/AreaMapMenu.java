package com.github.khanshoaib3.minecraft_access.features.area_map_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.AreaMapConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.KeyUtils;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.MenuKeyStroke;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.math.BlockPos;

/**
 * This menu gives user a bird eye view of surrounding area.
 * It plays the role of the Map function in other games.
 * User can move a virtual cursor to explore the area (speak out pointed block's information).
 * Open the AreaMap menu with F6.
 */
@SuppressWarnings("unused")
public class AreaMapMenu {
    private static final AreaMapMenu instance;

    private static final MenuKeyStroke menuKey;
    private boolean enabled;
    private BlockPos cachedPlayerPos;
    private BlockPos cursor;

    static {
        instance = new AreaMapMenu();

        // config keystroke conditions
        KeyBindingsHandler kbh = KeyBindingsHandler.getInstance();
        menuKey = new MenuKeyStroke(() -> KeyUtils.isAnyPressed(kbh.areaMapMenuKey));
    }

    public static AreaMapMenu getInstance() {
        return instance;
    }

    public void update() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) return;
            if (client.player == null) return;

            // functional core, imperative shell, for easier testing
            execute(client);
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in AreaMapMenu.");
            e.printStackTrace();
        }
    }

    public void execute(MinecraftClient client) {
        updateConfigs();
        if (!enabled) return;

        if (client.currentScreen == null) {
            if (menuKey.canOpenMenu()) {
                openAreaMapMenu();
                updateMapStates();
            }
        } else {
            if (client.currentScreen instanceof AreaMapMenuGUI) {
                if (menuKey.closeMenuIfMenuKeyPressing()) return;
                handleInMenuActions();
            } else {
                // other menus is opened currently, won't open the menu
                return;
            }
        }

        menuKey.updateStateForNextTick();
    }

    private void updateConfigs() {
        AreaMapConfigMap map = AreaMapConfigMap.getInstance();
        this.enabled = map.isEnabled();
    }

    private void openAreaMapMenu() {
        MinecraftClient.getInstance().setScreen(new AreaMapMenuGUI());
    }

    private void updateMapStates() {
        BlockPos currentPlayerPos = PlayerPositionUtils.getPlayerBlockPosition().orElseThrow();
        // player haven't moved since last menu opening, no need to update
        if(currentPlayerPos.equals(cachedPlayerPos)) return;

        cachedPlayerPos = currentPlayerPos;
        cursor = currentPlayerPos;
    }

    private void handleInMenuActions() {
    }
}
