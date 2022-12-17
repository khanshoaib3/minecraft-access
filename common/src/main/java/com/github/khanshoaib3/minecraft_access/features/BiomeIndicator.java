package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;

/**
 * Narrates the name of the biome when entering a different biome.
 */
public class BiomeIndicator {
    private String previousBiome;

    public BiomeIndicator() {
        previousBiome = "";
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.player == null) return;

            /*// Identifier id = minecraftClient.world.getRegistryManager().get(Registry.BIOME_KEY).getId(minecraftClient.world.getBiome(minecraftClient.player.getBlockPos()).value()); // Pre 1.19.3
            Identifier id = minecraftClient.world.getRegistryManager().get(Registries.BIOME_SOURCE.getEntry()).get(minecraftClient.world.getBiome(minecraftClient.player.getBlockPos()).getKey());
            if (id == null) {
                MainClass.errorLog("\nUnable to detect biome!!\n");
                return;
            }

            String name = I18n.translate("biome." + id.getNamespace() + "." + id.getPath());

            if (!previousBiome.equalsIgnoreCase(name)) {
                previousBiome = name;
                MainClass.speakWithNarrator(I18n.translate("minecraft_access.other.biome", name), true);
            }*/

        } catch (Exception e) {
            MainClass.errorLog("An error occurred while narrating biome.");
            e.printStackTrace();
        }
    }
}
