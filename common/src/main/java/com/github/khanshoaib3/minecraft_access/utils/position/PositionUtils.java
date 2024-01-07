package com.github.khanshoaib3.minecraft_access.utils.position;

import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.Vec3d;

/**
 * Functions about getting blocks' position.
 */
public class PositionUtils {

    /**
     * To indicate relative location between player and target.
     */
    public static void playRelativePositionSoundCue(Vec3d targetPosition, double maxDistance, RegistryEntry.Reference<SoundEvent> sound, double minVolume, double maxVolume) {
        Vec3d playerPos = PlayerPositionUtils.getPlayerPosition().orElseThrow();

        // Use pitch to represent relative elevation, the higher the sound the higher the target.
        // The range of pitch is [0.5, 2.0], calculated as: 2 ^ (x / 12), where x is [-12, 12].
        // ref: https://minecraft.wiki/w/Note_Block#Notes
        //
        // Since we have a custom distance,
        // the range of (targetY - playerY) is [-maxDistance, maxDistance],
        // so let the maxDistance be the denominator to map to the original range.
        float pitch = (float) Math.pow(2, (targetPosition.getY() - playerPos.y) / maxDistance);

        // Use volume to represent distance, the louder the sound the closer the distance.
        double distance = Math.sqrt(targetPosition.squaredDistanceTo(playerPos.x, playerPos.y, playerPos.z));
        // = base volume (minVolume) + the volume delta per block ((maxVolume - minVolume) / maxDistance)
        double volumeDeltaPerBlock = (maxVolume - minVolume) / maxDistance;
        float volume = (float) (minVolume + (maxDistance - distance) * volumeDeltaPerBlock);

        playSoundAtPosition(sound, volume, pitch, targetPosition);
    }

    public static void playSoundAtPosition(RegistryEntry.Reference<SoundEvent> sound, float volume, float pitch, Vec3d position) {
        // note that the useDistance param only works for positions 100 blocks away, check its code.
        WorldUtils.getClientWorld().orElseThrow().playSound(position.x, position.y, position.z, sound.value(), SoundCategory.BLOCKS, volume, pitch, true);
    }
}
