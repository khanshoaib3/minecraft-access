package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.CameraControlsConfigMap;

public class ConfigMap {
    private CameraControlsConfigMap cameraControlsConfigMap;

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
}

