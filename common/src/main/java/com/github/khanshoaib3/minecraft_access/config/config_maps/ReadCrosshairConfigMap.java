package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReadCrosshairConfigMap {
    private static ReadCrosshairConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Speak Block Sides")
    private boolean speakSide;
    @SerializedName("Disable Speaking Consecutive Blocks With Same Name")
    private boolean disableSpeakingConsecutiveBlocks;
    @SerializedName("Repeat Speaking Interval (in milliseconds) (0 to disable)")
    private long repeatSpeakingInterval;
    @SerializedName("Enable Partial Speaking")
    private boolean enablePartialSpeaking;
    @SerializedName("Partial Speaking White List Mode")
    private boolean partialSpeakingWhitelistMode;
    @SerializedName("Partial Speaking Fuzzy Mode")
    private boolean partialSpeakingFuzzyMode;
    @SerializedName("Partial Speaking Target Mode")
    private PartialSpeakingTargetMode partialSpeakingTargetMode;
    @SerializedName("Partial Speaking Targets")
    private List<String> partialSpeakingTargets;

    public enum PartialSpeakingTargetMode {
        @SerializedName("all")
        ALL,
        @SerializedName("entity")
        ENTITY,
        @SerializedName("block")
        BLOCK
    }

    public static ReadCrosshairConfigMap buildDefault() {
        ReadCrosshairConfigMap m = new ReadCrosshairConfigMap();
        m.setEnabled(true);
        m.setSpeakSide(true);
        m.setDisableSpeakingConsecutiveBlocks(true);
        m.setRepeatSpeakingInterval(0L);
        m.setEnablePartialSpeaking(false);
        m.setPartialSpeakingWhitelistMode(true);
        m.setPartialSpeakingFuzzyMode(true);
        m.setPartialSpeakingTargets(List.of("slab", "planks", "block", "stone", "sign"));
        m.setPartialSpeakingTargetMode(PartialSpeakingTargetMode.BLOCK);
        return m;
    }

    public static ReadCrosshairConfigMap getInstance() {
        return instance;
    }

    public static void setInstance(ReadCrosshairConfigMap map) {
        instance = map;
    }

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

    public long getRepeatSpeakingInterval() {
        return repeatSpeakingInterval;
    }

    public void setRepeatSpeakingInterval(long repeatSpeakingInterval) {
        this.repeatSpeakingInterval = repeatSpeakingInterval;
    }

    public boolean isEnablePartialSpeaking() {
        return enablePartialSpeaking;
    }

    public void setEnablePartialSpeaking(boolean enablePartialSpeaking) {
        this.enablePartialSpeaking = enablePartialSpeaking;
    }

    public boolean isPartialSpeakingWhitelistMode() {
        return partialSpeakingWhitelistMode;
    }

    public void setPartialSpeakingWhitelistMode(boolean partialSpeakingWhitelistMode) {
        this.partialSpeakingWhitelistMode = partialSpeakingWhitelistMode;
    }

    public List<String> getPartialSpeakingTargets() {
        return partialSpeakingTargets;
    }

    public void setPartialSpeakingTargets(List<String> partialSpeakingBlocks) {
        this.partialSpeakingTargets = partialSpeakingBlocks;
    }

    public boolean isPartialSpeakingFuzzyMode() {
        return partialSpeakingFuzzyMode;
    }

    public void setPartialSpeakingFuzzyMode(boolean partialSpeakingFuzzyMode) {
        this.partialSpeakingFuzzyMode = partialSpeakingFuzzyMode;
    }

    public PartialSpeakingTargetMode getPartialSpeakingTargetMode() {
        return partialSpeakingTargetMode;
    }

    public void setPartialSpeakingTargetMode(PartialSpeakingTargetMode partialSpeakingTargetMode) {
        this.partialSpeakingTargetMode = partialSpeakingTargetMode;
    }
}
