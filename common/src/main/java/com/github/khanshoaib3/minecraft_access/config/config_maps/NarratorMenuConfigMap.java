package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.google.gson.annotations.SerializedName;

public class NarratorMenuConfigMap {
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

    public static NarratorMenuConfigMap getDefaultNarratorMenuConfigMap() {
        NarratorMenuConfigMap defaultNarratorMenuConfigMap = new NarratorMenuConfigMap();
        defaultNarratorMenuConfigMap.setEnabled(true);
        defaultNarratorMenuConfigMap.fluidDetectorConfigMap = FluidDetectorConfigMap.buildDefault();
        return defaultNarratorMenuConfigMap;
    }
}
