package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class OtherConfigsMap {
    @SerializedName("Enable Biome Indicator")
    private boolean biomeIndicatorEnabled;
    @SerializedName("Enable XP Indicator")
    private boolean xpIndicatorEnabled;
    @SerializedName("Enable Facing Direction")
    private boolean facingDirectionEnabled;
    @SerializedName("Enable Health n Hunger")
    private boolean healthNHungerEnabled;
    @SerializedName("Enable Position Narrator")
    private boolean positionNarratorEnabled;
    @SerializedName("Position Narrator Format")
    private String positionNarratorFormat;
    @SerializedName("Use 12 Hour Time Format")
    private boolean use12HourTimeFormat;
    @SerializedName("Enable Narrator Menu")
    private boolean narratorMenuEnabled;
    @SerializedName("Enable Menu Fix")
    private boolean menuFixEnabled;
    @SerializedName("Debug Mode")
    private boolean debugMode;

    public boolean isBiomeIndicatorEnabled() {
        return biomeIndicatorEnabled;
    }

    public void setBiomeIndicatorEnabled(boolean biomeIndicatorEnabled) {
        this.biomeIndicatorEnabled = biomeIndicatorEnabled;
    }

    public boolean isXpIndicatorEnabled() {
        return xpIndicatorEnabled;
    }

    public void setXpIndicatorEnabled(boolean xpIndicatorEnabled) {
        this.xpIndicatorEnabled = xpIndicatorEnabled;
    }

    public boolean isFacingDirectionEnabled() {
        return facingDirectionEnabled;
    }

    public void setFacingDirectionEnabled(boolean facingDirectionEnabled) {
        this.facingDirectionEnabled = facingDirectionEnabled;
    }

    public boolean isHealthNHungerEnabled() {
        return healthNHungerEnabled;
    }

    public void setHealthNHungerEnabled(boolean healthNHungerEnabled) {
        this.healthNHungerEnabled = healthNHungerEnabled;
    }

    public boolean isPositionNarratorEnabled() {
        return positionNarratorEnabled;
    }

    public void setPositionNarratorEnabled(boolean positionNarratorEnabled) {
        this.positionNarratorEnabled = positionNarratorEnabled;
    }

    public String getPositionNarratorFormat() {
        return positionNarratorFormat;
    }

    public void setPositionNarratorFormat(String positionNarratorFormat) {
        this.positionNarratorFormat = positionNarratorFormat;
    }

    public boolean isUse12HourTimeFormat() {
        return use12HourTimeFormat;
    }

    public void setUse12HourTimeFormat(boolean use12HourTimeFormat) {
        this.use12HourTimeFormat = use12HourTimeFormat;
    }

    public boolean isNarratorMenuEnabled() {
        return narratorMenuEnabled;
    }

    public void setNarratorMenuEnabled(boolean narratorMenuEnabled) {
        this.narratorMenuEnabled = narratorMenuEnabled;
    }

    public boolean isMenuFixEnabled() {
        return menuFixEnabled;
    }

    public void setMenuFixEnabled(boolean menuFixEnabled) {
        this.menuFixEnabled = menuFixEnabled;
    }

    public boolean isDebugMode() {
        return debugMode;
    }

    public void setDebugMode(boolean debugMode) {
        this.debugMode = debugMode;
    }
}
