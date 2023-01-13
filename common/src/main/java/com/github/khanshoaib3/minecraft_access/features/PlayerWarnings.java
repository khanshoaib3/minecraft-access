package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;

/**
 * Warns the player when the health, hunger or food reaches below a certain threshold.
 */
public class PlayerWarnings {
    private MinecraftClient minecraftClient;

    private boolean isHealthBelowFirstThreshold;
    private boolean isHealthBelowSecondThreshold;
    private boolean isFoodBelowThreshold;
    private boolean isAirBelowThreshold;

    private boolean playSound;
    private double firstHealthThreshold;
    private double secondHealthThreshold;
    private double hungerThreshold;
    private double airThreshold;

    public PlayerWarnings() {
        isHealthBelowFirstThreshold = false;
        isHealthBelowSecondThreshold = false;
        isFoodBelowThreshold = false;
        isAirBelowThreshold = false;

        loadConfigurations();
    }

    public void update() {
        try {
            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            loadConfigurations();

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
            MainClass.errorLog("An error occurred in PlayerWarnings.");
            e.printStackTrace();
        }
    }

    private void loadConfigurations() {
        this.playSound = MainClass.config.getConfigMap().getPlayerWarningConfigMap().isPlaySound();
        this.firstHealthThreshold = MainClass.config.getConfigMap().getPlayerWarningConfigMap().getFirstHealthThreshold();
        this.secondHealthThreshold = MainClass.config.getConfigMap().getPlayerWarningConfigMap().getSecondHealthThreshold();
        this.hungerThreshold = MainClass.config.getConfigMap().getPlayerWarningConfigMap().getHungerThreshold();
        this.airThreshold = MainClass.config.getConfigMap().getPlayerWarningConfigMap().getAirThreshold();
    }

    private void healthWarning(double health) {
        if (minecraftClient.player == null) return;

        if (health < firstHealthThreshold && health > secondHealthThreshold && !isHealthBelowFirstThreshold && !isHealthBelowSecondThreshold) {
            isHealthBelowFirstThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.health_low", health), true);
            if(playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
        }

        if (health < secondHealthThreshold && health > 0 && isHealthBelowFirstThreshold && !isHealthBelowSecondThreshold) {
            isHealthBelowSecondThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.health_low", health), true);
            if(playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
        }

        if (isHealthBelowFirstThreshold && health >= firstHealthThreshold) isHealthBelowFirstThreshold = false;
        if (isHealthBelowSecondThreshold && health >= secondHealthThreshold) isHealthBelowSecondThreshold = false;
    }

    private void hungerWarning(double hunger) {
        if (minecraftClient.player == null) return;

        if (hunger < hungerThreshold && hunger > 0 && !isFoodBelowThreshold) {
            isFoodBelowThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.hunger_low", hunger), true);
            if(playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
        }

        if (isFoodBelowThreshold && hunger >= hungerThreshold) isFoodBelowThreshold = false;
    }

    private void airWarning(double air) {
        if (minecraftClient.player == null) return;

        if (air < airThreshold && air > 0 && !isAirBelowThreshold) {
            isAirBelowThreshold = true;
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.player_warnings.air_low", air), true);
            if(playSound) minecraftClient.player.playSound(SoundEvents.BLOCK_ANVIL_LAND, SoundCategory.PLAYERS, (float) 1, (float) 1);
        }

        if (isAirBelowThreshold && air >= airThreshold) isAirBelowThreshold = false;
    }
}
