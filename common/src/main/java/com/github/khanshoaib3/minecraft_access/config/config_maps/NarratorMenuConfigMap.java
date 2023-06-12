package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.google.gson.annotations.SerializedName;

public class NarratorMenuConfigMap {
    @SerializedName("Fluid Detector")
    private FluidDetectorConfigMap fluidDetectorConfigMap;

    public FluidDetectorConfigMap getFluidDetectorConfigMap() {
        return fluidDetectorConfigMap;
    }

    public void setFluidDetectorConfigMap(FluidDetectorConfigMap fluidDetectorConfigMap) {
        this.fluidDetectorConfigMap = fluidDetectorConfigMap;
    }
}
