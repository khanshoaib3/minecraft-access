package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class POIBlocksConfigMap {

    private static POIBlocksConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Detect Fluid Blocks")
    private boolean detectFluidBlocks;
    @SerializedName("Range")
    private int range;
    @SerializedName("Play Sound")
    private boolean playSound;
    @SerializedName("Sound Volume")
    private float volume;
    @SerializedName("Play Sound for Other Blocks as well")
    private boolean playSoundForOtherBlocks;
    @SerializedName("Delay (in milliseconds)")
    private int delay;

    private POIBlocksConfigMap() {
    }

    public static POIBlocksConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(POIBlocksConfigMap map) {
        instance = map;
    }

    public static POIBlocksConfigMap buildDefault() {
        POIBlocksConfigMap m = new POIBlocksConfigMap();
        m.setEnabled(true);
        m.setDetectFluidBlocks(true);
        m.setRange(6);
        m.setPlaySound(true);
        m.setVolume(0.25f);
        m.setPlaySoundForOtherBlocks(false);
        m.setDelay(3000);

        setInstance(m);
        return m;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isDetectFluidBlocks() {
        return detectFluidBlocks;
    }

    public void setDetectFluidBlocks(boolean detectFluidBlocks) {
        this.detectFluidBlocks = detectFluidBlocks;
    }

    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

    public boolean isPlaySoundForOtherBlocks() {
        return playSoundForOtherBlocks;
    }

    public void setPlaySoundForOtherBlocks(boolean playSoundForOtherBlocks) {
        this.playSoundForOtherBlocks = playSoundForOtherBlocks;
    }

    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
