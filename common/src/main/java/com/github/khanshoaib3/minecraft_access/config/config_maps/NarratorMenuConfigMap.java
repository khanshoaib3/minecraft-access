package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.google.gson.annotations.SerializedName;

public class NarratorMenuConfigMap {
    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Fluid Detector")

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    private FluidDetectorConfigMap fluidDetectorConfigMap;

    public FluidDetectorConfigMap getFluidDetectorConfigMap() {
        return fluidDetectorConfigMap;
    }

    public void setFluidDetectorConfigMap(FluidDetectorConfigMap fluidDetectorConfigMap) {
        this.fluidDetectorConfigMap = fluidDetectorConfigMap;
    }

    public static NarratorMenuConfigMap getDefaultNarratorMenuConfigMap() {
        NarratorMenuConfigMap defaultNarratorMenuConfigMap = new NarratorMenuConfigMap();
        defaultNarratorMenuConfigMap.setEnabled(true);
        defaultNarratorMenuConfigMap.setFluidDetectorConfigMap(FluidDetectorConfigMap.getDefaultFluidDetectorConfigMap());
        return defaultNarratorMenuConfigMap;
    }
}
