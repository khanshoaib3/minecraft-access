package com.github.khanshoaib3.minecraft_access.config.config_maps;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.google.gson.annotations.SerializedName;

import java.util.Objects;
import java.util.stream.Stream;

public class POIConfigMap {

    private static POIConfigMap instance;

    @SerializedName("Blocks")
    private POIBlocksConfigMap poiBlocksConfigMap;
    @SerializedName("Entities")
    private POIEntitiesConfigMap poiEntitiesConfigMap;
    @SerializedName("Entities/Blocks Locking")
    private POILockingConfigMap poiLockingConfigMap;
    @SerializedName("Entities/Blocks Marking")
    private POIMarkingConfigMap poiMarkingConfigMap;

    private POIConfigMap() {
    }

    public static POIConfigMap buildDefault() {
        POIConfigMap m = new POIConfigMap();
        m.poiBlocksConfigMap = POIBlocksConfigMap.buildDefault();
        m.poiEntitiesConfigMap = POIEntitiesConfigMap.buildDefault();
        m.poiLockingConfigMap = POILockingConfigMap.buildDefault();
        m.poiMarkingConfigMap = POIMarkingConfigMap.buildDefault();

        setInstance(m);
        return m;
    }

    public static POIConfigMap getInstance() {
        if (instance == null) Config.getInstance().loadConfig();
        return instance;
    }

    public static void setInstance(POIConfigMap map) {
        // Only leaf config maps (those will be directly used in feature classes)
        // has real singleton static class variable.
        // Parent config maps has responsibility for maintaining child maps' singleton instances.
        POIBlocksConfigMap.setInstance(map.poiBlocksConfigMap);
        POIEntitiesConfigMap.setInstance(map.poiEntitiesConfigMap);
        POILockingConfigMap.setInstance(map.poiLockingConfigMap);
        POIMarkingConfigMap.setInstance(map.poiMarkingConfigMap);
        instance = map;
    }

    public boolean validate() {
        return Stream.of(poiBlocksConfigMap, poiEntitiesConfigMap, poiLockingConfigMap, poiMarkingConfigMap).allMatch(Objects::nonNull);
    }
}

