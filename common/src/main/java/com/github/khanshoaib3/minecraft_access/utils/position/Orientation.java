package com.github.khanshoaib3.minecraft_access.utils.position;

/**
 * {@link net.minecraft.util.math.Direction} is not enough for this mod,
 * we need additional four horizontal directions (list north-east).
 */
public enum Orientation {
    NORTH(0, 0, -1),
    EAST(1, 0, 0),
    WEST(-1, 0, 0),
    SOUTH(0, 0, 1),
    NORTH_EAST(1, 0, -1),
    NORTH_WEST(-1, 0, -1),
    SOUTH_EAST(1, 0, 1),
    SOUTH_WEST(-1, 0, 1),
    UP(0, 1, 0),
    DOWN(0, -1, 0),
    ;

    public final int x;
    public final int y;
    public final int z;

    Orientation(int x, int y, int z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }
}
