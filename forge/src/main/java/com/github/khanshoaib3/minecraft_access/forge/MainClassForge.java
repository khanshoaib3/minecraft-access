package com.github.khanshoaib3.minecraft_access.forge;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.features.CameraControls;
import com.github.khanshoaib3.minecraft_access.features.InventoryControls.InventoryControls;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import dev.architectury.platform.forge.EventBuses;
import com.github.khanshoaib3.minecraft_access.ExampleMod;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(ExampleMod.MOD_ID)
public class MainClassForge {
    public MainClassForge() {
        String msg = "Initializing Minecraft Access";
        MainClass.infoLog(msg);

        MainClass.setScreenReader(ScreenReaderController.getAvailable());
        if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized())
            MainClass.getScreenReader().say(msg, true);

        MainClass.cameraControls = new CameraControls();
        MainClass.inventoryControls = new InventoryControls();

//        ClientTickEvents.END_CLIENT_TICK.register(MainClass::clientTickEventsMethod);
//        HudRenderCallback.EVENT.register(MainClass::hudRenderCallbackMethod);

        // This executes when minecraft closes
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized())
                MainClass.getScreenReader().closeScreenReader();
        }, "Shutdown-thread"));
    }
}
