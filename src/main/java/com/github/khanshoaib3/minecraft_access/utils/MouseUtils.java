package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;

import java.io.IOException;

public class MouseUtils {
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

            if (OsUtils.isLinux())
                Runtime.getRuntime().exec("xdotool mousemove %d %d".formatted(x, y));
            else if (OsUtils.isWindows()) {
            }
        } catch (IOException e) {
            MainClass.errorLog("Error encountered on moving mouse.");
            e.printStackTrace();
        }
    }

    public static void leftClick() {
        try {
            int x = (int) MinecraftClient.getInstance().mouse.getX();
            int y = (int) MinecraftClient.getInstance().mouse.getY();
            MainClass.infoLog("Performing left click at x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux())
                Runtime.getRuntime().exec("xdotool click 1");
            else if (OsUtils.isWindows()) {
            }
        } catch (IOException e) {
            MainClass.errorLog("Error encountered on performing left mouse click.");
            e.printStackTrace();
        }
    }


    public static void rightClick() {
        try {
            int x = (int) MinecraftClient.getInstance().mouse.getX();
            int y = (int) MinecraftClient.getInstance().mouse.getY();
            MainClass.infoLog("Performing right click at x:%d y:%d".formatted(x, y));

            if (OsUtils.isLinux())
                Runtime.getRuntime().exec("xdotool click 3");
            else if (OsUtils.isWindows()) {
            }
        } catch (IOException e) {
            MainClass.errorLog("Error encountered on performing right mouse click.");
            e.printStackTrace();
        }
    }
}
