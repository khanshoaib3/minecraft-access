package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

/**
 * This feature speaks when the player xp level is increased or decreased.
 */
public class XPIndicator {
    private int previousXPLevel;

    public XPIndicator() {
        previousXPLevel = -999;
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            int currentXPLevel = ClientPlayerEntityUtils.getExperienceLevel();
            if (previousXPLevel == currentXPLevel) return;

            boolean increased = previousXPLevel < currentXPLevel;
            previousXPLevel = currentXPLevel;

            String toSpeak = (increased) ? I18n.translate("minecraft_access.xp_indicator.increased", currentXPLevel)
                    : I18n.translate("minecraft_access.xp_indicator.decreased", currentXPLevel);
            MainClass.speakWithNarrator(toSpeak, true);
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in xp indicator.", e);
        }
    }
}
