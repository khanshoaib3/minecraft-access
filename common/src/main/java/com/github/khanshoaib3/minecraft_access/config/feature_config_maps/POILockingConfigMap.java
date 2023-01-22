package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

//import com.fasterxml.jackson.annotation.JsonProperty;

public class POILockingConfigMap {
    private boolean enabled;
    private boolean lockOnBlocks;
    private boolean speakDistance;
    private boolean unlockingSound;
    private boolean autoLockEyeOfEnderEntity;
    private int delay;

//    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    @JsonProperty("Lock on Blocks")
    public boolean isLockOnBlocks() {
        return lockOnBlocks;
    }

    public void setLockOnBlocks(boolean lockOnBlocks) {
        this.lockOnBlocks = lockOnBlocks;
    }

//    @JsonProperty("Speak Relative Distance to Entity/Block")
    public boolean isSpeakDistance() {
        return speakDistance;
    }

    public void setSpeakDistance(boolean speakDistance) {
        this.speakDistance = speakDistance;
    }

//    @JsonProperty("Play Unlocking Sound")
    public boolean isUnlockingSound() {
        return unlockingSound;
    }

    public void setUnlockingSound(boolean unlockingSound) {
        this.unlockingSound = unlockingSound;
    }

//    @JsonProperty("Auto Lock on to Eye of Ender when Used")
    public boolean isAutoLockEyeOfEnderEntity() {
        return autoLockEyeOfEnderEntity;
    }

    public void setAutoLockEyeOfEnderEntity(boolean autoLockEyeOfEnderEntity) {
        this.autoLockEyeOfEnderEntity = autoLockEyeOfEnderEntity;
    }

//    @JsonProperty("Delay (in milliseconds)")
    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
