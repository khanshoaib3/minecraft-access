package com.github.khanshoaib3.minecraft_access.utils.position;

import net.minecraft.util.math.Vec3i;

import java.util.Arrays;
import java.util.Comparator;

/**
 * The {@link net.minecraft.util.math.Direction} is not enough for this mod.
 */
@SuppressWarnings("unused")
public enum Orientation {
    NORTH(0, 1, LAYER.MIDDLE, new Vec3i(0, 0, -1)),
    SOUTH(1, 0, LAYER.MIDDLE, new Vec3i(0, 0, 1)),
    EAST(2, 3, LAYER.MIDDLE, new Vec3i(1, 0, 0)),
    WEST(3, 2, LAYER.MIDDLE, new Vec3i(-1, 0, 0)),
    NORTH_EAST(4, 7, LAYER.MIDDLE, new Vec3i(1, 0, -1)),
    NORTH_WEST(5, 6, LAYER.MIDDLE, new Vec3i(-1, 0, -1)),
    SOUTH_EAST(6, 5, LAYER.MIDDLE, new Vec3i(1, 0, 1)),
    SOUTH_WEST(7, 4, LAYER.MIDDLE, new Vec3i(-1, 0, 1)),
    UP(8, 9, LAYER.UPPER, new Vec3i(0, 1, 0)),
    DOWN(9, 8, LAYER.LOWER, new Vec3i(0, -1, 0)),
    CENTER(10, 10, LAYER.MIDDLE, new Vec3i(0, 0, 0)),
    ;

    private final int id;
    private final int idOpposite;

    private static final Orientation[] ALL = Arrays.stream(values()).toArray(Orientation[]::new);
    private static final Orientation[] VALUES = Arrays.stream(ALL)
            .sorted(Comparator.comparingInt((direction) -> direction.id))
            .toArray(Orientation[]::new);

    public final Vec3i vector;
    public final LAYER layer;

    Orientation(int id, int idOpposite, Orientation.LAYER layer, Vec3i vector) {
        this.id = id;
        this.idOpposite = idOpposite;
        this.layer = layer;
        this.vector = vector;
    }

    public static Orientation of(String s) {
        try {
            return Orientation.valueOf(s.toUpperCase());
        } catch (IllegalArgumentException e) {
            return CENTER;
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
        return VALUES[Math.abs(id % VALUES.length)];
    }

    public enum LAYER {
        UPPER, MIDDLE, LOWER;
    }
}
