package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

public class POIMarkingConfigMap {
    private static POIMarkingConfigMap instance;

    @SerializedName("Enabled")
    private boolean enabled;

    @SerializedName("Suppress Other POI When Marking On")
    private boolean suppressOtherWhenEnabled;

    private POIMarkingConfigMap() {
    }

    public static POIMarkingConfigMap buildDefault() {
        POIMarkingConfigMap m = new POIMarkingConfigMap();
        m.enabled = true;
        m.suppressOtherWhenEnabled = true;

        // leaf config maps should maintain their singletons during config-reset
        setInstance(m);
        return m;
    }

    public static void setInstance(POIMarkingConfigMap map) {
        instance = map;
    }

    public static POIMarkingConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean poiMarkingEnabled) {
        this.enabled = poiMarkingEnabled;
    }

    public boolean isSuppressOtherWhenEnabled() {
        return suppressOtherWhenEnabled;
    }

    public void setSuppressOtherWhenEnabled(boolean suppressOtherWhenEnabled) {
        this.suppressOtherWhenEnabled = suppressOtherWhenEnabled;
    }
}
