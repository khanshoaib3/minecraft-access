package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.MouseSimulationConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.TimeUtils;
import net.minecraft.client.MinecraftClient;

/**
 * Bind four mouse operations with customizable keys:<br><br>
 * 1) left mouse key pressing<br>
 * 2) right mouse key pressing<br>
 * 3) mouse wheel scroll up<br>
 * 4) mouse wheel scroll down
 */
public class MouseKeySimulation {
    private static final MouseKeySimulation instance;

    private boolean enabled;
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
        if (!enabled) return;
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return;
        if (minecraftClient.player == null) return;
        // this feature only works when there is no screen open.
        if (minecraftClient.currentScreen != null) return;

        loadConfigurations();

        // TODO 要求左右键能单击或长按，或者在MouseUtils写新的方法。
        // TODO 要求滚动能单击或长按，长按应控制滚动速度（变成配置项？）。
    }

    private void loadConfigurations() {
        MouseSimulationConfigMap map = MainClass.config.getConfigMap().getMouseSimulationConfigMap();
        this.enabled = map.isEnabled();
        this.scrollUpDelay = TimeUtils.Interval.inMilliseconds(map.getScrollDelayInMilliseconds(), this.scrollUpDelay);
        this.scrollDownDelay = TimeUtils.Interval.inMilliseconds(map.getScrollDelayInMilliseconds(), this.scrollDownDelay);
    }
}
