package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.gui.ConfigScreen;
import com.github.khanshoaib3.minecraft_access.gui.NarratorMenuGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class NarratorMenu {
    public final KeyBinding narratorMenuKey; //TODO create a separate class for initializing key binds

    public NarratorMenu(){
        narratorMenuKey = new KeyBinding(
                "minecraft_access.keys.other.narrator_menu_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                "minecraft_access.keys.other.group_name"
        );
    }

    public void update(){
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            boolean isNarratorMenuKeyPressed = InputUtil.isKeyPressed(
                    minecraftClient.getWindow().getHandle(),
                    InputUtil.fromTranslationKey(narratorMenuKey.getBoundKeyTranslationKey()).getCode()
            );

            if(isNarratorMenuKeyPressed){
                Screen screen = new ConfigScreen(new NarratorMenuGUI(minecraftClient.player, minecraftClient), "f4_menu");
                minecraftClient.setScreen(screen); // post 1.18
//                minecraftClient.openScreen(screen); // pre 1.18
            }
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in NarratorMenu.");
            e.printStackTrace();
        }
    }
}
