package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.feature_config_maps.ReadCrosshairConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.PlayerPositionUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.*;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.AnimalEntity;
import net.minecraft.entity.passive.SheepEntity;
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
    private boolean speakSide;
    private boolean speakingConsecutiveBlocks;
    /**
     * previousQuery should be expired after given time in nanosecond.
     */
    private Long repeatSpeakingInterval;
    private Long previousQuerySetTime;

    public ReadCrosshair() {
        previousQuery = "";
        previousQuerySetTime = 0L;
        loadConfigurations();
    }

    public String getPreviousQuery() {
        boolean expired = System.nanoTime() - this.previousQuerySetTime > this.repeatSpeakingInterval;
        if (expired) {
            this.previousQuery = "";
        }
        return this.previousQuery;
    }

    public void setPreviousQuery(String previousQuery) {
        this.previousQuerySetTime = System.nanoTime();
        this.previousQuery = previousQuery;
    }

    public void update() {
        try {
            MinecraftClient minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.world == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return;

            loadConfigurations();

            Entity entity = minecraftClient.getCameraEntity();
            if (entity == null) return;

            HitResult blockHit = minecraftClient.crosshairTarget;
            HitResult fluidHit = entity.raycast(6.0, 0.0F, true);

            if (blockHit == null) return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava() && checkForFluidHit(minecraftClient, fluidHit))
                return;

            checkForBlockAndEntityHit(minecraftClient, blockHit);
        } catch (Exception e) {
            MainClass.errorLog("Error occurred in read block feature.\n%s".formatted(e.getMessage()));
        }
    }

    private void loadConfigurations() {
        ReadCrosshairConfigMap map = MainClass.config.getConfigMap().getReadCrosshairConfigMap();
        this.speakSide = map.isSpeakSide();
        // affirmation for easier use
        this.speakingConsecutiveBlocks = !map.isDisableSpeakingConsecutiveBlocks();
        // 1 milliseconds = 1*10^6 nanoseconds
        this.repeatSpeakingInterval = map.getRepeatSpeakingInterval() * 1000_000;
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
            if (hit.getEntity() instanceof AnimalEntity animalEntity) {
                if (animalEntity instanceof SheepEntity sheepEntity)
                    currentQuery = "%s %s".formatted(sheepEntity.getColor().getName(), currentQuery);
                if (animalEntity.isBaby())
                    currentQuery = I18n.translate("minecraft_access.read_crosshair.animal_entity_baby", currentQuery);
                if (animalEntity.isLeashed())
                    currentQuery = I18n.translate("minecraft_access.read_crosshair.animal_entity_leashed", currentQuery);
            }

            speakIfFocusChanged(currentQuery, currentQuery);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * @param currentQuery for checking if focus is changed
     * @param toSpeak      text will be narrated (if focus has changed)
     */
    private void speakIfFocusChanged(String currentQuery, String toSpeak) {
        boolean focusChanged = !getPreviousQuery().equalsIgnoreCase(currentQuery);
        if (focusChanged) {
            setPreviousQuery(currentQuery);
            MainClass.speakWithNarrator(toSpeak, true);
        }
    }

    private void checkForBlocks(MinecraftClient minecraftClient, BlockHitResult hit) {
        if (minecraftClient.world == null) return;
        BlockState blockState = minecraftClient.world.getBlockState(hit.getBlockPos());
        Block block = blockState.getBlock();

        String name = block.getName().getString();
        String toSpeak = name;

        String side = "";
        if (this.speakSide) {
            Direction d = hit.getSide();
            String prefix;
            if (Direction.UP.equals(d) || Direction.DOWN.equals(d)) {
                prefix = "minecraft_access.direction.vertical_angle_";
            } else {
                prefix = "minecraft_access.direction.horizontal_angle_";
            }
            side = I18n.translate(prefix + d.getName());
        }
        toSpeak += " " + side;

        // Class name in production environment can be different
        String blockPos = hit.getBlockPos().toImmutable().toString();
        String currentQuery = name + side;

        if (this.speakingConsecutiveBlocks) currentQuery += " " + blockPos;

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

        if (minecraftClient.world.getBlockEntity(hit.getBlockPos(), BlockEntityType.BEEHIVE).isPresent()) {
            try {
                BeehiveBlockEntity beehiveBlockEntity = minecraftClient.world.getBlockEntity(hit.getBlockPos(), BlockEntityType.BEEHIVE).get();
                boolean isSmoked = beehiveBlockEntity.isSmoked();
                int honeyLevel = blockState.get(BeehiveBlock.HONEY_LEVEL);
                Direction facingDirection = blockState.get(BeehiveBlock.FACING);

                if (isSmoked)
                    toSpeak = I18n.translate("minecraft_access.read_crosshair.bee_hive_smoked", toSpeak);
                if (honeyLevel > 0)
                    toSpeak = I18n.translate("minecraft_access.read_crosshair.bee_hive_honey_level", toSpeak, honeyLevel);
                toSpeak = I18n.translate("minecraft_access.read_crosshair.bee_hive_facing", toSpeak, facingDirection.getName());
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
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_" + blockState.get(HopperBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(HopperBlock.FACING).getName();
            if (isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.locked", toSpeak);
                currentQuery += "locked";
            }
        } else if (block instanceof ObserverBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_" + blockState.get(ObserverBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(ObserverBlock.FACING).getName();
            if (isEmittingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if (block instanceof DispenserBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction.horizontal_angle_" + blockState.get(DispenserBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(DispenserBlock.FACING).getName();
            if (isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if (block instanceof ComparatorBlock) {
            BlockEntity blockEntity = minecraftClient.world.getBlockEntity(hit.getBlockPos());
            int outputPower = blockEntity instanceof ComparatorBlockEntity ? ((ComparatorBlockEntity) blockEntity).getOutputSignal() : 0;
            ComparatorMode mode = blockState.get(ComparatorBlock.MODE);
            Direction facing = blockState.get(ComparatorBlock.FACING);
            String correctFacing = I18n.translate("minecraft_access.direction.horizontal_angle_" + PlayerPositionUtils.getOppositeDirectionKey(facing.getName()).toLowerCase());

            toSpeak = I18n.translate("minecraft_access.read_crosshair.comparator_info", toSpeak, correctFacing, mode, outputPower);
            currentQuery += "mode:" + mode + " output_power:" + outputPower + " facing:" + correctFacing;
        } else if (block instanceof RepeaterBlock) {
            boolean locked = blockState.get(RepeaterBlock.LOCKED);
            int delay = blockState.get(RepeaterBlock.DELAY);
            Direction facing = blockState.get(ComparatorBlock.FACING);
            String correctFacing = I18n.translate("minecraft_access.direction.horizontal_angle_" + PlayerPositionUtils.getOppositeDirectionKey(facing.getName()).toLowerCase());

            toSpeak = I18n.translate("minecraft_access.read_crosshair.repeater_info", toSpeak, correctFacing, delay);
            currentQuery += "delay:" + delay + " facing:" + correctFacing;
            if (locked) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.locked", toSpeak);
                currentQuery += "locked";
            }
        } else if (isReceivingPower) { // For all the other blocks
            toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
            currentQuery += "powered";
        }

        speakIfFocusChanged(currentQuery, toSpeak);
    }

    private boolean checkForFluidHit(MinecraftClient minecraftClient, HitResult fluidHit) {
        if (minecraftClient == null) return false;
        if (minecraftClient.world == null) return false;
        if (minecraftClient.currentScreen != null) return false;

        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) fluidHit).getBlockPos();
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);

            String name = getFluidName(fluidState.getRegistryEntry());
            if (name.equals("block.minecraft.empty")) return false;
            if (name.contains("block.minecraft."))
                name = name.replace("block.minecraft.", ""); // Remove `block.minecraft.` for unsupported languages

            String currentQuery = name + blockPos;

            int level = fluidState.getLevel();
            String levelString = "";
            if (level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString;

            speakIfFocusChanged(currentQuery, toSpeak);
            return true;
        }
        return false;
    }

    /**
     * Gets the fluid name from registry entry
     *
     * @param fluid the fluid's registry entry
     * @return the fluid's name
     */
    public static String getFluidName(RegistryEntry<Fluid> fluid) {
        return I18n.translate(getFluidTranslationKey(fluid));
    }

    /**
     * Gets the fluid translation key from registry entry
     *
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
