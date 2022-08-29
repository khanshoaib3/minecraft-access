package com.github.khanshoaib3.minecraft_access.screen_reader;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.OsUtils;

public class ScreenReaderController {
    public static ScreenReaderInterface getAvailable() {
        if (OsUtils.isLinux()) {
            ScreenReaderLinux screenReaderLinux = new ScreenReaderLinux();
            screenReaderLinux.initializeScreenReader();
            return screenReaderLinux;
        }

        if (OsUtils.isWindows()) {
            ScreenReaderWindows screenReaderWindows = new ScreenReaderWindows();
            screenReaderWindows.initializeScreenReader();
            return screenReaderWindows;
        }

        return null;
    }
}
