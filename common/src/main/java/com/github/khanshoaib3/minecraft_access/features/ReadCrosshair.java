package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.block.*;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.fluid.Fluid;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.EntityHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;

/**
 * This feature reads the name of the targeted block or entity.<br>
 * It also gives feedback when a block is powered by a redstone signal or when a door is open similar cases.
 */
public class ReadCrosshair {
    private String previousQuery;

    public ReadCrosshair() {
        previousQuery = "";
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;
            Entity entity = minecraftClient.getCameraEntity();
            if(entity == null) return;

            HitResult blockHit = minecraftClient.crosshairTarget;
            HitResult fluidHit = entity.raycast(6.0, 0.0F, true);
            //TODO reset previousQuery after every 5000ms

            if (blockHit == null) return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava() && checkForFluidHit(minecraftClient, fluidHit)) return;

            checkForBlockAndEntityHit(minecraftClient, blockHit);
        } catch (Exception e) {
            MainClass.errorLog("Error occurred in read block feature.\n%s".formatted(e.getMessage()));
        }
    }

    private void checkForBlockAndEntityHit(MinecraftClient minecraftClient, HitResult blockHit) {
        switch (blockHit.getType()) {
            case MISS -> {
            }
            case BLOCK -> checkForBlocks(minecraftClient, (BlockHitResult) blockHit);
            case ENTITY -> checkForEntities((EntityHitResult) blockHit);
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
//        Direction side = hit.getSide();
//        toSpeak += " " + side.asString();

        // Class name in production environment can be different
        String blockPos = hit.getBlockPos().toImmutable().toString();
        String currentQuery = name + /*TODO side +*/ blockPos;

        if (blockState.isIn(BlockTags.SIGNS)) {
            String contents = "";
            try {
                SignBlockEntity signEntity = (SignBlockEntity) minecraftClient.world.getBlockEntity(hit.getBlockPos());
                if (signEntity != null) {
                    contents += signEntity.getTextOnRow(0, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(1, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(2, false).getString() + ", ";
                    contents += signEntity.getTextOnRow(3, false).getString();

                    toSpeak = I18n.translate("minecraft_access.read_crosshair.sign_content", toSpeak, contents);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        boolean isEmittingPower = minecraftClient.world.isEmittingRedstonePower(hit.getBlockPos().toImmutable(), Direction.DOWN);
        boolean isReceivingPower = minecraftClient.world.isReceivingRedstonePower(hit.getBlockPos().toImmutable());

        if ((block instanceof RedstoneWireBlock || block instanceof PistonBlock || block instanceof GlowLichenBlock || block instanceof RedstoneLampBlock) && (isReceivingPower || isEmittingPower)) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
            currentQuery += "powered";
//        } else if ((block instanceof RedstoneTorchBlock || block instanceof LeverBlock || block instanceof AbstractButtonBlock) && isEmittingPower) { // pre 1.19.3
        } else if ((block instanceof RedstoneTorchBlock || block instanceof LeverBlock || block instanceof ButtonBlock) && isEmittingPower) { // From 1.19.3
            toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
            currentQuery += "powered";
        } else if (block instanceof DoorBlock doorBlock && doorBlock.isOpen(blockState)) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.opened", toSpeak);
            currentQuery += "open";
        } else if (block instanceof HopperBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_"+blockState.get(HopperBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(HopperBlock.FACING).getName();
            if(isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.locked", toSpeak);
                currentQuery += "locked";
            }
        } else if (block instanceof ObserverBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_"+blockState.get(ObserverBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(ObserverBlock.FACING).getName();
            if(isEmittingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if (block instanceof DispenserBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_"+blockState.get(DispenserBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(DispenserBlock.FACING).getName();
            if(isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if(isReceivingPower){ // For all the other blocks
            toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
            currentQuery += "powered";
        }
        //TODO add for comparator and repeater


        if (!previousQuery.equalsIgnoreCase(currentQuery)) {
            previousQuery = currentQuery;
            MainClass.speakWithNarrator(toSpeak, true);
        }
    }

    private boolean checkForFluidHit(MinecraftClient minecraftClient, HitResult fluidHit) {
        if (minecraftClient == null) return false;
        if (minecraftClient.world == null) return false;
        if (minecraftClient.currentScreen != null) return false;

        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) fluidHit).getBlockPos();
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);

            String name = getFluidName(fluidState.getRegistryEntry());
            if(name.equals("block.minecraft.empty")) return false;
            if(name.contains("block.minecraft.")) name = name.replace("block.minecraft.", ""); // Remove `block.minecraft.` for unsupported languages

            String currentQuery = name + blockPos;

            int level = fluidState.getLevel();
            String levelString = "";
            if(level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString;

            if (!previousQuery.equalsIgnoreCase(currentQuery)) {
                previousQuery = currentQuery;
                MainClass.speakWithNarrator(toSpeak, true);
            }
            return true;
        }
        return false;
    }

    /**
     * Gets the fluid name from registry entry
     * @param fluid the fluid's registry entry
     * @return the fluid's name
     */
    public static String getFluidName(RegistryEntry<Fluid> fluid) {
        return I18n.translate(getFluidTranslationKey(fluid));
    }

    /**
     * Gets the fluid translation key from registry entry
     * @param fluid the fluid's registry entry
     * @return the fluid's translation key
     */
    private static String getFluidTranslationKey(RegistryEntry<Fluid> fluid) {
        return fluid.getKeyOrValue().map(
                (fluidKey) -> "block." + fluidKey.getValue().getNamespace() + "." + fluidKey.getValue().getPath(),
                (fluidValue) -> "[unregistered " + fluidValue + "]" // For unregistered fluid
        );
    }
}
