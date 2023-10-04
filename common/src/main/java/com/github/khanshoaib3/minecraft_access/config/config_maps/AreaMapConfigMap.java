package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class AreaMapConfigMap {
    private static AreaMapConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled = true;
    @SerializedName("Delay (in milliseconds)")
    private int delayInMilliseconds = 150;

    private AreaMapConfigMap() {
    }

    public static AreaMapConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(AreaMapConfigMap map) {
        instance = map;
    }

    public static AreaMapConfigMap buildDefault() {
        AreaMapConfigMap m = new AreaMapConfigMap();
        m.enabled = true;
        m.delayInMilliseconds = 150;

        setInstance(m);
        return m;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }
}
