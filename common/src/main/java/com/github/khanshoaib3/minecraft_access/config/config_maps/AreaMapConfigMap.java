package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;

@SuppressWarnings("InstantiationOfUtilityClass")
public class AreaMapConfigMap {
    private static AreaMapConfigMap instance;

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
        setInstance(m);
        return m;
    }
}
