package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.block.*;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

public class ReadBlock {
    private String previousQuery;

    public ReadBlock() {
        previousQuery = "";
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.world == null) return;

            //TODO reset previousQuery after every 5000ms

            HitResult hit = minecraftClient.crosshairTarget;
            if (hit == null) return;

            switch (hit.getType()) {
                case MISS -> {
                }
                case BLOCK -> checkForBlocks(minecraftClient, (BlockHitResult) hit);
                case ENTITY -> checkForEntities((EntityHitResult) hit);
            }
        } catch (Exception e) {
            MainClass.errorLog("Error occurred in read block feature.\n%s".formatted(e.getMessage()));
        }
    }

    private void checkForEntities(EntityHitResult hit) {
        try {
            String currentQuery = hit.getEntity().getName().getString();
            if (!previousQuery.equalsIgnoreCase(currentQuery)) {

                previousQuery = currentQuery;
                MainClass.speakWithNarrator(currentQuery, true);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void checkForBlocks(MinecraftClient minecraftClient, BlockHitResult hit) {
        if (minecraftClient.world == null) return;
        BlockState blockState = minecraftClient.world.getBlockState(hit.getBlockPos().toImmutable());
        Block block = blockState.getBlock();

        String name = block.getName().getString();
        String toSpeak = name;

        //TODO make this toggle-able
        Direction side = hit.getSide();
        toSpeak += " " + side.asString();

        // Class name in production environment can be different
        String blockPos = hit.getBlockPos().toImmutable().toString();
        String currentQuery = name + side + blockPos;

        if (blockState.isIn(BlockTags.SIGNS)) {
            String contents = "";
            try {
                SignBlockEntity signEntity = (SignBlockEntity) minecraftClient.world.getBlockEntity(hit.getBlockPos());
                if (signEntity != null) {
                    contents += signEntity.getTextOnRow(0, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(1, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(2, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(3, false).getString();

                    toSpeak += " says: " + contents; //TODO use i18n here
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean isEmittingPower = minecraftClient.world.isEmittingRedstonePower(hit.getBlockPos().toImmutable(), Direction.DOWN);
        boolean isReceivingPower = minecraftClient.world.isReceivingRedstonePower(hit.getBlockPos().toImmutable());

        if ((block instanceof RedstoneWireBlock || block instanceof PistonBlock || block instanceof GlowLichenBlock || block instanceof RedstoneLampBlock) && (isReceivingPower || isEmittingPower)) {
            toSpeak = "Powered " + toSpeak; //TODO I18n
            currentQuery += "powered";
        } else if ((block instanceof RedstoneTorchBlock || block instanceof LeverBlock || block instanceof AbstractButtonBlock) && isEmittingPower) {
            toSpeak = "Powered " + toSpeak; //TODO I18n
            currentQuery += "powered";
        } else if (block instanceof DoorBlock doorBlock && doorBlock.isOpen(blockState)) {
            toSpeak = "Opened " + toSpeak; //TODO I18n
            currentQuery += "open";
        } else if (block instanceof HopperBlock hopperBlock) {
            toSpeak += " Facing " + blockState.get(HopperBlock.FACING).getName(); //TODO I18n (use directions too)
            currentQuery += "facing " + blockState.get(HopperBlock.FACING).getName();
        }


        if (!previousQuery.equalsIgnoreCase(currentQuery)) {
            previousQuery = currentQuery;
            MainClass.speakWithNarrator(toSpeak, true);
        }
    }
}
