package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class POIConfigMap {
    @SerializedName("Blocks")
    private POIBlocksConfigMap poiBlocksConfigMap;
    @SerializedName("Entities")
    private POIEntitiesConfigMap poiEntitiesConfigMap;
    @SerializedName("Entities/Blocks Locking")
    private POILockingConfigMap poiLockingConfigMap;

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

