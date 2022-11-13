package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
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

            HitResult hit = minecraftClient.crosshairTarget;
            if (hit == null) return;

            switch (hit.getType()) {
                case MISS -> {
                }
                case BLOCK -> {
                    checkForBlocks(minecraftClient, (BlockHitResult) hit);
                }
                case ENTITY -> {
                    checkForEntities((EntityHitResult) hit);
                }
            }
        } catch (Exception e) {
            MainClass.errorLog("Error occurred in read block feature.\n%s".formatted(e.getMessage()));
        }
    }

    private void checkForEntities(EntityHitResult hit) {
        //                    if (Config.get(ConfigKeys.ENTITY_NARRATOR_KEY.getKey())) {
        try {
//                            if (((EntityHitResult) hit).getEntity() == LockingHandler.lockedOnEntity)
//                                break;

            String currentQuery = hit.getEntity().getName().getString();
            if (!previousQuery.equalsIgnoreCase(currentQuery)) {

                previousQuery = currentQuery;
                MainClass.speakWithNarrator(currentQuery, true);
//                                modInit.mainThreadMap.put("entity_narrator_key", 5000);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//                    }
    }

    private void checkForBlocks(MinecraftClient minecraftClient, BlockHitResult hit) {
        if (minecraftClient.world == null) return;
        String toSpeak;
        BlockState blockState = minecraftClient.world.getBlockState(hit.getBlockPos());
        Block block = blockState.getBlock();

        String name = block.getName().getString();
        toSpeak = name;

//                            if (Config.get(ConfigKeys.NARRATE_BLOCK_SIDE_KEY.getKey())) {
        Direction side = hit.getSide();
        toSpeak += " " + side.asString(); //TODO use I18n here
//                            }

        // Class name in production environment can be different
        String blockPos = hit.getBlockPos().toImmutable().toString();
        String currentQuery = name + blockPos;


//                    String blockEntries = blockState.getEntries() + "" + blockState.getBlock() + "" + blockPos;
        if (blockState.isIn(BlockTags.SIGNS)) {
            String contents = "";
            try {
                SignBlockEntity signEntity = (SignBlockEntity) minecraftClient.world.getBlockEntity(hit.getBlockPos());
                if (signEntity != null) {
                    contents += signEntity.getTextOnRow(0, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(1, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(2, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(3, false).getString();
                }

//                            contents = I18n.translate("narrate.apextended.sign.says", contents); TODO I18n!!
                toSpeak += " says: " + contents;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

//                        if (!modInit.mainThreadMap.containsKey(currentQuery) && !blockEntries.equalsIgnoreCase(LockingHandler.lockedOnBlockEntries)) {
        if (!previousQuery.equalsIgnoreCase(currentQuery)) {

            previousQuery = currentQuery;

            MainClass.speakWithNarrator(toSpeak, true);
//                            modInit.mainThreadMap.put(currentQuery, 5000);
        }
    }
}
