package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class POIEntitiesConfigMap {

    private static POIEntitiesConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Range")
    private int range;
    @SerializedName("Play Sound")
    private boolean playSound;
    @SerializedName("Volume")
    private float volume;
    @SerializedName("Delay (in milliseconds)")
    private int delay;

    private POIEntitiesConfigMap() {
    }

    public static POIEntitiesConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(POIEntitiesConfigMap map) {
        instance = map;
    }

    public static POIEntitiesConfigMap buildDefault() {
        POIEntitiesConfigMap m = new POIEntitiesConfigMap();
        m.setEnabled(true);
        m.setRange(6);
        m.setPlaySound(true);
        m.setVolume(0.25f);
        m.setDelay(3000);

        setInstance(m);
        return m;
    }

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

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public float getVolume() {
        return volume;
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
}
