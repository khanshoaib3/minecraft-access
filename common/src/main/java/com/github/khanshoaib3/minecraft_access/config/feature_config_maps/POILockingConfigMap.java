package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class POILockingConfigMap {
    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Lock on Blocks")
    private boolean lockOnBlocks;
    @SerializedName("Speak Relative Distance to Entity/Block")
    private boolean speakDistance;
    @SerializedName("Play Unlocking Sound")
    private boolean unlockingSound;
    @SerializedName("Auto Lock on to Eye of Ender when Used")
    private boolean autoLockEyeOfEnderEntity;
    @SerializedName("Delay (in milliseconds)")
    private int delay;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isLockOnBlocks() {
        return lockOnBlocks;
    }

    public void setLockOnBlocks(boolean lockOnBlocks) {
        this.lockOnBlocks = lockOnBlocks;
    }

    public boolean isSpeakDistance() {
        return speakDistance;
    }

    public void setSpeakDistance(boolean speakDistance) {
        this.speakDistance = speakDistance;
    }

    public boolean isUnlockingSound() {
        return unlockingSound;
    }

    public void setUnlockingSound(boolean unlockingSound) {
        this.unlockingSound = unlockingSound;
    }

    public boolean isAutoLockEyeOfEnderEntity() {
        return autoLockEyeOfEnderEntity;
    }

    public void setAutoLockEyeOfEnderEntity(boolean autoLockEyeOfEnderEntity) {
        this.autoLockEyeOfEnderEntity = autoLockEyeOfEnderEntity;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
