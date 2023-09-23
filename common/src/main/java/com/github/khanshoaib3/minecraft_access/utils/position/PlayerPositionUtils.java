package com.github.khanshoaib3.minecraft_access.utils.position;

import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * Functions about getting player entity's position, facing direction etc.
 */
public class PlayerPositionUtils {
    private final PlayerEntity player;

    public PlayerPositionUtils(MinecraftClient client) {
        this.player = client.player;
    }

    public static double getX() {
        String tempPosX = String.valueOf(getPlayerPosition().orElseThrow().x);
        tempPosX = tempPosX.substring(0, tempPosX.indexOf(".") + 2);

        return Double.parseDouble(tempPosX);
    }

    public static double getY() {
        String tempPosY;
        tempPosY = String.valueOf(getPlayerPosition().orElseThrow().y);
        tempPosY = tempPosY.substring(0, tempPosY.indexOf(".") + 2);

        return Double.parseDouble(tempPosY);
    }

    public static double getZ() {
        String tempPosZ;
        tempPosZ = String.valueOf(getPlayerPosition().orElseThrow().z);
        tempPosZ = tempPosZ.substring(0, tempPosZ.indexOf(".") + 2);

        return Double.parseDouble(tempPosZ);
    }

    public static Optional<Vec3d> getPlayerPosition() {
        MinecraftClient client = MinecraftClient.getInstance();
        return client.player == null ? java.util.Optional.empty() : Optional.of(client.player.getPos());
    }

    public static Optional<BlockPos> getPlayerBlockPosition() {
        Optional<Vec3d> op = getPlayerPosition();
        if (op.isEmpty()) return Optional.empty();
        Vec3d p = op.get();
        return Optional.of(new BlockPos(p));
    }

    public static String getI18NPosition() {
        String format = OtherConfigsMap.getInstance().getPositionNarratorFormat();

        // check if configured format is valid
        if (Objects.isNull(format) || !Stream.of("{x}", "{y}", "{z}").allMatch(format::contains)) {
            format = OtherConfigsMap.DEFAULT_POSITION_FORMAT;
        }

        String posX = PositionUtils.getNarratableNumber(getX());
        String posY = PositionUtils.getNarratableNumber(getY());
        String posZ = PositionUtils.getNarratableNumber(getZ());

        return format.replace("{x}", posX).replace("{y}", posY).replace("{z}", posZ);
    }

    public static String getNarratableXPos() {
        return PositionUtils.getNarratableNumber(getX()) + "x";
    }

    public static String getNarratableYPos() {
        return PositionUtils.getNarratableNumber(getY()) + "y";
    }

    public static String getNarratableZPos() {
        return PositionUtils.getNarratableNumber(getZ()) + "z";
    }

    public int getVerticalFacingDirection() {
        assert player != null;
        return (int) player.getRotationClient().x;
    }

    /**
     * Get the vertical direction in words.
     *
     * @return the vertical direction in words. null on error.
     */
    public @Nullable String getVerticalFacingDirectionInWords() {
        if (MinecraftClient.getInstance() == null) return null;
        if (MinecraftClient.getInstance().player == null) return null;

        int angle = getVerticalFacingDirection();
        if (angle == -999) return null;

        String angleInWords = null;

        if (angle >= -2 && angle <= 2)
            angleInWords = I18n.translate("minecraft_access.direction.straight");
        else if (angle <= -88 && angle >= -90)
            angleInWords = I18n.translate("minecraft_access.direction.up");
        else if (angle >= 88 && angle <= 90)
            angleInWords = I18n.translate("minecraft_access.direction.down");

        return angleInWords;
    }

    public int getHorizontalFacingDirectionInDegrees() {
        assert player != null;
        int angle = (int) player.getRotationClient().y;

        while (angle >= 360) angle -= 360;
        while (angle <= -360) angle += 360;

        return angle;
    }

    public String getHorizontalFacingDirectionInCardinal() {
        return getHorizontalFacingDirectionInCardinal(false, false);
    }

    @SuppressWarnings("unused")
    public String getHorizontalFacingDirectionInCardinal(boolean onlyDirectionKey) {
        return getHorizontalFacingDirectionInCardinal(onlyDirectionKey, false);
    }

    /**
     * Get the horizontal direction in words.
     *
     * @param onlyDirectionKey  directly return the direction word without i18n it.
     * @param oppositeDirection output the opposite direction instead.
     * @return onlyDirectionKey ? direction word : translated direction
     */
    public String getHorizontalFacingDirectionInCardinal(boolean onlyDirectionKey, boolean oppositeDirection) {
        assert player != null;

        int angle = getHorizontalFacingDirectionInDegrees();
        String direction;

        if ((angle >= -150 && angle <= -120) || (angle >= 210 && angle <= 240)) {
            // Looking North East
            direction = "north_east";
        } else if ((angle >= -60 && angle <= -30) || (angle >= 300 && angle <= 330)) {
            // Looking South East
            direction = "south_east";
        } else if ((angle >= 30 && angle <= 60) || (angle >= -330 && angle <= -300)) {
            // Looking South West
            direction = "south_west";
        } else if ((angle >= 120 && angle <= 150) || (angle >= -240 && angle <= -210)) {
            // Looking North West
            direction = "north_west";
        } else {
            direction = player.getHorizontalFacing().asString().toLowerCase();
        }

        if (oppositeDirection) direction = getOppositeDirectionKey(direction);

        if (onlyDirectionKey) return direction;
        else return I18n.translate("minecraft_access.direction." + direction);
    }

    public static String getOppositeDirectionKey(String originalDirectionKey) {
        return  Orientation.of(originalDirectionKey).getOpposite().toString();
    }
}
