package com.github.khanshoaib3.minecraft_access.screen_reader;

import com.github.khanshoaib3.minecraft_access.MainClass;

public class ScreenReaderController {
    public static ScreenReaderInterface getAvailable()
    {
        ScreenReaderLinux screenReaderLinux = new ScreenReaderLinux();
        screenReaderLinux.initializeScreenReader();
        MainClass.infoLog(screenReaderLinux.isInitialized()+"");
        return screenReaderLinux;
    }
}
