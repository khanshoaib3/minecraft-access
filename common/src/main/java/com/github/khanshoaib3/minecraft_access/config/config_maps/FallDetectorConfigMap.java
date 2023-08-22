package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class FallDetectorConfigMap {

    private static FallDetectorConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Range")
    private int range;
    @SerializedName("Depth Threshold")
    private int depth;
    @SerializedName("Play Alternate Sound")
    private boolean playAlternateSound;
    @SerializedName("Sound Volume")
    private float volume;
    @SerializedName("Delay (in milliseconds)")
    private int delay;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public int getDepth() {
        return depth;
    }

    public void setDepth(int depth) {
        this.depth = depth;
    }

    public float getVolume() {
        return volume;
    }

    public boolean isPlayAlternateSound() {
        return playAlternateSound;
    }

    public void setPlayAlternateSound(boolean playAlternateSound) {
        this.playAlternateSound = playAlternateSound;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }

    private FallDetectorConfigMap() {
    }

    public static FallDetectorConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(FallDetectorConfigMap map) {
        instance = map;
    }

    public static FallDetectorConfigMap buildDefault() {
        FallDetectorConfigMap fallDetectorConfigMap = new FallDetectorConfigMap();
        fallDetectorConfigMap.setEnabled(true);
        fallDetectorConfigMap.setRange(6);
        fallDetectorConfigMap.setDepth(4);
        fallDetectorConfigMap.setPlayAlternateSound(true);
        fallDetectorConfigMap.setVolume(0.25f);
        fallDetectorConfigMap.setDelay(2500);

        setInstance(fallDetectorConfigMap);
        return fallDetectorConfigMap;
    }
}
