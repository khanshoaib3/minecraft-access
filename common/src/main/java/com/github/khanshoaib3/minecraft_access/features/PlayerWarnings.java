package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.Config;
import com.github.khanshoaib3.minecraft_access.MainClass;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.sound.SoundEvents;

/**
 * Warns the player when the health, hunger or food reaches below a certain threshold.
 */
@Slf4j
public class PlayerWarnings {
    private MinecraftClient minecraftClient;

    private boolean isHealthBelowFirstThreshold;
    private boolean isHealthBelowSecondThreshold;
    private boolean isFoodBelowThreshold;
    private boolean isAirBelowThreshold;
    private Config.PlayerWarnings config;

    public PlayerWarnings() {
        isHealthBelowFirstThreshold = false;
        isHealthBelowSecondThreshold = false;
        isFoodBelowThreshold = false;
        isAirBelowThreshold = false;

        loadConfig();
    }

    public void update() {
        try {
            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            loadConfig();

            double health = minecraftClient.player.getHealth();
            double hunger = minecraftClient.player.getHungerManager().getFoodLevel();
            double air = minecraftClient.player.getAir();

            health = (double) Math.round((health / 2) * 10) / 10;
            hunger = (double) Math.round((hunger / 2) * 10) / 10;
            air = (double) Math.round((air / 30) * 10) / 10;

            healthWarning(health);

            hungerWarning(hunger);

            airWarning(air);
        } catch (Exception e) {
            log.error("An error occurred in PlayerWarnings.", e);
        }
    }

    private void loadConfig() {
        config = Config.getInstance().playerWarnings;
    }

    private void healthWarning(double health) {
        if (minecraftClient.player == null) return;

        if (health < config.firstHealthThreshold && health > config.secondHealthThreshold && !isHealthBelowFirstThreshold && !isHealthBelowSecondThreshold) {
            isHealthBelowFirstThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.health_low", health), true);
            if (config.playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
        }

        if (health < config.secondHealthThreshold && health > 0 && isHealthBelowFirstThreshold && !isHealthBelowSecondThreshold) {
            isHealthBelowSecondThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.health_low", health), true);
            if (config.playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
        }

        if (isHealthBelowFirstThreshold && health >= config.firstHealthThreshold) isHealthBelowFirstThreshold = false;
        if (isHealthBelowSecondThreshold && health >= config.secondHealthThreshold) isHealthBelowSecondThreshold = false;
    }

    private void hungerWarning(double hunger) {
        if (minecraftClient.player == null) return;

        if (hunger < config.hungerThreshold && hunger > 0 && !isFoodBelowThreshold) {
            isFoodBelowThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.hunger_low", hunger), true);
            if (config.playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
        }

        if (isFoodBelowThreshold && hunger >= config.hungerThreshold) isFoodBelowThreshold = false;
    }

    private void airWarning(double air) {
        if (minecraftClient.player == null) return;

        if (air < config.airThreshold && air > 0 && !isAirBelowThreshold) {
            isAirBelowThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.air_low", air), true);
            if (config.playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, 1.0f, 1.0f);
        }

        if (isAirBelowThreshold && air >= config.airThreshold) isAirBelowThreshold = false;
    }
}
