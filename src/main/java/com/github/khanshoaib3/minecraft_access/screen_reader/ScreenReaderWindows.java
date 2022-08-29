package com.github.khanshoaib3.minecraft_access.screen_reader;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.OsUtils;
import com.sun.jna.Library;
import com.sun.jna.Native;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenReaderWindows implements ScreenReaderInterface {
    private nvdaControllerClientInterface mainNVDAInterface = null;

    @Override
    public void initializeScreenReader() {
        Path nvdaPath;
        if (OsUtils.is64Bit())
            nvdaPath = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "nvdaControllerClient64.dll");
        else
            nvdaPath = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "nvdaControllerClient32.dll");

        // Try loading nvda
        if (!Files.exists(nvdaPath)) {
            MainClass.infoLog("nvdaControllerClient not installed, now trying JAWS.");
        } else {
            MainClass.infoLog("Initializing nvda controller client for windows at: " + nvdaPath.toString());
            nvdaControllerClientInterface instance = Native.load(nvdaPath.toString(), nvdaControllerClientInterface.class);
            int re = instance.nvdaController_testIfRunning();
            if (re == 0) {
                MainClass.infoLog("nvda controller client loaded");
                mainNVDAInterface = instance;
            } else {
                MainClass.infoLog("Unable to load nvda controller client, now trying JAWS.");
            }
        }
    }

    @Override
    public boolean isInitialized() {
        return (mainNVDAInterface != null);
    }

    @Override
    public void say(String text, boolean interrupt) {
        if (mainNVDAInterface != null) {
            if (interrupt) {
                int re = mainNVDAInterface.nvdaController_cancelSpeech();
                if (re != 0)
                    MainClass.infoLog("Error while cancelling speech for NVDA");
            }
            int re = mainNVDAInterface.nvdaController_speakText(text);
            if (re == 0)
                MainClass.infoLog("Speaking(interrupt:" + interrupt + ")= " + text);
            else
                MainClass.infoLog("Unable to speak");
        }
    }

    @Override
    public void closeScreenReader() {
    }

    private interface nvdaControllerClientInterface extends Library {
        int nvdaController_testIfRunning();

        int nvdaController_cancelSpeech();

        int nvdaController_speakText(String text);
    }
}
