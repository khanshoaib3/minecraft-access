package com.github.khanshoaib3.minecraft_access.config;

import com.fasterxml.jackson.annotation.JsonProperty;

public class CameraControlsConfigMap {
    private boolean enabled;
    private float normalRotatingAngle;
    private float modifiedRotatingAngle;
    private int delayInMilliseconds;

    @JsonProperty("Enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @JsonProperty("Normal Rotating Angle")
    public float getNormalRotatingAngle() {
        return normalRotatingAngle;
    }

    public void setNormalRotatingAngle(float normalRotatingAngle) {
        this.normalRotatingAngle = normalRotatingAngle;
    }

    @JsonProperty("Modified Rotating Angle (on holding left alt)")
    public float getModifiedRotatingAngle() {
        return modifiedRotatingAngle;
    }

    public void setModifiedRotatingAngle(float modifiedRotatingAngle) {
        this.modifiedRotatingAngle = modifiedRotatingAngle;
    }

    @JsonProperty("Delay (in milliseconds)")
    public int getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }
}
