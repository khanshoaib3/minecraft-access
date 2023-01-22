package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

//import com.fasterxml.jackson.annotation.JsonProperty;

public class POIBlocksConfigMap {
    private boolean enabled;
    private boolean detectFluidBlocks;
    private int range;
    private boolean playSound;
    private float volume;
    private boolean playSoundForOtherBlocks;
    private int delay;

//    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

//    @JsonProperty("Detect Fluid Blocks")
    public boolean isDetectFluidBlocks() {
        return detectFluidBlocks;
    }

    public void setDetectFluidBlocks(boolean detectFluidBlocks) {
        this.detectFluidBlocks = detectFluidBlocks;
    }

//    @JsonProperty("Range")
    public int getRange() {
        return range;
    }

    public void setRange(int range) {
        this.range = range;
    }

//    @JsonProperty("Play Sound")
    public boolean isPlaySound() {
        return playSound;
    }

    public void setPlaySound(boolean playSound) {
        this.playSound = playSound;
    }

//    @JsonProperty("Sound Volume")
    public float getVolume() {
        return volume;
    }

    public void setVolume(float volume) {
        this.volume = volume;
    }

//    @JsonProperty("Play Sound for Other Blocks as well")
    public boolean isPlaySoundForOtherBlocks() {
        return playSoundForOtherBlocks;
    }

    public void setPlaySoundForOtherBlocks(boolean playSoundForOtherBlocks) {
        this.playSoundForOtherBlocks = playSoundForOtherBlocks;
    }

//    @JsonProperty("Delay (in milliseconds)")
    public int getDelay() {
        return delay;
    }

    public void setDelay(int delay) {
        this.delay = delay;
    }
}
