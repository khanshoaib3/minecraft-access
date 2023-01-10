package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.CameraControlsConfigMap;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.InventoryControlsConfigMap;

public class ConfigMap {
    private CameraControlsConfigMap cameraControlsConfigMap;
    private InventoryControlsConfigMap inventoryControlsConfigMap;

    @JsonProperty("Camera Controls")
    public CameraControlsConfigMap getCameraControlsConfigMap() {
        return cameraControlsConfigMap;
    }

    public void setDefaultCameraControlsConfigMap() {
        CameraControlsConfigMap newCameraControlsConfigMap = new CameraControlsConfigMap();
        newCameraControlsConfigMap.setEnabled(true);
        newCameraControlsConfigMap.setNormalRotatingAngle(22.5f);
        newCameraControlsConfigMap.setModifiedRotatingAngle(11.25f);
        newCameraControlsConfigMap.setDelayInMilliseconds(250);

        this.cameraControlsConfigMap = newCameraControlsConfigMap;
    }

    @JsonProperty("Inventory Controls")
    public InventoryControlsConfigMap getInventoryControlsConfigMap() {
        return inventoryControlsConfigMap;
    }

    public void setDefaultInventoryControlsConfigMap() {
        InventoryControlsConfigMap newInventoryControlsConfigMap = new InventoryControlsConfigMap();
        newInventoryControlsConfigMap.setEnabled(true);
        newInventoryControlsConfigMap.setAutoOpenRecipeBook(true);
        newInventoryControlsConfigMap.setRowAndColumnFormat("%dx%d");
        newInventoryControlsConfigMap.setDelayInMilliseconds(250);

        this.inventoryControlsConfigMap = newInventoryControlsConfigMap;
    }
}

