package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class NarratorMenuConfigMap {

    private static NarratorMenuConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Fluid Detector")
    private FluidDetectorConfigMap fluidDetectorConfigMap;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public FluidDetectorConfigMap getFluidDetectorConfigMap() {
        return fluidDetectorConfigMap;
    }

    private NarratorMenuConfigMap() {
    }

    public static NarratorMenuConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(NarratorMenuConfigMap map) {
        FluidDetectorConfigMap.setInstance(map.fluidDetectorConfigMap);
        instance = map;
    }

    public static NarratorMenuConfigMap buildDefault() {
        NarratorMenuConfigMap defaultNarratorMenuConfigMap = new NarratorMenuConfigMap();
        defaultNarratorMenuConfigMap.setEnabled(true);
        defaultNarratorMenuConfigMap.fluidDetectorConfigMap = FluidDetectorConfigMap.buildDefault();

        setInstance(defaultNarratorMenuConfigMap);
        return defaultNarratorMenuConfigMap;
    }
}
