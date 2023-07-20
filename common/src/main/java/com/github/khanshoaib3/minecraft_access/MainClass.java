package com.github.khanshoaib3.minecraft_access;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.features.*;
import com.github.khanshoaib3.minecraft_access.features.inventory_controls.InventoryControls;
import com.github.khanshoaib3.minecraft_access.features.narrator_menu.NarratorMenu;
import com.github.khanshoaib3.minecraft_access.features.point_of_interest.LockingHandler;
import com.github.khanshoaib3.minecraft_access.features.point_of_interest.POIMarking;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderInterface;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.mojang.text2speech.Narrator;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.InputUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass {
    public static final String MOD_ID = "minecraft_access";
    private static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private static ScreenReaderInterface screenReader = null;

    public static Config config = null;

    public static CameraControls cameraControls = null;
    public static InventoryControls inventoryControls = null;
    public static ReadCrosshair readCrosshair = null;
    public static BiomeIndicator biomeIndicator = null;
    public static XPIndicator xpIndicator = null;
    public static FacingDirection facingDirection = null;
    public static PositionNarrator positionNarrator = null;
    public static HealthNHunger healthNHunger = null;
    public static PlayerWarnings playerWarnings = null;
    public static NarratorMenu narratorMenu = null;
    public static LockingHandler lockingHandler = null;
    public static FluidDetector fluidDetector = null;

    public static boolean isForge = false;
    public static boolean interrupt = true;

    private static boolean debugMode;
    private static boolean alreadyDisabledAdvancementKey = false;

    /**
     * Initializes the mod
     */
    public static void init() {
        config = Config.getInstance();
        Config.refresh();
        debugMode = config.getConfigMap().getOtherConfigsMap().isDebugMode();

        String msg = "Initializing Minecraft Access";
        MainClass.infoLog(msg);

        new AutoLibrarySetup().initialize();

        // It seems that Fabric loads classes in mod jars on-demand.
        // If the KeyBindingsHandler class is not invoked here, the static block of this class will not be executed.
        // After the game is initialized, invoking (what we did in the constructor of KeyBindingsHandler class) on
        // dev.architectury.registry.client.keymappings.KeyMappingRegistry.register
        // would throw an exception and terminate the game.
        var ignored = KeyBindingsHandler.getInstance();

        ScreenReaderController.refreshScreenReader();
        if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized())
            MainClass.getScreenReader().say(msg, true);

        MainClass.cameraControls = new CameraControls();
        MainClass.inventoryControls = new InventoryControls();
        MainClass.readCrosshair = new ReadCrosshair();
        MainClass.biomeIndicator = new BiomeIndicator();
        MainClass.xpIndicator = new XPIndicator();
        MainClass.facingDirection = new FacingDirection();
        MainClass.positionNarrator = new PositionNarrator();
        MainClass.healthNHunger = new HealthNHunger();
        MainClass.playerWarnings = new PlayerWarnings();
        MainClass.narratorMenu = new NarratorMenu();
        MainClass.lockingHandler = LockingHandler.getInstance();
        MainClass.fluidDetector = new FluidDetector();

        ClientTickEvent.CLIENT_POST.register(MainClass::clientTickEventsMethod);

        // This executes when minecraft closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized())
                MainClass.getScreenReader().closeScreenReader();
        }, "Shutdown-thread"));
    }

    /**
     * This method gets called at the end of every tick
     *
     * @param minecraftClient The current minecraft client object
     */
    public static void clientTickEventsMethod(MinecraftClient minecraftClient) {
        // update debug mode config
        debugMode = config.getConfigMap().getOtherConfigsMap().isDebugMode();

        // TODO change attack and use keys on startup and add startup features to config.json
        if (!MainClass.alreadyDisabledAdvancementKey && minecraftClient.options != null) {
            minecraftClient.options.advancementsKey.setBoundKey(InputUtil.fromTranslationKey("key.keyboard.unknown"));
            MainClass.alreadyDisabledAdvancementKey = true;
            infoLog("Unbound advancements key");
        }

        if (config.getConfigMap().getOtherConfigsMap().isMenuFixEnabled()) {
            MenuFix.update(minecraftClient);
        }

        // TODO Update these to singleton design pattern
        if (inventoryControls != null && config.getConfigMap().getInventoryControlsConfigMap().isEnabled())
            inventoryControls.update();

        if (cameraControls != null && config.getConfigMap().getCameraControlsConfigMap().isEnabled())
            cameraControls.update();

        if (readCrosshair != null && config.getConfigMap().getReadCrosshairConfigMap().isEnabled())
            readCrosshair.update();

        if (biomeIndicator != null && config.getConfigMap().getOtherConfigsMap().isBiomeIndicatorEnabled())
            biomeIndicator.update();

        if (xpIndicator != null && config.getConfigMap().getOtherConfigsMap().isXpIndicatorEnabled())
            xpIndicator.update();

        if (facingDirection != null && config.getConfigMap().getOtherConfigsMap().isFacingDirectionEnabled())
            facingDirection.update();

        if (positionNarrator != null && config.getConfigMap().getOtherConfigsMap().isPositionNarratorEnabled())
            positionNarrator.update();

        if (healthNHunger != null && config.getConfigMap().getOtherConfigsMap().isHealthNHungerEnabled())
            healthNHunger.update();

        if (playerWarnings != null && config.getConfigMap().getPlayerWarningConfigMap().isEnabled())
            playerWarnings.update();

        if (narratorMenu != null && config.getConfigMap().getNarratorMenuConfigMap().isEnabled())
            narratorMenu.update();

        POIMarking.getInstance().update();

        if (lockingHandler != null && config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isEnabled())
            lockingHandler.update();

        FallDetector.getInstance().update();

        MouseKeySimulation.getInstance().update();
    }

    public static void infoLog(String msg) {
        if (debugMode) LOGGER.info(msg);
    }

    public static void errorLog(String msg) {
        LOGGER.error(msg);
    }

    public static ScreenReaderInterface getScreenReader() {
        return MainClass.screenReader;
    } //TODO remove this

    public static void setScreenReader(ScreenReaderInterface screenReader) {
        MainClass.screenReader = screenReader;
    }

    public static void speakWithNarrator(String text, boolean interrupt) {
        MainClass.interrupt = interrupt;
        if (isForge) {
            MinecraftClient.getInstance().getNarratorManager().narrate(text);
            return;
        }

        Narrator.getNarrator().say(text, interrupt);
    }
}
