package com.github.khanshoaib3.minecraft_access.features.area_map_menu;

/**
 * This menu gives user a bird eye view of surrounding area.
 * It plays the role of the Map function in other games.
 * User can move a virtual cursor to explore the area (speak out pointed block's information).
 * Open the AreaMap menu with F6.
 */
public class AreaMapMenu {
    private static final AreaMapMenu instance;

    static {
        instance = new AreaMapMenu();
    }

    private AreaMapMenu() {
    }

    public static AreaMapMenu getInstance() {
        return instance;
    }
}
