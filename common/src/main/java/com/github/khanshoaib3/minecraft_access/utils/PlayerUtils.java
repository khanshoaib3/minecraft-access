package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.features.point_of_interest.BlockPos3d;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import java.util.Optional;

/**
 * This class provides delegate calls to {@link ClientPlayerEntity}.
 * The main reason for this class is that {@link ClientPlayerEntity} cannot be mocked by Mockito. <p>
 * ({@link ClientPlayerEntity} constructor requires -> {@link ClientWorld} constructor -> {@link World} static init block -> {@link RegistryKeys},
 * somehow the {@link RegistryKeys} cannot finish its static assignments in class loading.
 * We can replace Mockito with more powerful PowerMock to resolve this problem, but PowerMock is sticking on Junit 4,
 * we can't go back to Junit 4 from 5 since some of the mechanisms currently used for unit testing have no alternatives in 4.
 * Forgive me for doing this, but it's the most economical way.)
 */
public class PlayerUtils {

    public static void playSoundOnPlayer(RegistryEntry.Reference<SoundEvent> sound, float volume, float pitch) {
        WorldUtils.getClientPlayer().orElseThrow().playSound(sound.value(), volume, pitch);
    }

    public static void lookAt(Vec3d position) {
        WorldUtils.getClientPlayer().orElseThrow().lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, position);
    }

    public static void lookAt(Entity entity) {
        double aboutEntityHeadHeight = entity.getY() + entity.getHeight() - 0.25;
        Vec3d aboutEntityHeadPosition = new Vec3d(entity.getX(), aboutEntityHeadHeight, entity.getZ());
        lookAt(aboutEntityHeadPosition);
    }

    @SuppressWarnings("unused")
    public static void lookAt(BlockPos position) {
        lookAt(position.toCenterPos());
    }

    public static void lookAt(BlockPos3d position) {
        lookAt(position.getAccuratePosition());
    }

    public static int getExperienceLevel() {
        return WorldUtils.getClientPlayer().map(p -> p.experienceLevel).orElse(-999);
    }

    /**
     * @return percentage-based number
     */
    public static int getExperienceProgress() {
        return WorldUtils.getClientPlayer().map(p -> (int) (p.experienceProgress * 100)).orElse(-999);
    }

    public static boolean isInFluid() {
        ClientPlayerEntity player = WorldUtils.getClientPlayer().orElseThrow();
        return player.isSwimming()
                || player.isSubmergedInWater()
                || player.isInsideWaterOrBubbleColumn()
                || player.isInLava();
    }
}
