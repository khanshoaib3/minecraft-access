package com.github.khanshoaib3.minecraft_access.screen_reader;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.sun.jna.Library;
import com.sun.jna.Native;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class ScreenReaderWindowsTolk implements ScreenReaderInterface {
    tolkInterface mainInstance = null;

    @Override
    public void initializeScreenReader() {
        Path path = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "Tolk.dll");
        if (!Files.exists(path)) {
            MainClass.infoLog("Tolk not installed!");
            return;
        }

        MainClass.infoLog("Initializing Tolk for windows at: " + path);
        tolkInterface instance = Native.load(path.toString(), tolkInterface.class);
        instance.Tolk_Load();
        boolean isLoaded = instance.Tolk_IsLoaded() && instance.Tolk_HasSpeech();
        if (isLoaded) {
            MainClass.infoLog("Successfully initialized screen reader");
            mainInstance = instance;
        } else {
            MainClass.infoLog("Unable to initialize screen reader");
        }
    }

    @Override
    public boolean isInitialized() {
        return mainInstance != null;
    }

    @Override
    public void say(String text, boolean interrupt) {
        if(mainInstance==null)
            return;

        char[] ch = new char[text.length() + 1]; // Last character must be null so NVDA decodes the text correctly
        for (int i = 0; i < text.length(); i++) {
            ch[i] = text.charAt(i);
        }
        boolean re = mainInstance.Tolk_Speak(ch, interrupt);
        if (re)
            MainClass.infoLog("Speaking(interrupt:" + interrupt + ")= " + text);
        else
            MainClass.infoLog("Unable to speak");
    }

    @Override
    public void closeScreenReader() {
        if(mainInstance==null)
            return;

        mainInstance.Tolk_Unload();
    }

    private interface tolkInterface extends Library {
        void Tolk_Load();

        boolean Tolk_IsLoaded();

        boolean Tolk_HasSpeech();

        void Tolk_Unload();

        boolean Tolk_Speak(char[] text, boolean interrupt);
    }
}
