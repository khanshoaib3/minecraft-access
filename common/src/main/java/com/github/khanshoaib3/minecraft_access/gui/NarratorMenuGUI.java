package com.github.khanshoaib3.minecraft_access.gui;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.features.BiomeIndicator;
import com.github.khanshoaib3.minecraft_access.features.ReadCrosshair;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPosition;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Color;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.biome.Biome;

public class NarratorMenuGUI extends LightweightGuiDescription {
    private final ClientPlayerEntity player;
    public MinecraftClient minecraftClient;

    public NarratorMenuGUI(ClientPlayerEntity player, MinecraftClient client) {
        this.player = player;
        this.minecraftClient = client;

        WGridPanel rootGridPanel = new WGridPanel();

        setRootPanel(rootGridPanel);

        WButton blockAndFluidTargetInformationButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info")));
        blockAndFluidTargetInformationButton.setOnClick(this::getBlockAndFluidTargetInformation);
        rootGridPanel.add(blockAndFluidTargetInformationButton, 1, 1, 9, 1);

        WButton blockAndFluidTargetPositionButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position")));
        blockAndFluidTargetPositionButton.setOnClick(this::getBlockAndFluidTargetPosition);
        rootGridPanel.add(blockAndFluidTargetPositionButton, 11, 1, 9, 1);

        WButton entityTargetInformationButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.entity_target_info")));
        entityTargetInformationButton.setOnClick(this::getEntityTargetInformation);
        rootGridPanel.add(entityTargetInformationButton, 1, 3, 9, 1);

        WButton entityTargetPositionButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.entity_target_position")));
        entityTargetPositionButton.setOnClick(this::getEntityTargetPosition);
        rootGridPanel.add(entityTargetPositionButton, 11, 3, 9, 1);

        WButton lightLevelButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.light_level")));
        lightLevelButton.setOnClick(this::getLightLevel);
        rootGridPanel.add(lightLevelButton, 1, 5, 9, 1);

        WButton biomeButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.biome")));
        biomeButton.setOnClick(this::getBiome);
        rootGridPanel.add(biomeButton, 11, 5, 9, 1);

        WLabel labelForPadding = new WLabel(Text.of(""), Color.RED.toRgb());
        rootGridPanel.add(labelForPadding, 0, 6, 21, 1);

        rootGridPanel.validate(this);

        // TODO add time of day and map number buttons to trigger above buttons
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Color.LIGHT_GRAY_DYE.toRgb()));
    }

    private void getBlockAndFluidTargetInformation() {
        try {
            this.player.closeScreen();
            if(this.minecraftClient.cameraEntity == null) return;
            if(this.minecraftClient.player == null) return;
            if(this.minecraftClient.world == null) return;

            HitResult hit = this.minecraftClient.cameraEntity.raycast(20.0, 0.0f, true);
            if (hit == null)
                return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava()
                    && checkForFluidHit(minecraftClient, hit, false)) return;

            switch (hit.getType()) {
                case MISS, ENTITY -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
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

    private void getBlockAndFluidTargetPosition() {
        try {
            this.player.closeScreen();
            if(this.minecraftClient.cameraEntity == null) return;
            if(this.minecraftClient.player == null) return;
            if(this.minecraftClient.world == null) return;

            HitResult hit = this.minecraftClient.cameraEntity.raycast(20.0, 0.0f, true);
            if (hit == null)
                return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava()
                    && checkForFluidHit(minecraftClient, hit, true)) return;

            switch (hit.getType()) {
                case MISS, ENTITY -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
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

    private void getEntityTargetInformation() {
        try {
            this.player.closeScreen();
            if(this.minecraftClient.cameraEntity == null) return;
            if(this.minecraftClient.player == null) return;
            if(this.minecraftClient.world == null) return;

            HitResult hit = this.minecraftClient.cameraEntity.raycast(20.0, 0.0f, false);
            if (hit == null)
                return;

            switch (hit.getType()) {
                case MISS, BLOCK -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
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

    private void getEntityTargetPosition() {
        try {
            this.player.closeScreen();
            if(this.minecraftClient.cameraEntity == null) return;
            if(this.minecraftClient.player == null) return;
            if(this.minecraftClient.world == null) return;

            HitResult hit = this.minecraftClient.cameraEntity.raycast(20.0, 0.0f, false);
            if (hit == null)
                return;

            switch (hit.getType()) {
                case MISS, BLOCK -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
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

    private boolean checkForFluidHit(MinecraftClient minecraftClient, HitResult fluidHit, boolean onlyPosition) {
        if (minecraftClient == null) return false;
        if (minecraftClient.world == null) return false;
        if (minecraftClient.currentScreen != null) return false;

        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) fluidHit).getBlockPos();
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);

            String name = ReadCrosshair.getFluidName(fluidState.getRegistryEntry());
            if(name.equals("block.minecraft.empty")) return false;
            if(name.contains("block.minecraft.")) name = name.replace("block.minecraft.", ""); // Remove `block.minecraft.` for unsupported languages

            if(onlyPosition) {
                MainClass.speakWithNarrator(getPosition(blockPos), true);
                return true;
            }

            int level = fluidState.getLevel();
            String levelString = "";
            if(level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString + ", " + getPositionDifference(blockPos);

            MainClass.speakWithNarrator(toSpeak, true);
            return true;
        }
        return false;
    }

    private void getLightLevel() {
        try {
            this.player.closeScreen();
            if(this.minecraftClient.world == null) return;

            int light = this.minecraftClient.world.getLightLevel(this.player.getBlockPos());
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.light_level", light), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBiome() {
        try {
            this.player.closeScreen();
            if(minecraftClient.world == null) return;

            RegistryEntry<Biome> var27 = this.minecraftClient.world.getBiome(this.player.getBlockPos());
            String name = I18n.translate(BiomeIndicator.getBiomeName(var27));
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.biome", name), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getPositionDifference(BlockPos blockPos) {
        ClientPlayerEntity player = minecraftClient.player;
        if(this.minecraftClient.player == null) return "up";

        Direction dir = minecraftClient.player.getHorizontalFacing();

//        Vec3d diff = player.getEyePos().subtract(Vec3d.ofCenter(blockPos)); // post 1.18
        Vec3d diff = new Vec3d(player.getX(), player.getEyeY(), player.getZ()).subtract(Vec3d.ofCenter(blockPos)); // pre 1.18
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

    private String getPosition(BlockPos blockPos) {
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