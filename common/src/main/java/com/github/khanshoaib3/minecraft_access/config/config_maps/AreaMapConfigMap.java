package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class AreaMapConfigMap {
    private static AreaMapConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;

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

        setInstance(m);
        return m;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public AreaMapConfigMap setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
    }
}
