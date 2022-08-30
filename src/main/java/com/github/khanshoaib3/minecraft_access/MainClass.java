package com.github.khanshoaib3.minecraft_access;

import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class MainClass implements ModInitializer {
    private static final Logger LOGGER = LoggerFactory.getLogger("minecraft_access");
    private static ScreenReaderInterface screenReader = null;

    @Override
    public void onInitialize() {
        String msg = "Initializing Minecraft Access";
        infoLog(msg);

        setScreenReader(ScreenReaderController.getAvailable());
        if (getScreenReader() != null && getScreenReader().isInitialized())
            getScreenReader().say(msg, true);

        // This executes when minecraft closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (getScreenReader() != null && getScreenReader().isInitialized())
                getScreenReader().closeScreenReader();
        }, "Shutdown-thread"));
    }

    public static void infoLog(String msg) {
        //TODO add debug enabling/disabling logic here
        LOGGER.info(msg);
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
