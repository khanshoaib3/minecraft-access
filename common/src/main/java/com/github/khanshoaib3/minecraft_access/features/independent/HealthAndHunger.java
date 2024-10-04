package com.github.khanshoaib3.minecraft_access.features.independent;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.github.khanshoaib3.minecraft_access.utils.condition.IntervalKeystroke;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import com.github.khanshoaib3.minecraft_access.utils.system.KeyUtils;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;

/**
 * Speak Player Health and Hunger Key (default: R)
 */
@Slf4j
public class HealthAndHunger {
    private static final IntervalKeystroke key = new IntervalKeystroke(
            () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().healthNHungerNarrationKey),
            Keystroke.TriggeredAt.PRESSED,
            // 3s interval
            Interval.inMilliseconds(3000));

    public static void runWithInterval() {
        try {
            if (key.canBeTriggered()) {
                run();
            }
            key.updateStateForNextTick();
        } catch (Exception e) {
            log.error("An error occurred in HealthNHunger.", e);
        }
    }

    public static void run() {
        ClientPlayerEntity player = WorldUtils.getClientPlayer();
        double health = player.getHealth();
        double hunger = player.getHungerManager().getFoodLevel();
        health = (double) Math.round((health / 2) * 10) / 10;
        hunger = (double) Math.round((hunger / 2) * 10) / 10;
        String toSpeak = I18n.translate("minecraft_access.healthHunger.format", health, hunger);
        MainClass.speakWithNarrator(toSpeak, true);
    }
}
