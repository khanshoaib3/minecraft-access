package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;

public class POIMarking {
    private static final POIMarking instance;
    private static final POIBlocks poiBlocks;
    private static final POIEntities poiEntities;
    private static POIMarkingConfigMap config;

    static {
        instance = new POIMarking();
        poiBlocks = POIBlocks.getInstance();
        poiEntities = POIEntities.getInstance();
    }

    public static POIMarking getInstance() {
        return instance;
    }

    /**
     * Perform this feature before the normal POI scan,
     * and suppress the normal POI scan (by switching their targets to marked target)
     * if this feature is enabled.
     */
    public void update() {
        // update config
        config = POIMarkingConfigMap.getInstance();
        mainLogic();
        // trigger POI updates
        poiBlocks.update();
        poiEntities.update();
    }

    private void mainLogic() {

    }
}
