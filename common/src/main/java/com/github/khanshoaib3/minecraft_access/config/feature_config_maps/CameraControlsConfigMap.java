package com.github.khanshoaib3.minecraft_access.config.feature_config_maps;

import com.google.gson.annotations.SerializedName;

public class CameraControlsConfigMap {
    @SerializedName("Enabled")
    private boolean enabled;
    @SerializedName("Normal Rotating Angle")
    private float normalRotatingAngle;
    @SerializedName("Modified Rotating Angle (on holding left alt)")
    private float modifiedRotatingAngle;
    @SerializedName("Delay (in milliseconds)")
    private int delayInMilliseconds;

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
