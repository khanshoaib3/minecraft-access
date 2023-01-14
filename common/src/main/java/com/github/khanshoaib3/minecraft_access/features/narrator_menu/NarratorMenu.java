package com.github.khanshoaib3.minecraft_access.features.narrator_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.features.BiomeIndicator;
import com.github.khanshoaib3.minecraft_access.features.ReadCrosshair;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityUtils;
import com.github.khanshoaib3.minecraft_access.utils.PositionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

// TODO add doc and update readme

/**
 * Narrator Menu Key (default: F4) = Opens a menu with few options.
 */
public class NarratorMenu {
    private static MinecraftClient minecraftClient;

    public void update() {
        try {
            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            boolean isNarratorMenuKeyPressed = MainClass.keyBindingsHandler.isPressed(MainClass.keyBindingsHandler.narratorMenuKey);

            if (isNarratorMenuKeyPressed && !MainClass.keyBindingsHandler.isF3KeyPressed()) {
                Screen screen = new NarratorMenuGUI("f4_menu");
                minecraftClient.setScreen(screen); // post 1.18
//                minecraftClient.openScreen(screen); // pre 1.18
            }
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in NarratorMenu.");
            e.printStackTrace();
        }
    }

    public static void getBlockAndFluidTargetInformation() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.cameraEntity == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            HitResult hit = minecraftClient.cameraEntity.raycast(20.0, 0.0f, true);
            if (hit == null)
                return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava()
                    && checkForFluidHit(minecraftClient, hit, false)) return;

            switch (hit.getType()) {
                case MISS, ENTITY ->
                        MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case BLOCK -> {
                    try {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        BlockPos blockPos = blockHit.getBlockPos();

                        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
                        Block block = blockState.getBlock();
                        MutableText mutableText = block.getName();

                        String text = mutableText.getString() + ", " + PositionUtils.getPositionDifference(blockPos);
                        MainClass.speakWithNarrator(text, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBlockAndFluidTargetPosition() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.cameraEntity == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            HitResult hit = minecraftClient.cameraEntity.raycast(20.0, 0.0f, true);
            if (hit == null)
                return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava()
                    && checkForFluidHit(minecraftClient, hit, true)) return;

            switch (hit.getType()) {
                case MISS, ENTITY ->
                        MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case BLOCK -> {
                    try {
                        BlockHitResult blockHitResult = (BlockHitResult) hit;
                        BlockPos blockPos = blockHitResult.getBlockPos();
                        MainClass.speakWithNarrator(PositionUtils.getPosition(blockPos), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkForFluidHit(MinecraftClient minecraftClient, HitResult fluidHit, boolean onlyPosition) {
        if (minecraftClient == null) return false;
        if (minecraftClient.world == null) return false;
        if (minecraftClient.currentScreen != null) return false;

        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) fluidHit).getBlockPos();
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);

            String name = ReadCrosshair.getFluidName(fluidState.getRegistryEntry());
            if (name.equals("block.minecraft.empty")) return false;
            if (name.contains("block.minecraft."))
                name = name.replace("block.minecraft.", ""); // Remove `block.minecraft.` for unsupported languages

            if (onlyPosition) {
                MainClass.speakWithNarrator(PositionUtils.getPosition(blockPos), true);
                return true;
            }

            int level = fluidState.getLevel();
            String levelString = "";
            if (level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString + ", " + PositionUtils.getPositionDifference(blockPos);

            MainClass.speakWithNarrator(toSpeak, true);
            return true;
        }
        return false;
    }

    public static void getLightLevel() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            int light = minecraftClient.world.getLightLevel(minecraftClient.player.getBlockPos());
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.light_level", light), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBiome() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            RegistryEntry<Biome> var27 = minecraftClient.world.getBiome(minecraftClient.player.getBlockPos());
            String name = I18n.translate(BiomeIndicator.getBiomeName(var27));
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.biome", name), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getXP() {
        try {
            if (minecraftClient.player == null) return;

            minecraftClient.player.closeScreen();

            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.xp", ClientPlayerEntityUtils.getExperienceLevel(), ClientPlayerEntityUtils.getExperienceProgress()), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
