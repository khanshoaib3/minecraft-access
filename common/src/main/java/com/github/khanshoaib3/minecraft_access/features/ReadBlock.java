package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.block.*;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.Direction;

/**
 * This feature reads the name of the targeted block or entity.<br>
 * It also gives feedback when a block is powered by a redstone signal or when a door is open similar cases.
 */
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
            if (minecraftClient.currentScreen != null) return;

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

        //TODO make this toggle-able and i18n
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

                    toSpeak = I18n.translate("minecraft_access.read_block.sign_content", toSpeak, contents);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean isEmittingPower = minecraftClient.world.isEmittingRedstonePower(hit.getBlockPos().toImmutable(), Direction.DOWN);
        boolean isReceivingPower = minecraftClient.world.isReceivingRedstonePower(hit.getBlockPos().toImmutable());

        if ((block instanceof RedstoneWireBlock || block instanceof PistonBlock || block instanceof GlowLichenBlock || block instanceof RedstoneLampBlock) && (isReceivingPower || isEmittingPower)) {
            toSpeak = I18n.translate("minecraft_access.read_block.powered", toSpeak);
            currentQuery += "powered";
        } else if ((block instanceof RedstoneTorchBlock || block instanceof LeverBlock || block instanceof AbstractButtonBlock) && isEmittingPower) {
            toSpeak = I18n.translate("minecraft_access.read_block.powered", toSpeak);
            currentQuery += "powered";
        } else if (block instanceof DoorBlock doorBlock && doorBlock.isOpen(blockState)) {
            toSpeak = I18n.translate("minecraft_access.read_block.opened", toSpeak);
            currentQuery += "open";
        } else if (block instanceof HopperBlock) {
            toSpeak = I18n.translate("minecraft_access.read_block.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_"+blockState.get(HopperBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(HopperBlock.FACING).getName();
            if(isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_block.locked", toSpeak);
                currentQuery += "locked";
            }
        } else if (block instanceof ObserverBlock) {
            toSpeak = I18n.translate("minecraft_access.read_block.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_"+blockState.get(ObserverBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(ObserverBlock.FACING).getName();
            if(isEmittingPower) {
                toSpeak = I18n.translate("minecraft_access.read_block.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if (block instanceof DispenserBlock) {
            toSpeak = I18n.translate("minecraft_access.read_block.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_"+blockState.get(DispenserBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(DispenserBlock.FACING).getName();
            if(isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_block.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if(isReceivingPower){ // For all the other blocks
            toSpeak = I18n.translate("minecraft_access.read_block.powered", toSpeak);
            currentQuery += "powered";
        }
        //TODO add for comparator and repeater


        if (!previousQuery.equalsIgnoreCase(currentQuery)) {
            previousQuery = currentQuery;
            MainClass.speakWithNarrator(toSpeak, true);
        }
    }
}
