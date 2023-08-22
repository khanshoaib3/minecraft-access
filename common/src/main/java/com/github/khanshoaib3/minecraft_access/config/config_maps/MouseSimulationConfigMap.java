package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class MouseSimulationConfigMap {
    private static MouseSimulationConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;

    @SerializedName("Scroll Delay (in milliseconds)")
    private int scrollDelayInMilliseconds;

    public static MouseSimulationConfigMap buildDefault() {
        MouseSimulationConfigMap m = new MouseSimulationConfigMap();
        m.enabled = true;
        m.scrollDelayInMilliseconds = 150;

        setInstance(m);
        return m;
    }

    public static void setInstance(MouseSimulationConfigMap map) {
        instance = map;
    }

    public static MouseSimulationConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public MouseSimulationConfigMap setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }

    public int getScrollDelayInMilliseconds() {
        return scrollDelayInMilliseconds;
    }

    public void setScrollDelayInMilliseconds(int scrollDelayInMilliseconds) {
        this.scrollDelayInMilliseconds = scrollDelayInMilliseconds;
    }
}
