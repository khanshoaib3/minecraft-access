package com.github.khanshoaib3.minecraft_access.utils;

import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.world.World;

/**
 * This class provides delegate calls to {@link ClientPlayerEntity}.
 * The main reason for this class is that {@link ClientPlayerEntity} cannot be mocked by Mockito. <p>
 * ({@link ClientPlayerEntity} constructor requires -> {@link ClientWorld} constructor -> {@link World} static init block -> {@link RegistryKeys},
 * somehow the {@link RegistryKeys} cannot finish its static assignments in class loading.
 * We can replace Mockito with more powerful PowerMock to resolve this problem, but PowerMock is sticking on Junit 4,
 * we can't go back to Junit 4 from 5 since some of the mechanisms currently used for unit testing have no alternatives in 4.
 * Forgive me for doing this, but it's the most economical way.)
 */
public class ClientPlayerEntityProxy {

    public static void playSound(RegistryEntry.Reference<SoundEvent> sound, float volume, float pitch) {
        WorldUtils.getClientPlayer().orElseThrow().playSound(sound.value(), volume, pitch);
    }
}
