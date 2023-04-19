package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.*;
import com.github.khanshoaib3.minecraft_access.features.PositionNarrator;
import com.google.gson.annotations.SerializedName;

public class ConfigMap {
    @SerializedName("Camera Controls")
    private CameraControlsConfigMap cameraControlsConfigMap;
    @SerializedName("Inventory Controls")
    private InventoryControlsConfigMap inventoryControlsConfigMap;
    @SerializedName("Points of Interest")
    private POIConfigMap poiConfigMap;
    @SerializedName("Player Warnings")
    private PlayerWarningConfigMap playerWarningConfigMap;
    @SerializedName("Fall Detector")
    private FallDetectorConfigMap fallDetectorConfigMap;
    @SerializedName("Read Crosshair")
    private ReadCrosshairConfigMap readCrosshairConfigMap;
    @SerializedName("Other Configurations")
    private OtherConfigsMap otherConfigsMap;

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
        defaultPoiLockingConfigMap.setAutoLockEyeOfEnderEntity(true);
        defaultPoiLockingConfigMap.setDelay(100);
        defaultPoiConfigMap.setLockingConfigMap(defaultPoiLockingConfigMap);

        setPoiConfigMap(defaultPoiConfigMap);
    }

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

    public FallDetectorConfigMap getFallDetectorConfigMap() {
        return fallDetectorConfigMap;
    }

    public void setFallDetectorConfigMap(FallDetectorConfigMap fallDetectorConfigMap) {
        this.fallDetectorConfigMap = fallDetectorConfigMap;
    }

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

    public OtherConfigsMap getOtherConfigsMap() {
        return otherConfigsMap;
    }

    public void setOtherConfigsMap(OtherConfigsMap otherConfigsMap) {
        this.otherConfigsMap = otherConfigsMap;
    }

    public void setDefaultOtherConfigsMap() {
        OtherConfigsMap defaultOtherConfigsMap = new OtherConfigsMap();
        defaultOtherConfigsMap.setBiomeIndicatorEnabled(true);
        defaultOtherConfigsMap.setXpIndicatorEnabled(true);
        defaultOtherConfigsMap.setFacingDirectionEnabled(true);
        defaultOtherConfigsMap.setHealthNHungerEnabled(true);
        defaultOtherConfigsMap.setPositionNarratorEnabled(true);
        defaultOtherConfigsMap.setPositionNarratorFormat(PositionNarrator.defaultFormat);
        defaultOtherConfigsMap.setUse12HourTimeFormat(false);
        defaultOtherConfigsMap.setNarratorMenuEnabled(true);
        defaultOtherConfigsMap.setMenuFixEnabled(true);
        defaultOtherConfigsMap.setDebugMode(false);

        setOtherConfigsMap(defaultOtherConfigsMap);
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean validate() {
        if (this.getCameraControlsConfigMap() == null) return false;
        if (this.getInventoryControlsConfigMap() == null) return false;
        if (this.getPlayerWarningConfigMap() == null) return false;
        if (this.getOtherConfigsMap() == null) return false;
        if (this.getReadCrosshairConfigMap() == null) return false;
        if (this.getPoiConfigMap() == null || this.getPoiConfigMap().getEntitiesConfigMap() == null ||
                this.getPoiConfigMap().getLockingConfigMap() == null || this.getPoiConfigMap().getBlocksConfigMap() == null)
            return false;
        if (this.getFallDetectorConfigMap() == null) return false;

        return true;
    }
}