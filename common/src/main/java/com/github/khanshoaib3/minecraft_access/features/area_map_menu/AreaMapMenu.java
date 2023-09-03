package com.github.khanshoaib3.minecraft_access.features.area_map_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.KeyUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import net.minecraft.client.MinecraftClient;

/**
 * This menu gives user a bird eye view of surrounding area.
 * It plays the role of the Map function in other games.
 * User can move a virtual cursor to explore the area (speak out pointed block's information).
 * Open the AreaMap menu with F6.
 */
@SuppressWarnings("unused")
public class AreaMapMenu {
    private static final AreaMapMenu instance;

    private static final Keystroke menuKey;

    static {
        instance = new AreaMapMenu();

        // config keystroke conditions
        KeyBindingsHandler kbh = KeyBindingsHandler.getInstance();
        menuKey = new Keystroke(() -> KeyUtils.isAnyPressed(kbh.areaMapMenuKey));
    }

    public static AreaMapMenu getInstance() {
        return instance;
    }

    public void update() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) return;
            if (client.player == null) return;

            if (client.currentScreen instanceof AreaMapMenuGUI) {
                if (handleInMenuActions()) return;
            }

            // other menus is opened
            if (client.currentScreen != null) return;

            if (menuKey.isPressing()) openAreaMapMenu();

        } catch (Exception e) {
            MainClass.errorLog("An error occurred in AreaMapMenu.");
            e.printStackTrace();
        }
    }

    private void openAreaMapMenu() {
        MinecraftClient.getInstance().setScreen(new AreaMapMenuGUI());
    }

    /**
     * @return return early if the menu is closed.
     */
    private boolean handleInMenuActions() {
        return true;
    }
}
