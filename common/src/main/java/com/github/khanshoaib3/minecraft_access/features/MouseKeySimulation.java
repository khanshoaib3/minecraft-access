package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.MouseSimulationConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.*;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import net.minecraft.client.MinecraftClient;
import org.apache.commons.lang3.tuple.Triple;

import java.util.Set;

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
    private static final Keystroke[] mouseKeystrokes = new Keystroke[3];
    private Interval scrollUpDelay;
    private Interval scrollDownDelay;

    static {
        try {
            instance = new MouseKeySimulation();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating AttackAndUseSimulation instance");
        }

        // config keystroke conditions
        KeyBindingsHandler kbh = KeyBindingsHandler.getInstance();
        mouseKeystrokes[0] = new Keystroke(() -> KeyUtils.isAnyPressed(kbh.mouseSimulationLeftMouseKey));
        mouseKeystrokes[1] = new Keystroke(() -> KeyUtils.isAnyPressed(kbh.mouseSimulationMiddleMouseKey));
        mouseKeystrokes[2] = new Keystroke(() -> KeyUtils.isAnyPressed(kbh.mouseSimulationRightMouseKey));
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
        this.scrollUpDelay = Interval.inMilliseconds(map.getScrollDelayInMilliseconds(), this.scrollUpDelay);
        this.scrollDownDelay = Interval.inMilliseconds(map.getScrollDelayInMilliseconds(), this.scrollDownDelay);
    }

    private void execute() {
        KeyBindingsHandler kbh = KeyBindingsHandler.getInstance();

        Set.of(
                Triple.<Boolean, Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationScrollUpKey), scrollUpDelay, MouseUtils::scrollUp),
                Triple.<Boolean, Interval, Runnable>of(KeyUtils.isAnyPressed(kbh.mouseSimulationScrollDownKey), scrollDownDelay, MouseUtils::scrollDown)
        ).forEach(t -> {
            if (t.getLeft() && t.getMiddle().isReady()) {
                t.getRight().run();
            }
        });

        Set.of(
                new MouseKeyDTO(mouseKeystrokes[0], MouseUtils::leftDown, MouseUtils::leftUp),
                new MouseKeyDTO(mouseKeystrokes[1], MouseUtils::middleDown, MouseUtils::middleUp),
                new MouseKeyDTO(mouseKeystrokes[2], MouseUtils::rightDown, MouseUtils::rightUp)
        ).forEach(dto -> {
            if (dto.keystroke.isPressed()) {
                dto.keyDown.run();
            } else if (dto.keystroke.isReleased()) {
                dto.keyUp.run();
            }

            dto.keystroke.updateStateForNextTick();
        });
    }

    private record MouseKeyDTO(Keystroke keystroke, Runnable keyDown, Runnable keyUp) {
    }
}
