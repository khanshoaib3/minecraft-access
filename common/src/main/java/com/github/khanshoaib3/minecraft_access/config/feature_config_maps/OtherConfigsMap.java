package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class OtherConfigsMap {
    private boolean biomeIndicatorEnabled;
    private boolean facingDirectionEnabled;
    private boolean healthNHungerEnabled;
    private boolean positionNarratorEnabled;
    private String positionNarratorFormat;
    private boolean narratorMenuEnabled;
    private boolean menuFixEnabled;

    @JsonProperty("Enable Biome Indicator")
    public boolean isBiomeIndicatorEnabled() {
        return biomeIndicatorEnabled;
    }

    public void setBiomeIndicatorEnabled(boolean biomeIndicatorEnabled) {
        this.biomeIndicatorEnabled = biomeIndicatorEnabled;
    }

    @JsonProperty("Enable Facing Direction")
    public boolean isFacingDirectionEnabled() {
        return facingDirectionEnabled;
    }

    public void setFacingDirectionEnabled(boolean facingDirectionEnabled) {
        this.facingDirectionEnabled = facingDirectionEnabled;
    }

    @JsonProperty("Enable Health n Hunger")
    public boolean isHealthNHungerEnabled() {
        return healthNHungerEnabled;
    }

    public void setHealthNHungerEnabled(boolean healthNHungerEnabled) {
        this.healthNHungerEnabled = healthNHungerEnabled;
    }

    @JsonProperty("Enable Position Narrator")
    public boolean isPositionNarratorEnabled() {
        return positionNarratorEnabled;
    }

    public void setPositionNarratorEnabled(boolean positionNarratorEnabled) {
        this.positionNarratorEnabled = positionNarratorEnabled;
    }

    @JsonProperty("Position Narrator Format")
    public String getPositionNarratorFormat() {
        return positionNarratorFormat;
    }

    public void setPositionNarratorFormat(String positionNarratorFormat) {
        this.positionNarratorFormat = positionNarratorFormat;
    }

    @JsonProperty("Enable Narrator Menu")
    public boolean isNarratorMenuEnabled() {
        return narratorMenuEnabled;
    }

    public void setNarratorMenuEnabled(boolean narratorMenuEnabled) {
        this.narratorMenuEnabled = narratorMenuEnabled;
    }

    @JsonProperty("Enable Menu Fix")
    public boolean isMenuFixEnabled() {
        return menuFixEnabled;
    }

    public void setMenuFixEnabled(boolean menuFixEnabled) {
        this.menuFixEnabled = menuFixEnabled;
    }
}
