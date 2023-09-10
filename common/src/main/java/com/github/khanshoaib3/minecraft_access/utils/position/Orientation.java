package com.github.khanshoaib3.minecraft_access.utils.position;

import java.util.Arrays;
import java.util.Comparator;

/**
 * {@link net.minecraft.util.math.Direction} is not enough for this mod,
 * we need additional four horizontal directions (list north-east).
 */
@SuppressWarnings("unused")
public enum Orientation {
    /**
     * It's opposite direction is itself
     */
    UNKNOWN(-1, -1, 0, 0, 0),
    NORTH(0, 1, 0, 0, -1),
    SOUTH(1, 0, 0, 0, 1),
    EAST(2, 3, 1, 0, 0),
    WEST(3, 2, -1, 0, 0),
    NORTH_EAST(4, 7, 1, 0, -1),
    NORTH_WEST(5, 6, -1, 0, -1),
    SOUTH_EAST(6, 5, 1, 0, 1),
    SOUTH_WEST(7, 4, -1, 0, 1),
    UP(8, 9, 0, 1, 0),
    DOWN(9, 8, 0, -1, 0),
    ;

    private final int id;
    private final int idOpposite;
    /**
     * Remove the UNKNOWN since it's not a valid direction
     */
    private static final Orientation[] ALL = Arrays.stream(values())
            .filter((orientation) -> orientation != UNKNOWN)
            .toArray(Orientation[]::new);
    private static final Orientation[] VALUES = Arrays.stream(ALL)
            .sorted(Comparator.comparingInt((direction) -> direction.id))
            .toArray(Orientation[]::new);

    public final int x;
    public final int y;
    public final int z;

    Orientation(int id, int idOpposite, int x, int y, int z) {
        this.id = id;
        this.idOpposite = idOpposite;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    /**
     * Convert direction strings like "north", "up" to enum.
     */
    public static Orientation parse(String s) {
        try {
            return Orientation.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

    /**
     * Convert direction enum to string.
     */
    @Override
    public String toString() {
        return name().toLowerCase();
    }

    public Orientation getOpposite() {
        return byId(this.idOpposite);
    }

    public static Orientation byId(int id) {
        if (id == UNKNOWN.id) return UNKNOWN;
        return VALUES[Math.abs(id % VALUES.length)];
    }
}
