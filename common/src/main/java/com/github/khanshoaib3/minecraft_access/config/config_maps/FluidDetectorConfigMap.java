package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.google.gson.annotations.SerializedName;

public class FluidDetectorConfigMap {
    @SerializedName("Sound Volume")
    private float volume;

    @SerializedName("Range")
    private int range;

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public static FluidDetectorConfigMap getDefaultFluidDetectorConfigMap() {
        FluidDetectorConfigMap defaultFluidDetectorConfigMap  = new FluidDetectorConfigMap();
        defaultFluidDetectorConfigMap.setRange(10);
        defaultFluidDetectorConfigMap.setVolume(0.25f);
        return defaultFluidDetectorConfigMap;
    }
}
