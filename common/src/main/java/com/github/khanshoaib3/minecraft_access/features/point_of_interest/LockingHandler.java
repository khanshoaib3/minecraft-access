package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POILockingConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.position.PositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.system.KeyUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;

import java.util.Map.Entry;

/**
 * Locks on to the nearest entity or block.<br><br>
 * Keybindings and combinations:<br>
 * 1. Locking Key (default: Y) = Locks onto the nearest entity or block<br>
 * 2. Alt key + Locking Key = Unlocks from the currently locked entity or block<br>
 */
public class LockingHandler {
    private static final LockingHandler instance;
    public Entity lockedOnEntity = null;
    public BlockPos3d lockedOnBlock = null;
    public boolean isLockedOntoEyeOfEnderTarget = false; // The block where the eye of ender disappears
    public String lockedOnBlockEntries = "";
    private Interval interval;

    private boolean lockOnBlocks;
    private boolean speakDistance;
    private boolean unlockingSound;

    private boolean marking = false;

    static {
        instance = new LockingHandler();
    }

    private LockingHandler() {
    }

    public static LockingHandler getInstance() {
        return instance;
    }

    public void update() {
        if (interval != null && !interval.isReady()) return;
        try {
            loadConfigurations();
            mainLogic();
        } catch (Exception e) {
            MainClass.errorLog("An error while updating LockingHandler", e);
        }
    }

    /**
     * Loads the configs from the config.json
     */
    private void loadConfigurations() {
        POILockingConfigMap map = POILockingConfigMap.getInstance();
        this.lockOnBlocks = map.isLockOnBlocks();
        this.speakDistance = map.isSpeakDistance();
        this.unlockingSound = map.isUnlockingSound();
        this.interval = Interval.inMilliseconds(map.getDelay(), this.interval);
    }

    private void mainLogic() {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();

        if (minecraftClient == null) return;
        if (minecraftClient.player == null) return;
        if (minecraftClient.world == null) return;
        if (minecraftClient.currentScreen != null) return;

        if (lockedOnEntity != null) {
            if (unlockFromDeadEntity()) return;
            PlayerUtils.lookAt(lockedOnEntity);
        }

        if (lockedOnBlock != null) {
            BlockState blockState = minecraftClient.world.getBlockState(lockedOnBlock);

            if (unlockFromLadderIfClimbingOnIt(blockState)) return;

            String entries = blockState.getEntries() + String.valueOf(blockState.getBlock()) + lockedOnBlock;
            if (entries.equalsIgnoreCase(lockedOnBlockEntries) || isLockedOntoEyeOfEnderTarget)
                PlayerUtils.lookAt(lockedOnBlock);
            else {
                lockedOnBlockEntries = "";
                lockedOnBlock = null;
                playUnlockingSound();
            }
        }

        boolean isLockingKeyPressed = KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().lockingHandlerKey);
        if (!isLockingKeyPressed) return;

        // "Control" + "Locking Key" combination for POI marking feature
        if (Screen.hasControlDown()) return;

        if (Screen.hasAltDown()) {
            if (lockedOnEntity != null || lockedOnBlock != null) {
                MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.unlocked"), true);
                lockedOnEntity = null;
                lockedOnBlockEntries = "";
                lockedOnBlock = null;
                isLockedOntoEyeOfEnderTarget = false;
                playUnlockingSound();
            }
            return;
        }

        if (!POIEntities.markedEntities.isEmpty()) {
            Entity entity = POIEntities.markedEntities.firstEntry().getValue();
            if (entity.isAlive()) {
                lockOnEntity(entity);
                return;
            }
        }

        if (marking && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled()) {
            if (POIBlocks.markedBlocks.isEmpty()) {
                return;
            } else {
                // Skip entity locking logic
                determineClosestEntriesAndLock();
            }
        }

        if (!POIEntities.hostileEntity.isEmpty()) {
            Entity entity = POIEntities.hostileEntity.firstEntry().getValue();
            if (entity.isAlive()) {
                lockOnEntity(entity);
                return;
            }
        }

        if (!POIEntities.passiveEntity.isEmpty()) {
            Entity entity = POIEntities.passiveEntity.firstEntry().getValue();
            if (entity.isAlive()) {
                lockOnEntity(entity);
                return;
            }
        }

        if (!this.lockOnBlocks) return;

        determineClosestEntriesAndLock();
    }

    /**
     * Automatically unlock from the ladder after the player starting climbing the ladder.
     * When you stand directly in front of the ladder, the distance is 1.5,
     * since the player position is player's leg (player standing y + 1),
     * and the mod will lock on the ladder at the same height of the player head (player standing y + 2).
     *
     * @param blockState state of locked block, taken from world
     * @return true if unlocked
     */
    private boolean unlockFromLadderIfClimbingOnIt(BlockState blockState) {
        if (Blocks.LADDER.equals(blockState.getBlock())) {

            Vec3d playerPos = PlayerPositionUtils.getPlayerPosition().orElseThrow();
            double distance = lockedOnBlock.toCenterPos().distanceTo(playerPos);
            if (distance <= 0.5) {
                lockedOnBlock = null;
                playUnlockingSound();
                return true;
            }
        }
        return false;
    }

    /**
     * If the entity has dead, we'll automatically unlock from it.
     *
     * @return true if unlocked
     */
    private boolean unlockFromDeadEntity() {
        if (!lockedOnEntity.isAlive()) return false;

        // When the eye of ender disappears, its isAlive() will also return false.
        // Change the lock target to the last (block) position (somewhere floating in the air) where the eye of ender disappeared,
        // so the player can continue walking until being under that position.
        if (lockedOnEntity instanceof EyeOfEnderEntity) {
            lockedOnBlock = BlockPos3d.of(lockedOnEntity.getBlockPos());
            isLockedOntoEyeOfEnderTarget = true;
        }

        lockedOnEntity = null;
        playUnlockingSound();
        return true;
    }

    private void lockOnEntity(Entity entity) {
        String text = entity.getName().getString();
        lockedOnEntity = entity;
        lockedOnBlockEntries = "";

        lockedOnBlock = null;

        if (this.speakDistance) text += " " + PositionUtils.getPositionDifference(entity.getBlockPos());
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.locked", text), true);
    }

    private void determineClosestEntriesAndLock() {
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

        Entry<Double, Vec3d> closestMarkedBlockEntry = null;
        Double closestMarkedBlockDouble = -9999.0;
        if (!POIBlocks.markedBlocks.isEmpty()) {
            closestMarkedBlockEntry = POIBlocks.markedBlocks.firstEntry();
            closestMarkedBlockDouble = closestMarkedBlockEntry.getKey();
            closest = closestMarkedBlockDouble;
        }

        if (closest == -9999.0) return;

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

        lockOntoBlocksOrPassiveEntity(closest, closestDoorBlockEntry,
                closestDoorBlockDouble, closestButtonBlockEntry, closestButtonBlockDouble,
                closestLadderBlockEntry, closestLadderBlockDouble, closestLeverBlockEntry,
                closestLeverBlockDouble, closestTrapDoorBlockEntry, closestTrapDoorBlockDouble,
                closestFluidBlockEntry, closestFluidBlockDouble, closestOtherBlockEntry,
                closestOtherBlockDouble, closestOreBlockEntry, closestOreBlockDouble,
                closestMarkedBlockEntry, closestMarkedBlockDouble);

        narrateBlockPosAndSetBlockEntries();
    }

    private void lockOntoBlocksOrPassiveEntity(Double closest,
                                               Entry<Double, Vec3d> closestDoorBlockEntry, Double closestDoorBlockDouble,
                                               Entry<Double, Vec3d> closestButtonBlockEntry, Double closestButtonBlockDouble,
                                               Entry<Double, Vec3d> closestLadderBlockEntry, Double closestLadderBlockDouble,
                                               Entry<Double, Vec3d> closestLeverBlockEntry, Double closestLeverBlockDouble,
                                               Entry<Double, Vec3d> closestTrapDoorBlockEntry, Double closestTrapDoorBlockDouble,
                                               Entry<Double, Vec3d> closestFluidBlockEntry, Double closestFluidBlockDouble,
                                               Entry<Double, Vec3d> closestOtherBlockEntry, Double closestOtherBlockDouble,
                                               Entry<Double, Vec3d> closestOreBlockEntry, Double closestOreBlockDouble,
                                               Entry<Double, Vec3d> closestMarkedBlockEntry, Double closestMarkedBlockDouble) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.player == null) return;

        if (closest.equals(closestMarkedBlockDouble) && closestMarkedBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(closestMarkedBlockEntry.getValue());
            lockedOnEntity = null;
        }

        if (closest.equals(closestOreBlockDouble) && closestOreBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(closestOreBlockEntry.getValue());
            lockedOnEntity = null;
        } else if (closest.equals(closestDoorBlockDouble) && closestDoorBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(NonCubeBlockAbsolutePositions.getDoorAbsolutePosition(client, closestDoorBlockEntry.getValue()));
            lockedOnEntity = null;
        } else if (closest.equals(closestButtonBlockDouble) && closestButtonBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(NonCubeBlockAbsolutePositions.getButtonsAbsolutePosition(client, closestButtonBlockEntry.getValue()));
            lockedOnEntity = null;
        } else if (closest.equals(closestLadderBlockDouble) && closestLadderBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(NonCubeBlockAbsolutePositions.getLaddersAbsolutePosition(client, closestLadderBlockEntry.getValue()));
            lockedOnEntity = null;
        } else if (closest.equals(closestLeverBlockDouble) && closestLeverBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(NonCubeBlockAbsolutePositions.getLeversAbsolutePosition(client, closestLeverBlockEntry.getValue()));
            lockedOnEntity = null;
        } else if (closest.equals(closestTrapDoorBlockDouble) && closestTrapDoorBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(NonCubeBlockAbsolutePositions.getTrapDoorAbsolutePosition(client, closestTrapDoorBlockEntry.getValue()));
            lockedOnEntity = null;
        } else if (closest.equals(closestFluidBlockDouble) && closestFluidBlockDouble != -9999.0
                && !client.player.isSwimming() && !client.player.isSubmergedInWater() && !client.player.isInsideWaterOrBubbleColumn() && !client.player.isInLava()) {
            lockedOnBlock = new BlockPos3d(closestFluidBlockEntry.getValue());
            lockedOnEntity = null;
        } else if (closest.equals(closestOtherBlockDouble) && closestOtherBlockDouble != -9999.0) {
            lockedOnBlock = new BlockPos3d(closestOtherBlockEntry.getValue());
            lockedOnEntity = null;
        }
    }

    private void narrateBlockPosAndSetBlockEntries() {
        MinecraftClient client = MinecraftClient.getInstance();
        if (client.world == null) return;
        if (lockedOnBlock == null) return;

        BlockState blockState = client.world.getBlockState(lockedOnBlock);
        lockedOnBlockEntries = blockState.getEntries() + String.valueOf(blockState.getBlock()) + (lockedOnBlock);

        Block closestBlock = blockState.getBlock();

        MutableText mutableText = (Text.literal("")).append(closestBlock.getName()); // post 1.19
//            MutableText mutableText = (new net.minecraft.text.LiteralText("")).append(closestBlock.getName()); // pre 1.19
        String text = mutableText.getString();

        if (this.speakDistance)
            text += " " + PositionUtils.getPositionDifference(lockedOnBlock);
        MainClass.speakWithNarrator(text, true);
    }

    private void playUnlockingSound() {
        if (!this.unlockingSound) return;
        PlayerUtils.playSoundOnPlayer(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, 0.4f, 2f);
    }

    public void setMarking(boolean marking) {
        this.marking = marking;
    }
}