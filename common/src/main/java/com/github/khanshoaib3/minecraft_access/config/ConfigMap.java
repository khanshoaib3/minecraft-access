package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.config.config_maps.*;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;

/**
 * Pure DTO for mapping structure of config json via Gson.
 */
public class ConfigMap {

    private static ConfigMap instance;

    @SerializedName("Camera Controls")
    private CameraControlsConfigMap cameraControlsConfigMap;
    @SerializedName("Inventory Controls")
    private InventoryControlsConfigMap inventoryControlsConfigMap;
    @SerializedName("Mouse Simulation")
    private MouseSimulationConfigMap mouseSimulationConfigMap;
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
        m.cameraControlsConfigMap = CameraControlsConfigMap.buildDefault();
        m.inventoryControlsConfigMap = InventoryControlsConfigMap.buildDefault();
        m.mouseSimulationConfigMap = MouseSimulationConfigMap.buildDefault();
        m.poiConfigMap = POIConfigMap.buildDefault();
        m.playerWarningConfigMap = PlayerWarningConfigMap.buildDefault();
        m.fallDetectorConfigMap = FallDetectorConfigMap.buildDefault();
        m.readCrosshairConfigMap = ReadCrosshairConfigMap.buildDefault();
        m.otherConfigsMap = OtherConfigsMap.buildDefault();
        m.narratorMenuConfigMap = NarratorMenuConfigMap.buildDefault();
        return m;
    }

    public static void setInstance(ConfigMap map) {
        CameraControlsConfigMap.setInstance(map.cameraControlsConfigMap);
        FallDetectorConfigMap.setInstance(map.fallDetectorConfigMap);
        InventoryControlsConfigMap.setInstance(map.inventoryControlsConfigMap);
        MouseSimulationConfigMap.setInstance(map.mouseSimulationConfigMap);
        NarratorMenuConfigMap.setInstance(map.narratorMenuConfigMap);
        OtherConfigsMap.setInstance(map.otherConfigsMap);
        PlayerWarningConfigMap.setInstance(map.playerWarningConfigMap);
        POIConfigMap.setInstance(map.poiConfigMap);
        ReadCrosshairConfigMap.setInstance(map.readCrosshairConfigMap);
        instance = map;
    }

    public static ConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    @SuppressWarnings("RedundantIfStatement")
    public boolean validate() {
        if (Objects.isNull(this.cameraControlsConfigMap)) return false;
        if (Objects.isNull(this.inventoryControlsConfigMap)) return false;
        if (Objects.isNull(this.mouseSimulationConfigMap)) return false;
        if (Objects.isNull(this.playerWarningConfigMap)) return false;
        if (Objects.isNull(this.otherConfigsMap)) return false;
        if (Objects.isNull(this.readCrosshairConfigMap) || !this.readCrosshairConfigMap.validate()) return false;
        if (Objects.isNull(this.poiConfigMap) || !this.poiConfigMap.validate()) return false;
        if (Objects.isNull(this.fallDetectorConfigMap)) return false;
        if (Objects.isNull(this.narratorMenuConfigMap) || !this.narratorMenuConfigMap.validate()) return false;

        return true;
    }
}