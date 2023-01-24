package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class ReadCrosshairConfigMap {
    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Speak Block Sides")
    private boolean speakSide;
    @SerializedName("Disable Speaking Consecutive Blocks With Same Name")
    private boolean disableSpeakingConsecutiveBlocks;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSpeakSide() {
        return speakSide;
    }

    public void setSpeakSide(boolean speakSide) {
        this.speakSide = speakSide;
    }

    public boolean isDisableSpeakingConsecutiveBlocks() {
        return disableSpeakingConsecutiveBlocks;
    }

    public void setDisableSpeakingConsecutiveBlocks(boolean disableSpeakingConsecutiveBlocks) {
        this.disableSpeakingConsecutiveBlocks = disableSpeakingConsecutiveBlocks;
    }
}
