package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

/**
 * Adds a key bind to narrate/speak the player's health and hunger.
 * - Speak Player Health and Hunger Key (default: R) = Speaks the health and hunger.
 */
public class HealthNHunger {
    public final KeyBinding healthNHungerNarrationKey; //TODO create a separate class for initializing key binds

    public HealthNHunger(){
        healthNHungerNarrationKey = new KeyBinding(
                "minecraft_access.keys.other.health_n_hunger_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                "minecraft_access.keys.other.group_name"
        );
    }

    public void update(){
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;

            boolean isHealthNHungerNarrationKeyPressed = InputUtil.isKeyPressed(
                    minecraftClient.getWindow().getHandle(),
                    InputUtil.fromTranslationKey(healthNHungerNarrationKey.getBoundKeyTranslationKey()).getCode()
            );
            if (!isHealthNHungerNarrationKeyPressed) return;

            double health = minecraftClient.player.getHealth();
            double hunger = minecraftClient.player.getHungerManager().getFoodLevel();

            health = (double) Math.round((health / 2) * 10) / 10;
            hunger = (double) Math.round((hunger / 2) * 10) / 10;

            String toSpeak = I18n.translate("minecraft_access.healthHunger.format", health, hunger);
            MainClass.speakWithNarrator(toSpeak, true);
        } catch (Exception e){
            MainClass.errorLog("An error occurred in HealthNHunger.");
            e.printStackTrace();
        }
    }
}