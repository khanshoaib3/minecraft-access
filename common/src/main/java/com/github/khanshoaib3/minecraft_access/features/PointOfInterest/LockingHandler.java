package com.github.khanshoaib3.minecraft_access.features.PointOfInterest;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import org.lwjgl.glfw.GLFW;

import java.util.Map.Entry;
import java.util.Timer;
import java.util.TimerTask;

public class LockingHandler {
    public static Entity lockedOnEntity = null;
    public static Vec3d lockedOnBlock = null;
    public static Vec3d prevEntityPos = null;
    public static boolean isLockedOntoLadder = false;
    public static boolean isLockedOntoEyeOfEnderTarget = false;
    public static String lockedOnBlockEntries = "";

    public final KeyBinding lockingHandlerKey; //TODO create a separate class for initializing key binds
    private boolean shouldRun = true;

    public LockingHandler() {
        lockingHandlerKey = new KeyBinding(
                "minecraft_access.keys.other.locking_handler_key_name",
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_Y,
                "minecraft_access.keys.other.group_name"
        );
    }

    public void update() {
        if (!this.shouldRun) return;
        try {
            mainLogic();

            // Pause the execution of this feature for 100 milliseconds
            // TODO Remove Timer
            shouldRun = false;
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    shouldRun = true;
                }
            };
            new Timer().schedule(timerTask, 100);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void mainLogic() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();

        if (minecraftClient == null) return;
        if (minecraftClient.player == null) return;
        if (minecraftClient.world == null) return;
        if (minecraftClient.currentScreen != null) return;

        boolean isLockingKeyPressed = InputUtil.isKeyPressed(
                minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey(lockingHandlerKey.getBoundKeyTranslationKey()).getCode()
        );

        if (lockedOnEntity != null) outer:{
            if (!lockedOnEntity.isAlive()) {
                lockedOnEntity = null;
                playUnlockingSound(minecraftClient);
                break outer;
            }

            double posX = lockedOnEntity.getX() - 0.5;
            double posY = lockedOnEntity.getY() - 0.5;
            double posZ = lockedOnEntity.getZ() - 0.5;
            if (lockedOnEntity instanceof EyeOfEnderEntity)
                prevEntityPos = new Vec3d(posX, posY, posZ);

            Vec3d vec3d = new Vec3d(lockedOnEntity.getX(),
                    lockedOnEntity.getY() + lockedOnEntity.getHeight() - 0.25, lockedOnEntity.getZ());
            minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, vec3d);
        }
        else {
            if (prevEntityPos != null) {
                lockedOnBlock = prevEntityPos;
                isLockedOntoEyeOfEnderTarget = true;
                prevEntityPos = null;
            }
        }

        if (isLockedOntoLadder) {
            Vec3d playerPos = minecraftClient.player.getPos();
            double distance = lockedOnBlock.distanceTo(playerPos);
            if (distance <= 0.5) {
                lockedOnBlock = null;
                isLockedOntoLadder = false;
                playUnlockingSound(minecraftClient);
            }
        }

        if (lockedOnBlock != null) {
            BlockState blockState = minecraftClient.world.getBlockState(new BlockPos(lockedOnBlock));
            String entries = blockState.getEntries() + "" + blockState.getBlock() + "" + (new BlockPos(lockedOnBlock));
            if (entries.equalsIgnoreCase(lockedOnBlockEntries) || isLockedOntoEyeOfEnderTarget)
                minecraftClient.player.lookAt(EntityAnchorArgumentType.EntityAnchor.EYES, lockedOnBlock);
            else {
                lockedOnBlockEntries = "";
                isLockedOntoLadder = false;
                lockedOnBlock = null;
                playUnlockingSound(minecraftClient);
            }
        }

        if (!isLockingKeyPressed) return;

        if (Screen.hasAltDown()) {
            if (lockedOnEntity != null || lockedOnBlock != null) {
                MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.unlocked"), true);
                lockedOnEntity = null;
                lockedOnBlockEntries = "";
                lockedOnBlock = null;
                isLockedOntoLadder = false;
                isLockedOntoEyeOfEnderTarget = false;
                playUnlockingSound(minecraftClient);
            }
            return;
        }

        if (!POIEntities.eyeOfEnderEntity.isEmpty()) {
            Entry<Double, Entity> entry = POIEntities.eyeOfEnderEntity.firstEntry();
            Entity entity = entry.getValue();

            String text = I18n.translate("minecraft_access.point_of_interest.locking.tracking_eye_of_ender");
            lockedOnEntity = entity;
            lockedOnBlockEntries = "";

            lockedOnBlock = null;
            isLockedOntoLadder = false;

            MainClass.speakWithNarrator(text, true);
            return;
        }

        if (!POIEntities.hostileEntity.isEmpty()) {
            Entry<Double, Entity> entry = POIEntities.hostileEntity.firstEntry();
            Entity entity = entry.getValue();

            String text = entity.getName().getString();
            lockedOnEntity = entity;
            lockedOnBlockEntries = "";

            lockedOnBlock = null;
            isLockedOntoLadder = false;

//  FIXME                  if (Config.get(ConfigKeys.POI_ENTITY_LOCKING_NARRATE_DISTANCE_KEY.getKey())) {
//                        text += " " + HudRenderCallBackClass.get_position_difference(entity.getBlockPos(), minecraftClient);
//                    }
            MainClass.speakWithNarrator(text, true);
            return;
        }

        if (!POIEntities.passiveEntity.isEmpty()) {
            Entry<Double, Entity> entry = POIEntities.passiveEntity.firstEntry();
            Entity entity = entry.getValue();

            String text = entity.getName().getString();
            lockedOnEntity = entity;
            lockedOnBlockEntries = "";

            lockedOnBlock = null;
            isLockedOntoLadder = false;

//  FIXME                  if (Config.get(ConfigKeys.POI_ENTITY_LOCKING_NARRATE_DISTANCE_KEY.getKey())) {
//                        text += " " + HudRenderCallBackClass.get_position_difference(entity.getBlockPos(), minecraftClient);
//                    }
            MainClass.speakWithNarrator(text, true);
            return;
        }

        Double closest = -9999.0;

        Entry<Double, Vec3d> closestDoorBlockEntry = null;
        Double closestDoorBlockDouble = -9999.0;
        if (!POIBlocks.doorBlocks.isEmpty()) {
            closestDoorBlockEntry = POIBlocks.doorBlocks.firstEntry();
            closestDoorBlockDouble = closestDoorBlockEntry.getKey();
            closest = closestDoorBlockDouble;
        }

        Entry<Double, Vec3d> closestButtonBlockEntry = null;
        Double closestButtonBlockDouble = -9999.0;
        if (!POIBlocks.buttonBlocks.isEmpty()) {
            closestButtonBlockEntry = POIBlocks.buttonBlocks.firstEntry();
            closestButtonBlockDouble = closestButtonBlockEntry.getKey();
            closest = closestButtonBlockDouble;
        }

        Entry<Double, Vec3d> closestLadderBlockEntry = null;
        Double closestLadderBlockDouble = -9999.0;
        if (!POIBlocks.ladderBlocks.isEmpty()) {
            closestLadderBlockEntry = POIBlocks.ladderBlocks.firstEntry();
            closestLadderBlockDouble = closestLadderBlockEntry.getKey();
            closest = closestLadderBlockDouble;
        }

        Entry<Double, Vec3d> closestLeverBlockEntry = null;
        Double closestLeverBlockDouble = -9999.0;
        if (!POIBlocks.leverBlocks.isEmpty()) {
            closestLeverBlockEntry = POIBlocks.leverBlocks.firstEntry();
            closestLeverBlockDouble = closestLeverBlockEntry.getKey();
            closest = closestLeverBlockDouble;
        }

        Entry<Double, Vec3d> closestTrapDoorBlockEntry = null;
        Double closestTrapDoorBlockDouble = -9999.0;
        if (!POIBlocks.trapDoorBlocks.isEmpty()) {
            closestTrapDoorBlockEntry = POIBlocks.trapDoorBlocks.firstEntry();
            closestTrapDoorBlockDouble = closestTrapDoorBlockEntry.getKey();
            closest = closestTrapDoorBlockDouble;
        }

        Entry<Double, Vec3d> closestFluidBlockEntry = null;
        Double closestFluidBlockDouble = -9999.0;
        if (!POIBlocks.fluidBlocks.isEmpty()) {
            closestFluidBlockEntry = POIBlocks.fluidBlocks.firstEntry();
            closestFluidBlockDouble = closestFluidBlockEntry.getKey();
            closest = closestFluidBlockDouble;
        }

        Entry<Double, Vec3d> closestOtherBlockEntry = null;
        Double closestOtherBlockDouble = -9999.0;
        if (!POIBlocks.otherBlocks.isEmpty()) {
            closestOtherBlockEntry = POIBlocks.otherBlocks.firstEntry();
            closestOtherBlockDouble = closestOtherBlockEntry.getKey();
            closest = closestOtherBlockDouble;
        }

        Entry<Double, Vec3d> closestOreBlockEntry = null;
        Double closestOreBlockDouble = -9999.0;
        if (!POIBlocks.oreBlocks.isEmpty()) {
            closestOreBlockEntry = POIBlocks.oreBlocks.firstEntry();
            closestOreBlockDouble = closestOreBlockEntry.getKey();
            closest = closestOreBlockDouble;
        }

        if (closest != -9999.0) {
            if (closestDoorBlockDouble != -9999.0)
                closest = Math.min(closest, closestDoorBlockDouble);
            if (closestButtonBlockDouble != -9999.0)
                closest = Math.min(closest, closestButtonBlockDouble);
            if (closestLadderBlockDouble != -9999.0)
                closest = Math.min(closest, closestLadderBlockDouble);
            if (closestLeverBlockDouble != -9999.0)
                closest = Math.min(closest, closestLeverBlockDouble);
            if (closestTrapDoorBlockDouble != -9999.0)
                closest = Math.min(closest, closestTrapDoorBlockDouble);
            if (closestOreBlockDouble != -9999.0)
                closest = Math.min(closest, closestOreBlockDouble);
            if (closestOtherBlockDouble != -9999.0)
                closest = Math.min(closest, closestOtherBlockDouble);

            lockOntoBlocksOrPassiveEntity(minecraftClient, closest, closestDoorBlockEntry,
                    closestDoorBlockDouble, closestButtonBlockEntry, closestButtonBlockDouble,
                    closestLadderBlockEntry, closestLadderBlockDouble, closestLeverBlockEntry,
                    closestLeverBlockDouble, closestTrapDoorBlockEntry, closestTrapDoorBlockDouble,
                    closestFluidBlockEntry, closestFluidBlockDouble, closestOtherBlockEntry,
                    closestOtherBlockDouble, closestOreBlockEntry, closestOreBlockDouble);

            narrateBlockPosAndSetBlockEntries(minecraftClient);
        }
    }

    private void lockOntoBlocksOrPassiveEntity(MinecraftClient client, Double closest,
                                               Entry<Double, Vec3d> closestDoorBlockEntry, Double closestDoorBlockDouble,
                                               Entry<Double, Vec3d> closestButtonBlockEntry, Double closestButtonBlockDouble,
                                               Entry<Double, Vec3d> closestLadderBlockEntry, Double closestLadderBlockDouble,
                                               Entry<Double, Vec3d> closestLeverBlockEntry, Double closestLeverBlockDouble,
                                               Entry<Double, Vec3d> closestTrapDoorBlockEntry, Double closestTrapDoorBlockDouble,
                                               Entry<Double, Vec3d> closestFluidBlockEntry, Double closestFluidBlockDouble,
                                               Entry<Double, Vec3d> closestOtherBlockEntry, Double closestOtherBlockDouble,
                                               Entry<Double, Vec3d> closestOreBlockEntry, Double closestOreBlockDouble) {

        if (client.player == null) return;

        if (closest.equals(closestOreBlockDouble) && closestOreBlockDouble != -9999.0) {
            lockedOnBlock = closestOreBlockEntry.getValue();
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        } else if (closest.equals(closestDoorBlockDouble) && closestDoorBlockDouble != -9999.0) {
            lockedOnBlock = AbsolutePositions.getDoorAbsolutePosition(client, closestDoorBlockEntry.getValue());
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        } else if (closest.equals(closestButtonBlockDouble) && closestButtonBlockDouble != -9999.0) {
            lockedOnBlock = AbsolutePositions.getButtonsAbsolutePosition(client, closestButtonBlockEntry.getValue());
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        } else if (closest.equals(closestLadderBlockDouble) && closestLadderBlockDouble != -9999.0) {
            Vec3d absolutePos = AbsolutePositions.getLaddersAbsolutePosition(client, closestLadderBlockEntry.getValue());
            isLockedOntoLadder = true;
            lockedOnBlock = absolutePos;
            lockedOnEntity = null;
        } else if (closest.equals(closestLeverBlockDouble) && closestLeverBlockDouble != -9999.0) {
            lockedOnBlock = AbsolutePositions.getLeversAbsolutePosition(client, closestLeverBlockEntry.getValue());
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        } else if (closest.equals(closestTrapDoorBlockDouble) && closestTrapDoorBlockDouble != -9999.0) {
            lockedOnBlock = AbsolutePositions.getTrapDoorAbsolutePosition(client, closestTrapDoorBlockEntry.getValue());
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        } else if (closest.equals(closestFluidBlockDouble) && closestFluidBlockDouble != -9999.0
                && !client.player.isSwimming() && !client.player.isSubmergedInWater() && !client.player.isInsideWaterOrBubbleColumn() && !client.player.isInLava()) {
            lockedOnBlock = closestFluidBlockEntry.getValue();
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        } else if (closest.equals(closestOtherBlockDouble) && closestOtherBlockDouble != -9999.0) {
            lockedOnBlock = closestOtherBlockEntry.getValue();
            lockedOnEntity = null;
            isLockedOntoLadder = false;
        }
    }

    private void narrateBlockPosAndSetBlockEntries(MinecraftClient client) {
        if (client.world == null) return;
        if (lockedOnBlock == null) return;

        BlockState blockState = client.world.getBlockState(new BlockPos(lockedOnBlock));
        lockedOnBlockEntries = blockState.getEntries() + "" + blockState.getBlock() + ""
                + (new BlockPos(lockedOnBlock));

        Block closestBlock = blockState.getBlock();

        MutableText mutableText = (Text.literal("")).append(closestBlock.getName()); // post 1.19
//            MutableText mutableText = (new net.minecraft.text.LiteralText("")).append(closestBlock.getName()); // pre 1.19
        String text = mutableText.getString();

//   FIXME         if (Config.get(ConfigKeys.POI_BLOCKS_LOCKING_NARRATE_DISTANCE_KEY.getKey())) {
//                text += " " + HudRenderCallBackClass.get_position_difference(new BlockPos(lockedOnBlock), client);
//            }
        MainClass.speakWithNarrator(text, true);
    }

    private void playUnlockingSound(MinecraftClient client) {
        if (client.player == null) return;

//        if (Config.get(ConfigKeys.POI_UNLOCKING_SOUND_KEY.getKey())) {
        float volume = 0.4f;
        client.player.playSound(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM.value(), volume, 2f);
//        }
    }
}