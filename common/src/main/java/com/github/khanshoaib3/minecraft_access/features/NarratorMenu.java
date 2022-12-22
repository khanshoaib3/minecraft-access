package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.gui.NarratorMenuGUI;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPosition;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;
import org.lwjgl.glfw.GLFW;

public class NarratorMenu {
    public final KeyBinding narratorMenuKey; //TODO create a separate class for initializing key binds
    private static MinecraftClient minecraftClient;

    public NarratorMenu() {
        narratorMenuKey = new KeyBinding(
                "minecraft_access.keys.other.narrator_menu_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_F4,
                "minecraft_access.keys.other.group_name"
        );
    }

    public void update() {
        try {
            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            boolean isNarratorMenuKeyPressed = InputUtil.isKeyPressed(
                    minecraftClient.getWindow().getHandle(),
                    InputUtil.fromTranslationKey(narratorMenuKey.getBoundKeyTranslationKey()).getCode()
            );

            if (isNarratorMenuKeyPressed) {
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

                        String text = mutableText.getString() + ", " + getPositionDifference(blockPos);
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
                        MainClass.speakWithNarrator(getPosition(blockPos), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getEntityTargetInformation() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.cameraEntity == null)return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            HitResult hit = minecraftClient.cameraEntity.raycast(20.0, 0.0f, false);
            if (hit == null)
                return;

            switch (hit.getType()) {
                case MISS, BLOCK ->
                        MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case ENTITY -> {
                    try {
                        EntityHitResult entityHitResult = (EntityHitResult) hit;
                        String name;
                        name = entityHitResult.getEntity().getName().getString();
                        BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
                        String text = name + ", " + getPositionDifference(blockPos);
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

    public static void getEntityTargetPosition() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.cameraEntity == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            HitResult hit = minecraftClient.cameraEntity.raycast(20.0, 0.0f, false);
            if (hit == null)
                return;

            switch (hit.getType()) {
                case MISS, BLOCK ->
                        MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case ENTITY -> {
                    try {
                        EntityHitResult entityHitResult = (EntityHitResult) hit;
                        BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
                        MainClass.speakWithNarrator(getPosition(blockPos), true);
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
                MainClass.speakWithNarrator(getPosition(blockPos), true);
                return true;
            }

            int level = fluidState.getLevel();
            String levelString = "";
            if (level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString + ", " + getPositionDifference(blockPos);

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

    public static String getPositionDifference(BlockPos blockPos) {
        if (minecraftClient.player == null) return "up";

        Direction dir = minecraftClient.player.getHorizontalFacing();

//        Vec3d diff = minecraftClient.player.getEyePos().subtract(Vec3d.ofCenter(blockPos)); // post 1.18
        Vec3d diff = new Vec3d(minecraftClient.player.getX(), minecraftClient.player.getEyeY(), minecraftClient.player.getZ()).subtract(Vec3d.ofCenter(blockPos)); // pre 1.18
        BlockPos diffBlockPos = new BlockPos(Math.round(diff.x), Math.round(diff.y), Math.round(diff.z));

        String diffXBlockPos = "";
        String diffYBlockPos = "";
        String diffZBlockPos = "";

        if (diffBlockPos.getX() != 0) {
            if (dir == Direction.NORTH) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "right", "left");
            } else if (dir == Direction.SOUTH) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "left", "right");
            } else if (dir == Direction.EAST) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "away", "behind");
            } else if (dir == Direction.WEST) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "behind", "away");
            }
        }

        if (diffBlockPos.getY() != 0) {
            diffYBlockPos = getDifferenceString(diffBlockPos.getY(), "up", "down");
        }

        if (diffBlockPos.getZ() != 0) {
            if (dir == Direction.SOUTH) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "away", "behind");
            } else if (dir == Direction.NORTH) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "behind", "away");
            } else if (dir == Direction.EAST) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "right", "left");
            } else if (dir == Direction.WEST) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "left", "right");
            }
        }

        String text;
        if (dir == Direction.NORTH || dir == Direction.SOUTH)
            text = String.format("%s  %s  %s", diffZBlockPos, diffYBlockPos, diffXBlockPos);
        else
            text = String.format("%s  %s  %s", diffXBlockPos, diffYBlockPos, diffZBlockPos);
        return text;
    }

    private static String getPosition(BlockPos blockPos) {
        try {
            String posX = PlayerPosition.getNarratableNumber(blockPos.getX());
            String posY = PlayerPosition.getNarratableNumber(blockPos.getY());
            String posZ = PlayerPosition.getNarratableNumber(blockPos.getZ());
            return String.format("%s x %s y %s z", posX, posY, posZ);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    private static String getDifferenceString(int blocks, String key1, String key2) {
        return I18n.translate("minecraft_access.util.position_difference_" + (blocks < 0 ? key1 : key2), Math.abs(blocks));
    }
}
