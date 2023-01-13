package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PlayerWarningConfigMap {
    private boolean enabled;
    private boolean playSound;
    private double firstHealthThreshold;
    private double secondHealthThreshold;
    private double hungerThreshold;
    private double airThreshold;

    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("Play Sound")
    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    @JsonProperty("Health Threshold First")
    public double getFirstHealthThreshold() {
        return firstHealthThreshold;
    }

    public void setFirstHealthThreshold(double firstHealthThreshold) {
        this.firstHealthThreshold = firstHealthThreshold;
    }

    @JsonProperty("Health Threshold Second")
    public double getSecondHealthThreshold() {
        return secondHealthThreshold;
    }

    public void setSecondHealthThreshold(double secondHealthThreshold) {
        this.secondHealthThreshold = secondHealthThreshold;
    }

    @JsonProperty("Hunger Threshold")
    public double getHungerThreshold() {
        return hungerThreshold;
    }

    public void setHungerThreshold(double hungerThreshold) {
        this.hungerThreshold = hungerThreshold;
    }

    @JsonProperty("Air Threshold")
    public double getAirThreshold() {
        return airThreshold;
    }

    public void setAirThreshold(double airThreshold) {
        this.airThreshold = airThreshold;
    }
}
