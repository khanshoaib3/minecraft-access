package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.sun.jna.Library;
import com.sun.jna.Native;
import net.minecraft.client.MinecraftClient;

public class MouseUtils {
    private static user32dllInterface mainInterface = null;

    public static void moveAndLeftClick(int x, int y) {
        move(x, y);
        leftClick();
    }

    public static void moveAndRightClick(int x, int y) {
        move(x, y);
        rightClick();
    }

    public static void move(int x, int y) {
        try {
            MainClass.infoLog("Moving mouse to x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool mousemove %d %d".formatted(x, y));
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                if (!mainInterface.SetCursorPos(x, y))
                    MainClass.errorLog("Error encountered on moving mouse.");
            }
        } catch (Exception e) {
            MainClass.errorLog("Error encountered on moving mouse.");
            e.printStackTrace();
        }
    }

    public static void leftClick() {
        try {
            int x = (int) MinecraftClient.getInstance().mouse.getX();
            int y = (int) MinecraftClient.getInstance().mouse.getY();
            MainClass.infoLog("Performing left click at x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool click 1");
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                mainInterface.mouse_event(MouseEventFlags.LEFTDOWN.getValue(), 0, 0, 0, 0);
                mainInterface.mouse_event(MouseEventFlags.LEFTUP.getValue(), 0, 0, 0, 0);
            }
        } catch (Exception e) {
            MainClass.errorLog("Error encountered on performing left mouse click.");
            e.printStackTrace();
        }
    }


    public static void rightClick() {
        try {
            int x = (int) MinecraftClient.getInstance().mouse.getX();
            int y = (int) MinecraftClient.getInstance().mouse.getY();
            MainClass.infoLog("Performing right click at x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool click 3");
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                mainInterface.mouse_event(MouseEventFlags.RIGHTDOWN.getValue(), 0, 0, 0, 0);
                mainInterface.mouse_event(MouseEventFlags.RIGHTUP.getValue(), 0, 0, 0, 0);
            }
        } catch (Exception e) {
            MainClass.errorLog("Error encountered on performing right mouse click.");
            e.printStackTrace();
        }
    }

    private static void initializeUser32dll() {
        try {
            mainInterface = Native.load("User32.dll", user32dllInterface.class);
        } catch (Exception e) {
            MainClass.errorLog("Error encountered while initializing User32.dll");
            e.printStackTrace();
        }
    }

    private interface user32dllInterface extends Library {
        boolean SetCursorPos(int x, int y);

        void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);
    }

    private enum MouseEventFlags {
        LEFTDOWN(0x00000002),
        LEFTUP(0x00000004),
        MIDDLEDOWN(0x00000020),
        MIDDLEUP(0x00000040),
        MOVE(0x00000001),
        ABSOLUTE(0x00008000),
        RIGHTDOWN(0x00000008),
        RIGHTUP(0x00000010);

        private final int value;

        MouseEventFlags(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }
}
