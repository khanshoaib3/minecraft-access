package com.github.khanshoaib3.minecraft_access.screen_reader;

import com.github.khanshoaib3.minecraft_access.utils.OsUtils;

public class ScreenReaderController {
    public static ScreenReaderInterface getAvailable() {
        if (OsUtils.isLinux()) {
            ScreenReaderLinux screenReaderLinux = new ScreenReaderLinux();
            screenReaderLinux.initializeScreenReader();
            return screenReaderLinux;
        }

        if (OsUtils.isWindows()) {
            ScreenReaderWindowsTolk screenReaderWindows = new ScreenReaderWindowsTolk();
            screenReaderWindows.initializeScreenReader();
            return screenReaderWindows;
        }

        return null;
    }
}
