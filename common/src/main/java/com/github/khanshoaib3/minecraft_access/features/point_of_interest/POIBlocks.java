package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.config.config_maps.POIBlocksConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.PlayerUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.google.common.collect.ImmutableSet;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.*;
import java.util.function.Predicate;

/**
 * Scans the area to find exposed ore blocks, doors, buttons, ladders, etc., groups them and plays a sound only at ore blocks.
 */
@Slf4j
public class POIBlocks {
    private static final POIBlocks instance;
    private MinecraftClient minecraftClient;

    private static final Block[] POI_BLOCKS = new Block[]{
            Blocks.PISTON,
            Blocks.STICKY_PISTON,
            Blocks.RESPAWN_ANCHOR,
            Blocks.BELL,
            Blocks.OBSERVER,
            Blocks.DAYLIGHT_DETECTOR,
            Blocks.JUKEBOX,
            Blocks.LODESTONE,
            Blocks.BEE_NEST,
            Blocks.COMPOSTER,
            Blocks.OBSERVER,
            Blocks.SCULK_SHRIEKER,
            Blocks.SCULK_CATALYST,
            Blocks.CALIBRATED_SCULK_SENSOR,
            Blocks.SCULK_SENSOR
    };

    private static final List<Predicate<BlockState>> poiBlockPredicates = Arrays.stream(POI_BLOCKS)
            .map(b -> (Predicate<BlockState>) state -> state.isOf(b))
            .toList();

    private static final Block[] ORE_BLOCKS = new Block[]{
            Blocks.COAL_ORE,
            Blocks.DEEPSLATE_COAL_ORE,
            Blocks.COPPER_ORE,
            Blocks.DEEPSLATE_COPPER_ORE,
            Blocks.DIAMOND_ORE,
            Blocks.DEEPSLATE_DIAMOND_ORE,
            Blocks.EMERALD_ORE,
            Blocks.DEEPSLATE_EMERALD_ORE,
            Blocks.GOLD_ORE,
            Blocks.DEEPSLATE_GOLD_ORE,
            Blocks.NETHER_GOLD_ORE,
            Blocks.IRON_ORE,
            Blocks.DEEPSLATE_IRON_ORE,
            Blocks.LAPIS_ORE,
            Blocks.DEEPSLATE_LAPIS_ORE,
            Blocks.REDSTONE_ORE,
            Blocks.DEEPSLATE_REDSTONE_ORE,
            Blocks.NETHER_QUARTZ_ORE
    };

    private static final List<Predicate<BlockState>> oreBlockPredicates = Arrays.stream(ORE_BLOCKS)
            .map(b -> (Predicate<BlockState>) state -> state.isOf(b))
            .toList();

    public static TreeMap<Double, Vec3d> oreBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> doorBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> buttonBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> ladderBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> leverBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> trapDoorBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> fluidBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> otherBlocks = new TreeMap<>();
    public static TreeMap<Double, Vec3d> markedBlocks = new TreeMap<>();

    private List<Vec3d> checkedBlocks = new ArrayList<>();
    private boolean enabled;
    private boolean detectFluidBlocks;
    private int range;
    private boolean playSound;
    private float volume;
    private boolean playSoundForOtherBlocks;
    private Interval interval;
    private Predicate<BlockState> markedBlock = state -> false;
    @Setter
    private boolean onPOIMarkingNow = false;

    static {
        try {
            instance = new POIBlocks();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating POIBlocks instance", e);
        }
    }

    public static POIBlocks getInstance() {
        return instance;
    }

    private POIBlocks() {
        loadConfigurations();
    }

    public void update() {
        try {
            loadConfigurations();

            if (!this.enabled) return;
            if (interval != null && !interval.isReady()) return;

            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;
            if (minecraftClient.currentScreen != null) return; //Prevent running if any screen is opened

            oreBlocks = new TreeMap<>();
            doorBlocks = new TreeMap<>();
            buttonBlocks = new TreeMap<>();
            ladderBlocks = new TreeMap<>();
            leverBlocks = new TreeMap<>();
            trapDoorBlocks = new TreeMap<>();
            fluidBlocks = new TreeMap<>();
            otherBlocks = new TreeMap<>();
            markedBlocks = new TreeMap<>();

            BlockPos pos = minecraftClient.player.getBlockPos();

            int posX = pos.getX();
            int posY = pos.getY() - 1;
            int posZ = pos.getZ();
            checkedBlocks = new ArrayList<>();

            log.debug("POIBlock started...");

            checkBlock(new BlockPos(new Vec3i(posX, posY, posZ)), 0);
            checkBlock(new BlockPos(new Vec3i(posX, posY + 3, posZ)), 0);
            checkBlock(new BlockPos(new Vec3i(posX, posY + 1, posZ)), this.range);
            checkBlock(new BlockPos(new Vec3i(posX, posY + 2, posZ)), this.range);

            log.debug("POIBlock ended.");

        } catch (Exception e) {
            log.error("Error encountered in poi blocks feature.", e);
        }
    }

    private void loadConfigurations() {
        POIBlocksConfigMap poiBlocksConfigMap = POIBlocksConfigMap.getInstance();
        this.enabled = poiBlocksConfigMap.isEnabled();
        this.detectFluidBlocks = poiBlocksConfigMap.isDetectFluidBlocks();
        this.range = poiBlocksConfigMap.getRange();
        this.playSound = poiBlocksConfigMap.isPlaySound();
        this.volume = poiBlocksConfigMap.getVolume();
        this.playSoundForOtherBlocks = poiBlocksConfigMap.isPlaySoundForOtherBlocks();
        this.interval = Interval.inMilliseconds(poiBlocksConfigMap.getDelay(), this.interval);
    }

    private void checkBlock(BlockPos blockPos, int val) {
        if (minecraftClient.player == null) return;
        if (minecraftClient.world == null) return;

        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
        Block block = blockState.getBlock();

        Vec3d playerVec3dPos = minecraftClient.player.getEyePos(); // post 1.17
//        Vec3d playerVec3dPos = (new Vec3d(client.player.getX(), client.player.getEyeY(), client.player.getZ())); // pre 1.17
        double posX = blockPos.getX(), posY = blockPos.getY(), posZ = blockPos.getZ();
        Vec3d blockVec3dPos = Vec3d.ofCenter(blockPos);

        if (checkedBlocks.contains(blockVec3dPos)) return;
        checkedBlocks.add(blockVec3dPos);

        double diff = playerVec3dPos.distanceTo(blockVec3dPos);
        String soundType = "";

        if (markedBlock.test(blockState)) {
            markedBlocks.put(diff, blockVec3dPos);
            soundType = "mark";
        } else if (this.detectFluidBlocks && block instanceof FluidBlock && !PlayerUtils.isInFluid()) {
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);
            if (fluidState.getLevel() == 8) {
                fluidBlocks.put(diff, blockVec3dPos);
                soundType = "blocks";
            }

//            MainClass.speakWithNarrator(I18n.translate("narrate.apextended.poiblock.warn"), true);
        } else if (oreBlockPredicates.stream().anyMatch($ -> $.test(blockState))) {
            oreBlocks.put(diff, blockVec3dPos);
            soundType = "ore";
        } else if (block instanceof ButtonBlock) {
            buttonBlocks.put(diff, blockVec3dPos);
            soundType = "blocks";
        } else if (block instanceof LadderBlock) {
            ladderBlocks.put(diff, blockVec3dPos);
            soundType = "blocks";
        } else if (block instanceof TrapdoorBlock) {
            trapDoorBlocks.put(diff, blockVec3dPos);
            soundType = "blocks";
        } else if (block instanceof LeverBlock) {
            leverBlocks.put(diff, blockVec3dPos);
            soundType = "blocks";
        } else if (block instanceof DoorBlock) {
            ImmutableSet<Map.Entry<Property<?>, Comparable<?>>> entries = blockState.getEntries().entrySet();
            for (Map.Entry<Property<?>, Comparable<?>> i : entries) {

                if (i.getKey().getName().equals("half")) {
                    if (i.getValue().toString().equals("upper")) {
                        doorBlocks.put(diff, blockVec3dPos);
                        soundType = "blocks";
                    }
                    break;
                }

            }
        } else if (poiBlockPredicates.stream().anyMatch($ -> $.test(blockState))) {
            otherBlocks.put(diff, blockVec3dPos);
            soundType = "blocks";
        } else if (blockState.createScreenHandlerFactory(minecraftClient.world, blockPos) != null) {
            otherBlocks.put(diff, blockVec3dPos);
            soundType = "blocksWithInterface";
        } else if (blockState.isAir() && val - 1 >= 0) {
            checkBlock(new BlockPos(new Vec3i((int) posX, (int) posY, (int) (posZ - 1))), val - 1); // North Block
            checkBlock(new BlockPos(new Vec3i((int) posX, (int) posY, (int) posZ + 1)), val - 1); // South Block
            checkBlock(new BlockPos(new Vec3i((int) posX - 1, (int) posY, (int) posZ)), val - 1); // West Block
            checkBlock(new BlockPos(new Vec3i((int) posX + 1, (int) posY, (int) posZ)), val - 1); // East Block
            checkBlock(new BlockPos(new Vec3i((int) posX, (int) posY + 1, (int) posZ)), val - 1); // Top Block
            checkBlock(new BlockPos(new Vec3i((int) posX, (int) posY - 1, (int) posZ)), val - 1); // Bottom Block
        }

        if (this.playSound && this.volume > 0 && !soundType.isEmpty()) {

            if (soundType.equalsIgnoreCase("mark")) {
                log.debug("{POIBlocks} Playing sound at x:%d y:%d z:%d".formatted((int) posX, (int) posY, (int) posZ));
                minecraftClient.world.playSound(minecraftClient.player, new BlockPos(new Vec3i((int) blockVec3dPos.x, (int) blockVec3dPos.y, (int) blockVec3dPos.z)), SoundEvents.ENTITY_ITEM_PICKUP,
                        SoundCategory.BLOCKS, volume, -5f);
            }

            if (onPOIMarkingNow && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled()) {
                if (!soundType.equalsIgnoreCase("mark")) {
                    log.debug("{POIBlocks} Suppress sound at x:%d y:%d z:%d".formatted((int) posX, (int) posY, (int) posZ));
                }
                return;
            }

            log.debug("{POIBlocks} Playing sound at x:%d y:%d z:%d".formatted((int) posX, (int) posY, (int) posZ));

            if (soundType.equalsIgnoreCase("ore"))
                minecraftClient.world.playSound(minecraftClient.player, new BlockPos(new Vec3i((int) blockVec3dPos.x, (int) blockVec3dPos.y, (int) blockVec3dPos.z)), SoundEvents.ENTITY_ITEM_PICKUP,
                        SoundCategory.BLOCKS, volume, -5f);
            else if (this.playSoundForOtherBlocks && soundType.equalsIgnoreCase("blocks"))
                minecraftClient.world.playSound(minecraftClient.player, new BlockPos(new Vec3i((int) blockVec3dPos.x, (int) blockVec3dPos.y, (int) blockVec3dPos.z)), SoundEvents.BLOCK_NOTE_BLOCK_BIT.value(),
                        SoundCategory.BLOCKS, volume, 2f);
            else if (this.playSoundForOtherBlocks && soundType.equalsIgnoreCase("blocksWithInterface"))
                minecraftClient.world.playSound(minecraftClient.player, new BlockPos(new Vec3i((int) blockVec3dPos.x, (int) blockVec3dPos.y, (int) blockVec3dPos.z)), SoundEvents.BLOCK_NOTE_BLOCK_BANJO.value(),
                        SoundCategory.BLOCKS, volume, 0f);

        }
    }

    public void setMarkedBlock(Block block) {
        if (block == null) {
            this.onPOIMarkingNow = false;
            this.markedBlock = s -> false;
        } else {
            this.onPOIMarkingNow = true;
            this.markedBlock = s -> s.isOf(block);
        }
    }

    public List<TreeMap<Double, Vec3d>> getLockingCandidates() {
        boolean suppressLockingOnNonMarkedThings = onPOIMarkingNow && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled();
        return suppressLockingOnNonMarkedThings ?
                List.of(markedBlocks) :
                List.of(doorBlocks, buttonBlocks, ladderBlocks, leverBlocks, trapDoorBlocks, otherBlocks, oreBlocks, fluidBlocks, markedBlocks);
    }
}