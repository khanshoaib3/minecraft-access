package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.stream.Stream;

public class POIConfigMap {
    @SerializedName("Blocks")
    private POIBlocksConfigMap poiBlocksConfigMap;
    @SerializedName("Entities")
    private POIEntitiesConfigMap poiEntitiesConfigMap;
    @SerializedName("Entities/Blocks Locking")
    private POILockingConfigMap poiLockingConfigMap;
    @SerializedName("Entities/Blocks Marking")
    private POIMarkingConfigMap poiMarkingConfigMap;

    public static POIConfigMap buildDefault() {
        POIConfigMap m1 = new POIConfigMap();

        POIBlocksConfigMap m2 = new POIBlocksConfigMap();
        m2.setEnabled(true);
        m2.setDetectFluidBlocks(true);
        m2.setRange(6);
        m2.setPlaySound(true);
        m2.setVolume(0.25f);
        m2.setPlaySoundForOtherBlocks(false);
        m2.setDelay(3000);
        m1.setBlocksConfigMap(m2);

        POIEntitiesConfigMap m3 = new POIEntitiesConfigMap();
        m3.setEnabled(true);
        m3.setRange(6);
        m3.setPlaySound(true);
        m3.setVolume(0.25f);
        m3.setDelay(3000);
        m1.setEntitiesConfigMap(m3);

        POILockingConfigMap m4 = new POILockingConfigMap();
        m4.setEnabled(true);
        m4.setLockOnBlocks(true);
        m4.setSpeakDistance(false);
        m4.setUnlockingSound(true);
        m4.setAutoLockEyeOfEnderEntity(true);
        m4.setDelay(100);
        m1.setLockingConfigMap(m4);

        m1.poiMarkingConfigMap = (POIMarkingConfigMap.buildDefault());

        return m1;
    }

    public static void setInstance(POIConfigMap map) {
        // Only leaf config maps (those will be directly used in feature classes)
        // has real singleton static class variable.
        // Parent config maps has responsibility for maintaining child maps' singleton instances.
        POIMarkingConfigMap.setInstance(map.poiMarkingConfigMap);
    }

    public boolean validate() {
        return Stream.of(poiBlocksConfigMap, poiEntitiesConfigMap, poiLockingConfigMap, poiMarkingConfigMap).allMatch(Objects::nonNull);
    }

    public POIBlocksConfigMap getBlocksConfigMap() {
        return poiBlocksConfigMap;
    }

    public void setBlocksConfigMap(POIBlocksConfigMap poiBlocksConfigMap) {
        this.poiBlocksConfigMap = poiBlocksConfigMap;
    }

    public POIEntitiesConfigMap getEntitiesConfigMap() {
        return poiEntitiesConfigMap;
    }

    public void setEntitiesConfigMap(POIEntitiesConfigMap poiEntitiesConfigMap) {
        this.poiEntitiesConfigMap = poiEntitiesConfigMap;
    }

    public POILockingConfigMap getLockingConfigMap() {
        return poiLockingConfigMap;
    }

    public void setLockingConfigMap(POILockingConfigMap poiLockingConfigMap) {
        this.poiLockingConfigMap = poiLockingConfigMap;
    }
}

