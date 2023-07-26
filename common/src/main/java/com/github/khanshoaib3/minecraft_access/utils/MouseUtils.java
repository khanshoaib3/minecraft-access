package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.sun.jna.Library;
import com.sun.jna.Native;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

import java.util.Timer;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

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
        // fix the https://github.com/khanshoaib3/minecraft-access/issues/65
        if (OsUtils.isWindows()) {
            try {
                // with a little bit of waiting, everything is ok now.
                // I've tried to set the value to 10, and it doesn't always work, 20 is fine.
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (Exception ignored) {
            }
        }
        leftClick();
    }

    /**
     * Move the mouse to the given pixel location and then perform right click.
     * @param x the x position of the pixel location
     * @param y the y position of the pixel location
     */
    @SuppressWarnings("unused")
    public static void moveAndRightClick(int x, int y) {
        move(x, y);
        // fix the https://github.com/khanshoaib3/minecraft-access/issues/65
        if (OsUtils.isWindows()) {
            try {
                // with a little bit of waiting, everything is ok now.
                // I've tried to set the value to 10, and it doesn't always work, 20 is fine.
                TimeUnit.MILLISECONDS.sleep(20);
            } catch (Exception ignored) {
            }
        }
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
     * Move the mouse to the given pixel location after a dela.
     * @param x the x position of the pixel location
     * @param y the y position of the pixel location
     * @param delay delay amount in milliseconds
     */
    public static void moveAfterDelay(int x, int y, int delay){
        try {
            MainClass.infoLog("Moving mouse to x:%d y:%d after %d milliseconds".formatted(x, y, delay));
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    move(x, y);
                }
            };
            new Timer().schedule(timerTask, delay);
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
            int x = (int) minecraftClient.mouse.getX(), y = (int) minecraftClient.mouse.getY();
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
     * Perform middle click at the current pixel location.
     */
    public static void middleClick() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null)
            return;

        try {
            int x = (int) minecraftClient.mouse.getX(), y = (int) minecraftClient.mouse.getY();
            MainClass.infoLog("Performing middle click at x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool click 2");
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                mainInterface.mouse_event(MouseEventFlags.MIDDLEDOWN.getValue(), 0, 0, 0, 0);
                mainInterface.mouse_event(MouseEventFlags.MIDDLEUP.getValue(), 0, 0, 0, 0);
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered on performing middle mouse click.");
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
            int x = (int) minecraftClient.mouse.getX(), y = (int) minecraftClient.mouse.getY();
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
     * Performs mouse scroll up
     */
    public static void scrollUp(){
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null)
            return;

        try {
            MainClass.infoLog("Performing scroll up");

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool click 4");
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                mainInterface.mouse_event(MouseEventFlags.WHEEL.getValue(), 0, 0, 120, 0);
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered on performing scroll up.");
            e.printStackTrace();
        }
    }

    /**
     * Performs mouse scroll down
     */
    public static void scrollDown(){
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null)
            return;

        try {
            MainClass.infoLog("Performing scroll down");

            if (OsUtils.isLinux()) {
                Runtime.getRuntime().exec("xdotool click 5");
            }

            if (OsUtils.isWindows()) {
                if (mainInterface == null) initializeUser32dll();

                mainInterface.mouse_event(MouseEventFlags.WHEEL.getValue(), 0, 0, -120, 0);
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered on performing scroll up.");
            e.printStackTrace();
        }
    }

    public record Coordinates(int x, int y) {
    }

    public static Coordinates calcRealPositionOfWidget(int x, int y) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client == null) return new Coordinates(x, y);
        Window window = client.getWindow();
        if (window == null) return new Coordinates(x, y);

        int realX = (int) (window.getX() + (x * window.getScaleFactor()));
        int realY = (int) (window.getY() + (y * window.getScaleFactor()));
        return new Coordinates(realX, realY);
    }

    public static void move(Coordinates coordinates) {
        move(coordinates.x(), coordinates.y());
    }

    /**
     * Preform a mouse event at the given location
     *
     * @param x        x coordinate
     * @param y        y coordinate
     * @param consumer event
     */
    public static void performAt(int x, int y, Consumer<Coordinates> consumer) {
        consumer.accept(calcRealPositionOfWidget(x, y));
    }

    /**
     * Initializes the User32.dll for windows
     */
    private static void initializeUser32dll() {
        if (!OsUtils.isWindows())
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
        // https://stackoverflow.com/questions/37262822/c-sharp-simulate-mouse-wheel-down
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
        RIGHTUP(0x00000010),
        WHEEL(0x00000800);


        private final int value;

        MouseEventFlags(final int newValue) {
            value = newValue;
        }

        public int getValue() {
            return value;
        }
    }
}
