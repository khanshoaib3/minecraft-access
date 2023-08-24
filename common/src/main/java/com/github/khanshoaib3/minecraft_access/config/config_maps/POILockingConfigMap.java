package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class POILockingConfigMap {

    private static POILockingConfigMap instance;

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

    private POILockingConfigMap() {
    }

    public static POILockingConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(POILockingConfigMap map) {
        instance = map;
    }

    public static POILockingConfigMap buildDefault() {
        POILockingConfigMap m = new POILockingConfigMap();
        m.setEnabled(true);
        m.setLockOnBlocks(true);
        m.setSpeakDistance(false);
        m.setUnlockingSound(true);
        m.setAutoLockEyeOfEnderEntity(true);
        m.setDelay(100);

        setInstance(m);
        return m;
    }

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
