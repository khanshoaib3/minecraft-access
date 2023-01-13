package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.*;
import com.github.khanshoaib3.minecraft_access.features.PositionNarrator;

public class ConfigMap {
    private CameraControlsConfigMap cameraControlsConfigMap;
    private InventoryControlsConfigMap inventoryControlsConfigMap;
    private POIConfigMap poiConfigMap;
    private PlayerWarningConfigMap playerWarningConfigMap;
    private ReadCrosshairConfigMap readCrosshairConfigMap;
    private OtherConfigsMap otherConfigsMap;

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
        defaultPoiEntitiesConfigMap.setEnabled(true);
        defaultPoiEntitiesConfigMap.setRange(6);
        defaultPoiEntitiesConfigMap.setPlaySound(true);
        defaultPoiEntitiesConfigMap.setVolume(0.25f);
        defaultPoiEntitiesConfigMap.setDelay(3000);
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

    @JsonProperty("Player Warnings")
    public PlayerWarningConfigMap getPlayerWarningConfigMap() {
        return playerWarningConfigMap;
    }

    public void setPlayerWarningConfigMap(PlayerWarningConfigMap playerWarningConfigMap) {
        this.playerWarningConfigMap = playerWarningConfigMap;
    }

    public void setDefaultPlayerWarningConfigMap() {
        PlayerWarningConfigMap defaultPlayerWarningConfigMap = new PlayerWarningConfigMap();
        defaultPlayerWarningConfigMap.setEnabled(true);
        defaultPlayerWarningConfigMap.setPlaySound(true);
        defaultPlayerWarningConfigMap.setFirstHealthThreshold(6.0);
        defaultPlayerWarningConfigMap.setSecondHealthThreshold(3.0);
        defaultPlayerWarningConfigMap.setHungerThreshold(3.0);
        defaultPlayerWarningConfigMap.setAirThreshold(3.0);

        setPlayerWarningConfigMap(defaultPlayerWarningConfigMap);
    }

    @JsonProperty("Read Crosshair")
    public ReadCrosshairConfigMap getReadCrosshairConfigMap() {
        return readCrosshairConfigMap;
    }

    public void setReadCrosshairConfigMap(ReadCrosshairConfigMap readCrosshairConfigMap) {
        this.readCrosshairConfigMap = readCrosshairConfigMap;
    }

    public void setDefaultReadCrosshairConfigMap() {
        ReadCrosshairConfigMap defaultReadCrosshairConfigMap = new ReadCrosshairConfigMap();
        defaultReadCrosshairConfigMap.setEnabled(true);
        defaultReadCrosshairConfigMap.setSpeakSide(true);
        defaultReadCrosshairConfigMap.setDisableSpeakingConsecutiveBlocks(true);

        setReadCrosshairConfigMap(defaultReadCrosshairConfigMap);
    }

    @JsonProperty("Other Configurations")
    public OtherConfigsMap getOtherConfigsMap() {
        return otherConfigsMap;
    }

    public void setOtherConfigsMap(OtherConfigsMap otherConfigsMap) {
        this.otherConfigsMap = otherConfigsMap;
    }

    public void setDefaultOtherConfigsMap() {
        OtherConfigsMap defaultOtherConfigsMap = new OtherConfigsMap();
        defaultOtherConfigsMap.setBiomeIndicatorEnabled(true);
        defaultOtherConfigsMap.setFacingDirectionEnabled(true);
        defaultOtherConfigsMap.setHealthNHungerEnabled(true);
        defaultOtherConfigsMap.setPositionNarratorEnabled(true);
        defaultOtherConfigsMap.setPositionNarratorFormat(PositionNarrator.defaultFormat);
        defaultOtherConfigsMap.setNarratorMenuEnabled(true);
        defaultOtherConfigsMap.setMenuFixEnabled(true);
        defaultOtherConfigsMap.setDebugMode(false);

        setOtherConfigsMap(defaultOtherConfigsMap);
    }
}