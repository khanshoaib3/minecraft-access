package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POILockingConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.NarrationUtils;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import com.github.khanshoaib3.minecraft_access.utils.WorldUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
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
import net.minecraft.util.Pair;
import net.minecraft.util.math.Vec3d;

import java.util.List;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.TreeMap;

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
    public boolean isLockedOnWhereEyeOfEnderDisappears = false;
    public String lockedOnBlockEntries = "";
    private Interval interval;

    private boolean lockOnBlocks;
    private boolean speakDistance;
    private boolean unlockingSound;

    private boolean onPOIMarkingNow = false;

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

            // Entries are different shape of blocks when they're in different states,
            // for example, opened chest and closed chest are different states of chest block,
            // they are different entries when invoking getEntries().
            String entries = blockState.getEntries().toString();
            boolean entriesOfLockedBlockNotChanged = entries.equalsIgnoreCase(lockedOnBlockEntries);

            if (entriesOfLockedBlockNotChanged || isLockedOnWhereEyeOfEnderDisappears)
                PlayerUtils.lookAt(lockedOnBlock);
            else {
                // Unlock if (the state of) locked block is changed
                unlock(true);
            }
        }

        boolean isLockingKeyPressed = KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().lockingHandlerKey);
        if (isLockingKeyPressed && Screen.hasAltDown()) {
            if (lockedOnEntity != null || lockedOnBlock != null) {
                MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.unlocked"), true);
                unlock(true);
            }
        } else if (isLockingKeyPressed) {
            relock();
        }
    }

    private void unlock(boolean playSound) {
        lockedOnEntity = null;
        lockedOnBlockEntries = "";
        lockedOnBlock = null;
        isLockedOnWhereEyeOfEnderDisappears = false;
        if (playSound) playUnlockingSound();
    }

    private void relock() {
        if (!POIEntities.markedEntities.isEmpty()) {
            Entity entity = POIEntities.markedEntities.firstEntry().getValue();
            if (lockOnEntity(entity)) return;
        }

        boolean suppressLockingOnNonMarkedThings = onPOIMarkingNow && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled();
        if (suppressLockingOnNonMarkedThings) {
            if (POIBlocks.markedBlocks.isEmpty()) {
                return;
            } else {
                // Skip entity locking logic
                determineClosestEntriesAndLock();
            }
        }

        if (!POIEntities.hostileEntity.isEmpty()) {
            Entity entity = POIEntities.hostileEntity.firstEntry().getValue();
            if (lockOnEntity(entity)) return;
        }

        if (!POIEntities.passiveEntity.isEmpty()) {
            Entity entity = POIEntities.passiveEntity.firstEntry().getValue();
            if (lockOnEntity(entity)) return;
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
                unlock(true);
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
            isLockedOnWhereEyeOfEnderDisappears = true;
        }

        unlock(true);
        return true;
    }

    /**
     * @return true if locked
     */
    public boolean lockOnEntity(Entity entity) {
        if (!entity.isAlive()) return false;

        unlock(false);
        lockedOnEntity = entity;

        String narration = NarrationUtils.narrateEntity(entity);
        if (this.speakDistance) {
            narration += " " + NarrationUtils.narrateRelativePositionOfPlayerAnd(entity.getBlockPos());
        }
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.locked", narration), true);
        return true;
    }

    private void determineClosestEntriesAndLock() {
        Double minPlayerDistance = Double.MAX_VALUE;
        Vec3d nearestBlockPosition = null;

        List<TreeMap<Double, Vec3d>> scannedBlockMaps = List.of(
                POIBlocks.doorBlocks,
                POIBlocks.buttonBlocks,
                POIBlocks.ladderBlocks,
                POIBlocks.leverBlocks,
                POIBlocks.trapDoorBlocks,
                POIBlocks.otherBlocks,
                POIBlocks.oreBlocks,
                POIBlocks.fluidBlocks,
                POIBlocks.markedBlocks
        );

        for (TreeMap<Double, Vec3d> map : scannedBlockMaps) {
            if (!map.isEmpty()) {
                Entry<Double, Vec3d> closestOneInThisType = map.firstEntry();
                Double distanceWithPlayer = closestOneInThisType.getKey();
                if (distanceWithPlayer < minPlayerDistance) {
                    minPlayerDistance = distanceWithPlayer;
                    nearestBlockPosition = closestOneInThisType.getValue();
                }
            }
        }

        if (Objects.isNull(nearestBlockPosition)) return;

        unlock(false);
        lockedOnBlock = new BlockPos3d(nearestBlockPosition);
        BlockState blockState = WorldUtils.getClientWorld().orElseThrow().getBlockState(lockedOnBlock);
        lockedOnBlockEntries = blockState.getEntries().toString();

        Pair<String, String> toSpeakAndCurrentQuery = NarrationUtils.narrateBlock(lockedOnBlock, "");
        String toSpeak = toSpeakAndCurrentQuery.getLeft();
        if (this.speakDistance) {
            toSpeak += " " + NarrationUtils.narrateRelativePositionOfPlayerAnd(lockedOnBlock);
        }
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.locked", toSpeak), true);
    }

    private void playUnlockingSound() {
        if (!this.unlockingSound) return;
        PlayerUtils.playSoundOnPlayer(SoundEvents.BLOCK_NOTE_BLOCK_BASEDRUM, 0.4f, 2f);
    }

    public void setMarking(boolean marking) {
        this.onPOIMarkingNow = marking;
    }
}