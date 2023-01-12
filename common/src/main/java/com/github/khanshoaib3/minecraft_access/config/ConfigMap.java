package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.*;

public class ConfigMap {
    private CameraControlsConfigMap cameraControlsConfigMap;
    private InventoryControlsConfigMap inventoryControlsConfigMap;
    private POIConfigMap poiConfigMap;

    @JsonProperty("Camera Controls")
    public CameraControlsConfigMap getCameraControlsConfigMap() {
        return cameraControlsConfigMap;
    }

    public void setCameraControlsConfigMap(CameraControlsConfigMap cameraControlsConfigMap) {
        this.cameraControlsConfigMap = cameraControlsConfigMap;
    }

    public void setDefaultCameraControlsConfigMap() {
        CameraControlsConfigMap defaultCameraControlsConfigMap = new CameraControlsConfigMap();
        defaultCameraControlsConfigMap.setEnabled(true);
        defaultCameraControlsConfigMap.setNormalRotatingAngle(22.5f);
        defaultCameraControlsConfigMap.setModifiedRotatingAngle(11.25f);
        defaultCameraControlsConfigMap.setDelayInMilliseconds(250);

        setCameraControlsConfigMap(defaultCameraControlsConfigMap);
    }

    @JsonProperty("Inventory Controls")
    public InventoryControlsConfigMap getInventoryControlsConfigMap() {
        return inventoryControlsConfigMap;
    }

    public void setInventoryControlsConfigMap(InventoryControlsConfigMap inventoryControlsConfigMap) {
        this.inventoryControlsConfigMap = inventoryControlsConfigMap;
    }

    public void setDefaultInventoryControlsConfigMap() {
        InventoryControlsConfigMap defaultInventoryControlsConfigMap = new InventoryControlsConfigMap();
        defaultInventoryControlsConfigMap.setEnabled(true);
        defaultInventoryControlsConfigMap.setAutoOpenRecipeBook(true);
        defaultInventoryControlsConfigMap.setRowAndColumnFormat("%dx%d");
        defaultInventoryControlsConfigMap.setDelayInMilliseconds(250);

        setInventoryControlsConfigMap(defaultInventoryControlsConfigMap);
    }

    @JsonProperty("Points of Interest")
    public POIConfigMap getPoiConfigMap() {
        return poiConfigMap;
    }

    public void setPoiConfigMap(POIConfigMap poiConfigMap) {
        this.poiConfigMap = poiConfigMap;
    }

    public void setDefaultPoiConfigMap() {
        POIConfigMap defaultPoiConfigMap = new POIConfigMap();

        POIBlocksConfigMap defaultPoiBlocksConfigMap = new POIBlocksConfigMap();
        defaultPoiBlocksConfigMap.setEnabled(true);
        defaultPoiBlocksConfigMap.setDetectFluidBlocks(true);
        defaultPoiBlocksConfigMap.setRange(6);
        defaultPoiBlocksConfigMap.setPlaySound(true);
        defaultPoiBlocksConfigMap.setVolume(0.25f);
        defaultPoiBlocksConfigMap.setPlaySoundForOtherBlocks(false);
        defaultPoiBlocksConfigMap.setDelay(3000);
        defaultPoiConfigMap.setBlocksConfigMap(defaultPoiBlocksConfigMap);

        POIEntitiesConfigMap defaultPoiEntitiesConfigMap = new POIEntitiesConfigMap();
        defaultPoiConfigMap.setEntitiesConfigMap(defaultPoiEntitiesConfigMap);

        POILockingConfigMap defaultPoiLockingConfigMap = new POILockingConfigMap();
        defaultPoiLockingConfigMap.setEnabled(true);
        defaultPoiLockingConfigMap.setLockOnBlocks(true);
        defaultPoiLockingConfigMap.setSpeakDistance(false);
        defaultPoiLockingConfigMap.setUnlockingSound(true);
        defaultPoiLockingConfigMap.setAutoLockEyeOfEnderEntity(false);
        defaultPoiLockingConfigMap.setDelay(100);
        defaultPoiConfigMap.setLockingConfigMap(defaultPoiLockingConfigMap);

        setPoiConfigMap(defaultPoiConfigMap);
    }
}

