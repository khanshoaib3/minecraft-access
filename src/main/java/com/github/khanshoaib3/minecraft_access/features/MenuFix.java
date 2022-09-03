package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Moves the mouse to the top left of the screen and then performs left click.
 * This fixes the bug in which the mouse cursor interrupts when navigating through the screen elements
 * which results in infinite speaking of `Screen element x out of x` by the narrator
 */
public class MenuFix {
    /**
     * Prevents executing this fix for the title screen the first time
     */
    private static String prevScreenTitle = "title screen";
    /**
     * The list of screens for which this fix will execute when opened
     */
    private static final List<String> menuList = new ArrayList<>() {{
        add("title screen");
        add("options");
        add("controls");
        add("online options");
        add("skin customization");
        add("music & sound options");
        add("video settings");
        add("language...");
        add("chat settings...");
        add("select resource packs");
        add("accessibility settings...");
        add("mouse settings");
        add("key binds");
        add("select world");
        add("create new world");
        add("play multiplayer");
        add("direct connection");
        add("edit server info");
    }};

    /**
     * This method gets called at the end of every tick.
     * @param minecraftClient Current MinecraftClient instance
     */
    public static void update(MinecraftClient minecraftClient) {
        if (minecraftClient.currentScreen == null)
            return;

        try {
            String title = minecraftClient.currentScreen.getTitle().getString();
            if (menuList.contains(title.toLowerCase())) {
                if (!prevScreenTitle.equalsIgnoreCase(title)) {
                    MainClass.infoLog(title + " opened, now moving the mouse cursor.");
                    moveMouseCursor(minecraftClient);
                    prevScreenTitle = title;
                }

                boolean isRPressed = (InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(),
                        InputUtil.fromTranslationKey("key.keyboard.r").getCode()));
                if (isRPressed)
                    moveMouseCursor(minecraftClient);
            }
            else
                MainClass.infoLog(title);
        } catch (Exception e) {
            MainClass.errorLog("Error encountered while running the menu fix feature\n");
            e.printStackTrace();
        }
    }

    /**
     * Moves the mouse cursor to x=1 y=1 pixel location relative the Minecraft window location
     * @param minecraftClient Current MinecraftClient instance
     */
    private static void moveMouseCursor(MinecraftClient minecraftClient) {
        try {
            int movePosX = 1, movePosY = 1;
            if (!minecraftClient.options.getFullscreen().getValue()) {
                movePosX += minecraftClient.getWindow().getX();
                movePosY += minecraftClient.getWindow().getY();
            }

            MouseUtils.moveAndLeftClick(movePosX, movePosY);
        } catch (Exception e) {
            MainClass.errorLog("Error encountered while moving the mouse for the menu fix feature\n");
            e.printStackTrace();
        }
    }
}
