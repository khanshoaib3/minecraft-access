package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.sun.jna.Library;
import com.sun.jna.Native;
import net.minecraft.client.MinecraftClient;

/**
 * Contains functions to simulate mouse events.
 */
public class MouseUtils {
    private static user32dllInterface mainInterface = null;

    /**
     * Move the mouse to the given pixel location and then perform left click.
     * @param x the x position of the pixel location
     * @param y the y position of the pixel location
     */
    public static void moveAndLeftClick(int x, int y) {
        move(x, y);
        leftClick();
    }

    /**
     * Move the mouse to the given pixel location and then perform right click.
     * @param x the x position of the pixel location
     * @param y the y position of the pixel location
     */
    public static void moveAndRightClick(int x, int y) {
        move(x, y);
        rightClick();
    }

    /**
     * Move the mouse to the given pixel location.
     * @param x the x position of the pixel location
     * @param y the y position of the pixel location
     */
    public static void move(int x, int y) {
        try {
            MainClass.infoLog("Moving mouse to x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool mousemove %d %d".formatted(x, y));
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                if (!mainInterface.SetCursorPos(x, y))
                    MainClass.errorLog("\nError encountered on moving mouse.");
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered on moving mouse.");
            e.printStackTrace();
        }
    }

    /**
     * Perform left click at the current pixel location.
     */
    public static void leftClick() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null)
            return;

        try {
            int x = 1, y = 1;
            if (!minecraftClient.options.getFullscreen().getValue()) {
                x += minecraftClient.getWindow().getX();
                y += minecraftClient.getWindow().getY();
            }
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
            MainClass.errorLog("\nError encountered on performing left mouse click.");
            e.printStackTrace();
        }
    }


    /**
     * Perform right click at the current pixel location.
     */
    public static void rightClick() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null)
            return;

        try {
            int x = 1, y = 1;
            if (!minecraftClient.options.getFullscreen().getValue()) {
                x += minecraftClient.getWindow().getX();
                y += minecraftClient.getWindow().getY();
            }
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
            MainClass.errorLog("\nError encountered on performing right mouse click.");
            e.printStackTrace();
        }
    }

    /**
     * Initializes the User32.dll for windows
     */
    private static void initializeUser32dll() {
        if(!OsUtils.isWindows())
            return;

        try {
            mainInterface = Native.load("User32.dll", user32dllInterface.class);
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered while initializing User32.dll");
            e.printStackTrace();
        }
    }

    /**
     * Contains definition for SetCursorPos() and mouse_event() of User32.dll
     */
    private interface user32dllInterface extends Library {
        // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-setcursorpos
        boolean SetCursorPos(int x, int y);

        // https://docs.microsoft.com/en-us/windows/win32/api/winuser/nf-winuser-mouse_event?source=recommendations
        // https://stackoverflow.com/questions/8739523/directing-mouse-events-dllimportuser32-dll-click-double-click
        void mouse_event(int dwFlags, int dx, int dy, int dwData, int dwExtraInfo);
    }

    /**
     * Flags used in the mouse_event function of User32.dll
     */
    @SuppressWarnings("unused")
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
