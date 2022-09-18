package com.github.khanshoaib3.minecraft_access;

import com.github.khanshoaib3.minecraft_access.features.CameraControls;
import com.github.khanshoaib3.minecraft_access.features.InventoryControls.InventoryControls;
import com.github.khanshoaib3.minecraft_access.features.MenuFix;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderInterface;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class MainClass {
    public static final String MOD_ID = "minecraft_access";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static ScreenReaderInterface screenReader = null;
    public static CameraControls cameraControls = null;
    public static InventoryControls inventoryControls = null;

    public static boolean debugMode = true; // TODO add option to toggle this

    public static void hudRenderCallbackMethod() {
        if (inventoryControls != null) inventoryControls.update();

        if (cameraControls != null) cameraControls.update();
    }

    /**
     * This method gets called at the end of every tick
     *
     * @param minecraftClient The current minecraft client object
     */
    public static void clientTickEventsMethod(MinecraftClient minecraftClient) {
        MenuFix.update(minecraftClient);
    }

    public static void infoLog(String msg) {
        if (debugMode) LOGGER.info(msg);
    }

    public static void errorLog(String msg) {
        LOGGER.error(msg);
    }

    public static ScreenReaderInterface getScreenReader() {
        return MainClass.screenReader;
    }

    public static void setScreenReader(ScreenReaderInterface screenReader) {
        MainClass.screenReader = screenReader;
    }
}
