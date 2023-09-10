package com.github.khanshoaib3.minecraft_access.utils.position;

import net.minecraft.util.math.Vec3i;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@link net.minecraft.util.math.Direction} is not enough for this mod.
 */
@SuppressWarnings("unused")
public enum Orientation {
    /**
     * It's opposite direction is itself
     */
    UNKNOWN(-1, -1, new Vec3i(0, 0, 0)),
    NORTH(0, 1, new Vec3i(0, 0, -1)),
    SOUTH(1, 0, new Vec3i(0, 0, 1)),
    EAST(2, 3, new Vec3i(1, 0, 0)),
    WEST(3, 2, new Vec3i(-1, 0, 0)),
    NORTH_EAST(4, 7, new Vec3i(1, 0, -1)),
    NORTH_WEST(5, 6, new Vec3i(-1, 0, -1)),
    SOUTH_EAST(6, 5, new Vec3i(1, 0, 1)),
    SOUTH_WEST(7, 4, new Vec3i(-1, 0, 1)),
    UP(8, 9, new Vec3i(0, 1, 0)),
    DOWN(9, 8, new Vec3i(0, -1, 0)),
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
    private static final Orientation[] HORIZONTAL = Arrays.stream(ALL)
            .filter((direction) -> direction.isHorizontal)
            .toArray(Orientation[]::new);
    private static final Orientation[] VERTICAL = Arrays.stream(ALL)
            .filter((direction) -> !direction.isHorizontal)
            .toArray(Orientation[]::new);

    public final Vec3i vector;
    public final boolean isHorizontal;

    Orientation(int id, int idOpposite, Vec3i vector) {
        this.id = id;
        this.idOpposite = idOpposite;
        this.vector = vector;
        this.isHorizontal = vector.getY() == 0;
    }

    public static Orientation of(String s) {
        try {
            return Orientation.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return UNKNOWN;
        }
    }

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
