package com.github.khanshoaib3.minecraft_access;

import com.github.khanshoaib3.minecraft_access.features.MenuFix;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderInterface;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class MainClass implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("minecraft_access");
    private static ScreenReaderInterface screenReader = null;

    public static boolean debugMode = true; // TODO add option to toggle this

    @Override
    public void onInitialize() {
        String msg = "Initializing Minecraft Access";
        infoLog(msg);

        setScreenReader(ScreenReaderController.getAvailable());
        if (getScreenReader() != null && getScreenReader().isInitialized())
            getScreenReader().say(msg, true);

        System.setProperty("java.awt.headless","false");
        ClientTickEvents.END_CLIENT_TICK.register(this::clientTickEventsMethod);

        // This executes when minecraft closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (getScreenReader() != null && getScreenReader().isInitialized())
                getScreenReader().closeScreenReader();
        }, "Shutdown-thread"));
    }

    /**
     * This method gets called at the end of every tick
     *
     * @param minecraftClient The current minecraft client object
     */
    private void clientTickEventsMethod(MinecraftClient minecraftClient) {
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
