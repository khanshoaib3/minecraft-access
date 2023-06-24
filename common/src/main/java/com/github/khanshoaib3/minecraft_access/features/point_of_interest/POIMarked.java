package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

public class POIMarked {
    private static final POIMarked instance;

    static {
        instance = new POIMarked();
    }

    public static POIMarked getInstance() {
        return instance;
    }

    public void update() {

    }
}
