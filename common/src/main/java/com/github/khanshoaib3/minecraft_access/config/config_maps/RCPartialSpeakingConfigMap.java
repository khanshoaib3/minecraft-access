package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RCPartialSpeakingConfigMap {
    private static RCPartialSpeakingConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("White List Mode")
    private boolean partialSpeakingWhitelistMode;
    @SerializedName("Fuzzy Matching Mode")
    private boolean partialSpeakingFuzzyMode;
    @SerializedName("Target Mode")
    private PartialSpeakingTargetMode partialSpeakingTargetMode;
    @SerializedName("Targets")
    private List<String> partialSpeakingTargets;

    public static RCPartialSpeakingConfigMap buildDefault() {
        RCPartialSpeakingConfigMap m = new RCPartialSpeakingConfigMap();
        m.enabled = false;
        m.partialSpeakingWhitelistMode = true;
        m.partialSpeakingFuzzyMode = true;
        m.partialSpeakingTargets = List.of("slab", "planks", "block", "stone", "sign");
        m.partialSpeakingTargetMode = PartialSpeakingTargetMode.BLOCK;

        setInstance(m);
        return m;
    }

    public static void setInstance(RCPartialSpeakingConfigMap map) {
        if (instance == null) Config.getInstance().loadConfig();
        instance = map;
    }

    public static RCPartialSpeakingConfigMap getInstance() {
        return instance;
    }

    public enum PartialSpeakingTargetMode {
        @SerializedName("all")
        ALL,
        @SerializedName("entity")
        ENTITY,
        @SerializedName("block")
        BLOCK,
    }

    public boolean isEnabled() {
        return enabled;
    }

    public RCPartialSpeakingConfigMap setEnabled(boolean enabled) {
        this.enabled = enabled;
        return this;
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

    @SuppressWarnings("unused")
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

    @SuppressWarnings("unused")
    public void setPartialSpeakingTargetMode(PartialSpeakingTargetMode partialSpeakingTargetMode) {
        this.partialSpeakingTargetMode = partialSpeakingTargetMode;
    }
}
