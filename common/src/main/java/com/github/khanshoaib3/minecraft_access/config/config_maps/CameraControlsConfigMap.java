package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class CameraControlsConfigMap {

    private static CameraControlsConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Normal Rotating Angle")
    private float normalRotatingAngle;
    @SerializedName("Modified Rotating Angle (on holding left alt)")
    private float modifiedRotatingAngle;
    @SerializedName("Delay (in milliseconds)")
    private int delayInMilliseconds;

    private CameraControlsConfigMap() {
    }

    public static CameraControlsConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(CameraControlsConfigMap map) {
        instance = map;
    }

    public static CameraControlsConfigMap buildDefault() {
        CameraControlsConfigMap m = new CameraControlsConfigMap();
        m.setEnabled(true);
        m.setNormalRotatingAngle(22.5f);
        m.setModifiedRotatingAngle(11.25f);
        m.setDelayInMilliseconds(250);

        setInstance(m);
        return m;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public float getNormalRotatingAngle() {
        return normalRotatingAngle;
    }

    public void setNormalRotatingAngle(float normalRotatingAngle) {
        this.normalRotatingAngle = normalRotatingAngle;
    }

    public float getModifiedRotatingAngle() {
        return modifiedRotatingAngle;
    }

    public void setModifiedRotatingAngle(float modifiedRotatingAngle) {
        this.modifiedRotatingAngle = modifiedRotatingAngle;
    }

    public int getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }
}
