package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.MouseSimulationConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.KeyUtils;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import com.github.khanshoaib3.minecraft_access.utils.TimeUtils;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

/**
 * Bind four mouse operations with customizable keys:<br><br>
 * 1) left mouse key pressing<br>
 * 2) right mouse key pressing<br>
 * 3) middle mouse key pressing<br>
 * 4) mouse wheel scroll up<br>
 * 5) mouse wheel scroll down
 */
public class MouseKeySimulation {
    private static final MouseKeySimulation instance;

    private boolean enabled;
    /**
     * How these values are chosen:
     * Creative mode, standing three blocks away from a wall,
     * comparing (listening) block breaking and placing speeds between using real mouse and using keyboard.
     */
    private static final TimeUtils.Interval leftKeyDelay = TimeUtils.Interval.inMilliseconds(300);
    private static final TimeUtils.Interval middleKeyDelay = TimeUtils.Interval.inMilliseconds(200);
    private static final TimeUtils.Interval rightKeyDelay = TimeUtils.Interval.inMilliseconds(180);
    private TimeUtils.Interval scrollUpDelay;
    private TimeUtils.Interval scrollDownDelay;

    static {
        try {
            instance = new MouseKeySimulation();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating AttackAndUseSimulation instance");
        }
    }

    public static synchronized MouseKeySimulation getInstance() {
        return instance;
    }

    public void update() {
        try {
            loadConfigurations();

            if (!enabled) return;
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            // this feature only works when there is no screen open.
            if (minecraftClient.currentScreen != null) return;

            execute();

        } catch (Exception e) {
            MainClass.errorLog("An error occurred while executing MouseKeySimulation", e);
        }
    }

    private void loadConfigurations() {
        MouseSimulationConfigMap map = MainClass.config.getConfigMap().getMouseSimulationConfigMap();
        this.enabled = map.isEnabled();
        this.scrollUpDelay = TimeUtils.Interval.inMilliseconds(map.getScrollDelayInMilliseconds(), this.scrollUpDelay);
        this.scrollDownDelay = TimeUtils.Interval.inMilliseconds(map.getScrollDelayInMilliseconds(), this.scrollDownDelay);
    }

    private void execute() {
        KeyBindingsHandler kbh = KeyBindingsHandler.getInstance();
        List.of(
                Triple.<Boolean, TimeUtils.Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationLeftMouseKey), leftKeyDelay, MouseUtils::leftClick),
                Triple.<Boolean, TimeUtils.Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationMiddleMouseKey), middleKeyDelay, MouseUtils::middleClick),
                Triple.<Boolean, TimeUtils.Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationRightMouseKey), rightKeyDelay, MouseUtils::rightClick),
                Triple.<Boolean, TimeUtils.Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationScrollUpKey), scrollUpDelay, MouseUtils::scrollUp),
                Triple.<Boolean, TimeUtils.Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationScrollDownKey), scrollDownDelay, MouseUtils::scrollDown)
        ).forEach(t -> {
            if (t.getLeft() && t.getMiddle().isReady()) {
                t.getRight().run();
            }
        });
    }
}
