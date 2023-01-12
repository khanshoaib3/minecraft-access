package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class POIEntitiesConfigMap {
    private boolean enabled;
    private int range;
    private boolean playSound;
    private float volume;
    private int delay;

    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("Range")
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    @JsonProperty("Play Sound")
    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    @JsonProperty("Volume")
    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    @JsonProperty("Delay (in milliseconds)")
    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
