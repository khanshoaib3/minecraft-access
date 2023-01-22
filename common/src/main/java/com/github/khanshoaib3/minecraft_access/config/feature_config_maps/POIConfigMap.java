package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

//import com.fasterxml.jackson.annotation.JsonProperty;

public class POIConfigMap {
    private POIBlocksConfigMap poiBlocksConfigMap;
    private POIEntitiesConfigMap poiEntitiesConfigMap;
    private POILockingConfigMap poiLockingConfigMap;

//    @JsonProperty("Blocks")
    public POIBlocksConfigMap getBlocksConfigMap() {
        return poiBlocksConfigMap;
    }

    public void setBlocksConfigMap(POIBlocksConfigMap poiBlocksConfigMap) {
        this.poiBlocksConfigMap = poiBlocksConfigMap;
    }

//    @JsonProperty("Entities")
    public POIEntitiesConfigMap getEntitiesConfigMap() {
        return poiEntitiesConfigMap;
    }

    public void setEntitiesConfigMap(POIEntitiesConfigMap poiEntitiesConfigMap) {
        this.poiEntitiesConfigMap = poiEntitiesConfigMap;
    }

//    @JsonProperty("Entities/Blocks Locking")
    public POILockingConfigMap getLockingConfigMap() {
        return poiLockingConfigMap;
    }

    public void setLockingConfigMap(POILockingConfigMap poiLockingConfigMap) {
        this.poiLockingConfigMap = poiLockingConfigMap;
    }
}

