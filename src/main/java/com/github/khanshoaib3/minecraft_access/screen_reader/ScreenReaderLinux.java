package com.github.khanshoaib3.minecraft_access.screen_reader;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import net.fabricmc.loader.api.FabricLoader;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class ScreenReaderLinux implements ScreenReaderInterface {
    private libSpeechdWrapperInterface mainInstance = null;

    @Override
    public void initializeScreenReader() {
        Path path = Paths.get(FabricLoader.getInstance().getGameDir().toString(), "mods", "libspeechdwrapper.so");
        if(!Files.exists(path))
        {
            MainClass.infoLog("libspeechdwrapper not installed!");
            return;
        }

        MainClass.infoLog("Initializing libspeechdwrapper for linux at: " + path.toString());
        libSpeechdWrapperInterface instance = Native.load(path.toString(), libSpeechdWrapperInterface.class);
        int re = instance.Initialize();

        // Try initializing again after 3 seconds (when no screen reader is running, the library is unable to initialize the installed screen reader the first time
        if (re == -1) {
            try {
                MainClass.infoLog("Unable to initialize screen reader, trying again in 3 seconds.");
                TimeUnit.SECONDS.sleep(3);
                re = instance.Initialize();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        if (re == 1) {
            mainInstance = instance;
            MainClass.infoLog("Successfully initialized screen reader");
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
        if (mainInstance == null)
            return;

        libSpeechdWrapperInterface.GoString.ByValue str = new libSpeechdWrapperInterface.GoString.ByValue();
        str.p = text;
        str.n = text.length();

        int re = mainInstance.Speak(str, interrupt);
        if (re == 1) {
            MainClass.infoLog("Speaking(interrupt:" + interrupt + ")= " + text);
        } else {
            MainClass.infoLog("Unable to speak");
        }
    }

    @Override
    public void closeScreenReader() {
        if (mainInstance == null)
            return;

        int re = mainInstance.Close();
        if (re == 1) {
            MainClass.infoLog("Successfully closed screen reader");
        } else {
            MainClass.infoLog("Unable to close screen reader");
        }
    }

    public interface libSpeechdWrapperInterface extends Library {
        // https://github.com/vladimirvivien/go-cshared-examples
        class GoString extends Structure {
            public static class ByValue extends GoString implements Structure.ByValue {
            }

            public String p;
            public long n;

            @SuppressWarnings({"unchecked", "rawtypes"})
            protected List getFieldOrder() {
                return Arrays.asList("p", "n");
            }
        }

        int Initialize();

        int Speak(GoString.ByValue text, boolean interrupt);

        int Close();
    }
}
