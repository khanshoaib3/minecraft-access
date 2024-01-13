package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import org.slf4j.Logger;

/**
 * After struggling with achieving "print mod's DEBUG log in console while fabric/forge's log level stay on INFO",
 * I come up with this ugly implementation.
 * <p>
 * Loom sets the log4j config in dev, in prod it might be dictated by the launcher or just the game itself.
 * But <a href="https://github.com/FabricMC/fabric-loom/issues/648#issuecomment-1890456832">failed to replace archy/fabric loom's log4j config</a>.
 * Simply adding "-Dfabric.log.level=debug" on JVM will make huge amount of fabric's DEBUG log flowing in the console,
 * make it hard to find our mod's log.
 */
public class Log {
    private static boolean debugMode;

    /**
     * Log message in INFO level if debug mode config is enabled.
     */
    public static void debug(Logger logger, String message) {
        if (debugMode) {
            // Use INFO level to make sure we can see it in console with default INFO log level,
            // but following the debug mode config value.
            // Add "DEBUG" so the color of these logs will be gray, I don't know why, but it's nice.
            logger.info("DEBUG| " + message);
        }
    }

    public static void updateDebugModeConfig() {
        debugMode = OtherConfigsMap.getInstance().isDebugMode();
    }
}
