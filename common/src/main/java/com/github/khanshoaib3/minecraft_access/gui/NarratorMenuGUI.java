package com.github.khanshoaib3.minecraft_access.gui;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.features.BiomeIndicator;
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
    public MinecraftClient client;

    public NarratorMenuGUI(ClientPlayerEntity player, MinecraftClient client) {
        this.player = player;
        this.client = client;

        WGridPanel rootGridPanel = new WGridPanel();

        setRootPanel(rootGridPanel);

        WButton wb11 = new WButton(Text.of(I18n.translate("minecraft_access.gui.button.target_info")));
        wb11.setOnClick(this::block_target_information);
        rootGridPanel.add(wb11, 1, 1, 7, 1);

        WButton wb12 = new WButton(Text.of(I18n.translate("minecraft_access.gui.button.target_position")));
        wb12.setOnClick(this::block_target_position);
        rootGridPanel.add(wb12, 9, 1, 7, 1);

        WButton wb22 = new WButton(Text.of(I18n.translate("minecraft_access.gui.button.light_level")));
        wb22.setOnClick(this::light_level);
        rootGridPanel.add(wb22, 1, 3, 7, 1);

        WButton wb23 = new WButton(Text.of(I18n.translate("minecraft_access.gui.button.biome")));
        wb23.setOnClick(this::getBiome);
        rootGridPanel.add(wb23, 9, 3, 7, 1);

        WLabel labelForPadding = new WLabel(Text.of(""), Color.RED.toRgb());
        rootGridPanel.add(labelForPadding, 0, 4, 17, 1);

        rootGridPanel.validate(this);

//        WButton wb31 = new WButton(Text.of("Time Of Day"));
//        wb31.setOnClick(this::getTimeOfDay);
//        rootGridPanel.add(wb31, 4, 4, 7, 1);
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Color.LIGHT_GRAY_DYE.toRgb()));
    }

    private void block_target_information() {
        try {
            this.player.closeScreen();
            if(this.client.cameraEntity == null) return;


            HitResult hit = this.client.cameraEntity.raycast(20.0, 0.0f, false);
            if (hit == null)
                return;

            switch (hit.getType()) {
                case MISS -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case BLOCK -> {
                    try {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        BlockPos blockPos = blockHit.getBlockPos();
                        assert client.world != null;
                        BlockState blockState = client.world.getBlockState(blockPos);
                        Block block = blockState.getBlock();
                        MutableText mutableText = block.getName();
                        String text = mutableText.getString() + ", " + get_position_difference(blockPos);
                        MainClass.speakWithNarrator(text, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case ENTITY -> {
                    try {
                        EntityHitResult entityHitResult = (EntityHitResult) hit;
                        String name;
                        name = entityHitResult.getEntity().getName().getString();
                        BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
                        String text = name + ", " + get_position_difference(blockPos);
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

    private void block_target_position() {
        try {
            this.player.closeScreen();
            if(this.client.cameraEntity == null) return;

            HitResult hit = this.client.cameraEntity.raycast(20.0, 0.0f, false);
            if (hit == null)
                return;

            switch (hit.getType()) {
                case MISS -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case BLOCK -> {
                    try {
                        BlockHitResult blockHitResult = (BlockHitResult) hit;
                        BlockPos blockPos = blockHitResult.getBlockPos();
                        MainClass.speakWithNarrator(get_position(blockPos), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                case ENTITY -> {
                    try {
                        EntityHitResult entityHitResult = (EntityHitResult) hit;
                        BlockPos blockPos = entityHitResult.getEntity().getBlockPos();
                        MainClass.speakWithNarrator(get_position(blockPos), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void light_level() {
        try {
            this.player.closeScreen();
            if(this.client.world == null) return;

            int light = this.client.world.getLightLevel(this.player.getBlockPos());
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.light_level", light), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void getBiome() {
        try {
            this.player.closeScreen();
            if(client.world == null) return;

            RegistryEntry<Biome> var27 = this.client.world.getBiome(this.player.getBlockPos());
            String name = I18n.translate(BiomeIndicator.getBiomeName(var27));
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.biome", name), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String get_position_difference(BlockPos blockPos) {
        ClientPlayerEntity player = client.player;
        if(this.client.player == null) return "up";

        Direction dir = client.player.getHorizontalFacing();

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

    private String get_position(BlockPos blockPos) {
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