package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.config.config_maps.*;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

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
    @SerializedName("Narrator Menu")
    private NarratorMenuConfigMap narratorMenuConfigMap;
    @SerializedName("Other Configurations")
    private OtherConfigsMap otherConfigsMap;

    public static ConfigMap buildDefault() {
        ConfigMap m = new ConfigMap();
        m.setDefaultCameraControlsConfigMap();
        m.setDefaultInventoryControlsConfigMap();
        m.poiConfigMap = POIConfigMap.buildDefault();
        m.setDefaultPlayerWarningConfigMap();
        m.setFallDetectorConfigMap(FallDetectorConfigMap.defaultFallDetectorConfigMap());
        m.setDefaultReadCrosshairConfigMap();
        m.setOtherConfigsMap(OtherConfigsMap.getDefaultOtherConfigsMap());
        m.setNarratorMenuConfigMap(NarratorMenuConfigMap.getDefaultNarratorMenuConfigMap());
        return m;
    }

    public static void setInstance(ConfigMap map) {
        POIConfigMap.setInstance(map.poiConfigMap);
    }

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
        defaultInventoryControlsConfigMap.setDelayInMilliseconds(150);

        setInventoryControlsConfigMap(defaultInventoryControlsConfigMap);
    }

    public POIConfigMap getPoiConfigMap() {
        return poiConfigMap;
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
        defaultReadCrosshairConfigMap.setRepeatSpeakingInterval(0L);

        setReadCrosshairConfigMap(defaultReadCrosshairConfigMap);
    }

    public OtherConfigsMap getOtherConfigsMap() {
        return otherConfigsMap;
    }

    public void setOtherConfigsMap(OtherConfigsMap otherConfigsMap) {
        this.otherConfigsMap = otherConfigsMap;
    }

    public NarratorMenuConfigMap getNarratorMenuConfigMap() {
        return narratorMenuConfigMap;
    }

    public void setNarratorMenuConfigMap(NarratorMenuConfigMap narratorMenuConfigMap) {
        this.narratorMenuConfigMap = narratorMenuConfigMap;
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean validate() {
        if (this.getCameraControlsConfigMap() == null) return false;
        if (this.getInventoryControlsConfigMap() == null) return false;
        if (this.getPlayerWarningConfigMap() == null) return false;
        if (this.getOtherConfigsMap() == null) return false;
        if (this.getReadCrosshairConfigMap() == null) return false;
        if (Objects.isNull(this.poiConfigMap) || !this.poiConfigMap.validate()) return false;
        if (this.getFallDetectorConfigMap() == null) return false;
        if(this.getNarratorMenuConfigMap() == null) return false;

        return true;
    }
}