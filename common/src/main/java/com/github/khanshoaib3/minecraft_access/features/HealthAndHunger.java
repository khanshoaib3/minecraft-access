package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.resource.language.I18n;

/**
 * Speak Player Health and Hunger Key (default: R)
 */
@Slf4j
public class HealthAndHunger {
    private static final Keystroke key = new Keystroke(KeyBindingsHandler.getInstance().healthNHungerNarrationKey);

    public static void runIfConditionMet() {
        if (key.canBeTriggered()) speak();
    }

    public static void speak() {
        double health = PlayerUtils.getHearts();
        double hunger = PlayerUtils.getHunger();
        String toSpeak = I18n.translate("minecraft_access.health_and_hunger.format", health, hunger);
        long air = PlayerUtils.getAir();
        if (air < 300) {
            toSpeak += " " + I18n.translate("minecraft_access.air.format", air);
        }
        MainClass.speakWithNarrator(toSpeak, true);
    }
}
