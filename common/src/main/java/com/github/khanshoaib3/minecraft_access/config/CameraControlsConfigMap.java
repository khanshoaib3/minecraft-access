package com.github.khanshoaib3.minecraft_access.config;

public class CameraControlsConfigMap {
    private boolean enabled;
    private float normalRotatingAngle;
    private float modifiedRotatingAngle;
    private int delayInMilliseconds;

    public int getDelayInMilliseconds() {
        return delayInMilliseconds;
    }

    public void setDelayInMilliseconds(int delayInMilliseconds) {
        this.delayInMilliseconds = delayInMilliseconds;
    }

    public float getModifiedRotatingAngle() {
        return modifiedRotatingAngle;
    }

    public void setModifiedRotatingAngle(float modifiedRotatingAngle) {
        this.modifiedRotatingAngle = modifiedRotatingAngle;
    }

    public float getNormalRotatingAngle() {
        return normalRotatingAngle;
    }

    public void setNormalRotatingAngle(float normalRotatingAngle) {
        this.normalRotatingAngle = normalRotatingAngle;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
}
