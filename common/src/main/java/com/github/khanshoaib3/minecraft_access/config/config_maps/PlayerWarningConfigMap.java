package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class PlayerWarningConfigMap {

    private static PlayerWarningConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Play Sound")
    private boolean playSound;
    @SerializedName("Health Threshold First")
    private double firstHealthThreshold;
    @SerializedName("Health Threshold Second")
    private double secondHealthThreshold;
    @SerializedName("Hunger Threshold")
    private double hungerThreshold;
    @SerializedName("Air Threshold")
    private double airThreshold;

    public static PlayerWarningConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(PlayerWarningConfigMap map) {
        instance = map;
    }

    public static PlayerWarningConfigMap buildDefault() {
        PlayerWarningConfigMap defaultPlayerWarningConfigMap = new PlayerWarningConfigMap();
        defaultPlayerWarningConfigMap.setEnabled(true);
        defaultPlayerWarningConfigMap.setPlaySound(true);
        defaultPlayerWarningConfigMap.setFirstHealthThreshold(6.0);
        defaultPlayerWarningConfigMap.setSecondHealthThreshold(3.0);
        defaultPlayerWarningConfigMap.setHungerThreshold(3.0);
        defaultPlayerWarningConfigMap.setAirThreshold(3.0);

        setInstance(defaultPlayerWarningConfigMap);
        return defaultPlayerWarningConfigMap;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public double getFirstHealthThreshold() {
        return firstHealthThreshold;
    }

    public void setFirstHealthThreshold(double firstHealthThreshold) {
        this.firstHealthThreshold = firstHealthThreshold;
    }

    public double getSecondHealthThreshold() {
        return secondHealthThreshold;
    }

    public void setSecondHealthThreshold(double secondHealthThreshold) {
        this.secondHealthThreshold = secondHealthThreshold;
    }

    public double getHungerThreshold() {
        return hungerThreshold;
    }

    public void setHungerThreshold(double hungerThreshold) {
        this.hungerThreshold = hungerThreshold;
    }

    public double getAirThreshold() {
        return airThreshold;
    }

    public void setAirThreshold(double airThreshold) {
        this.airThreshold = airThreshold;
    }
}
