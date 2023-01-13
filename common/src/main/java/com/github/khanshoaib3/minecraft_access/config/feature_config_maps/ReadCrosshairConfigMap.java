package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ReadCrosshairConfigMap {
    private boolean enabled;
    private boolean speakSide;
    private boolean disableSpeakingConsecutiveBlocks;

    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("Speak Block Sides")
    public boolean isSpeakSide() {
        return speakSide;
    }

    public void setSpeakSide(boolean speakSide) {
        this.speakSide = speakSide;
    }

    @JsonProperty("Disable Speaking Consecutive Blocks With Same Name")
    public boolean isDisableSpeakingConsecutiveBlocks() {
        return disableSpeakingConsecutiveBlocks;
    }

    public void setDisableSpeakingConsecutiveBlocks(boolean disableSpeakingConsecutiveBlocks) {
        this.disableSpeakingConsecutiveBlocks = disableSpeakingConsecutiveBlocks;
    }
}
