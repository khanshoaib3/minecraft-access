package com.github.khanshoaib3.minecraft_access.features.point_of_interest;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIBlocksConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.POIMarkingConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.TimeUtils;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import net.minecraft.block.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Property;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.function.Predicate;

/**
 * Scans the area to find exposed ore blocks, doors, buttons, ladders, etc., groups them and plays a sound only at ore blocks.
 */
public class POIBlocks {
    private static final POIBlocks instance;
    private MinecraftClient minecraftClient;

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
    private TimeUtils.Interval interval;

    private static final List<Predicate<BlockState>> blockList = Lists.newArrayList();
    private static final List<Predicate<BlockState>> oreBlockList = Lists.newArrayList();
    private Predicate<BlockState> markedBlock = state -> false;
    private boolean marking = false;

    static {
        try {
            blockList.add(state -> state.isOf(Blocks.PISTON));
            blockList.add(state -> state.isOf(Blocks.STICKY_PISTON));
            blockList.add(state -> state.isOf(Blocks.RESPAWN_ANCHOR));
            blockList.add(state -> state.isOf(Blocks.BELL));
            blockList.add(state -> state.isOf(Blocks.OBSERVER));
            blockList.add(state -> state.isOf(Blocks.DAYLIGHT_DETECTOR));
            blockList.add(state -> state.isOf(Blocks.JUKEBOX));
            blockList.add(state -> state.isOf(Blocks.LODESTONE));
            blockList.add(state -> state.getBlock() instanceof BeehiveBlock);
            blockList.add(state -> state.getBlock() instanceof ComposterBlock);
            blockList.add(state -> state.isOf(Blocks.OBSERVER));
            blockList.add(state -> state.isIn(BlockTags.FENCE_GATES));
            blockList.add(state -> state.getBlock() instanceof SculkShriekerBlock);
            blockList.add(state -> state.getBlock() instanceof SculkCatalystBlock);
            blockList.add(state -> state.getBlock() instanceof SculkSensorBlock);

            oreBlockList.add(state -> state.isOf(Blocks.COAL_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_COAL_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.COPPER_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_COPPER_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DIAMOND_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_DIAMOND_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.EMERALD_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_EMERALD_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.GOLD_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_GOLD_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.NETHER_GOLD_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.IRON_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_IRON_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.LAPIS_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_LAPIS_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.REDSTONE_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.DEEPSLATE_REDSTONE_ORE));
            oreBlockList.add(state -> state.isOf(Blocks.NETHER_QUARTZ_ORE));
            instance = new POIBlocks();
        } catch (Exception e) {
            throw new RuntimeException("Exception occurred in creating POIBlocks instance");
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

            MainClass.infoLog("POIBlock started...");

            checkBlock(new BlockPos(new Vec3i(posX, posY, posZ)), 0);
            checkBlock(new BlockPos(new Vec3i(posX, posY + 3, posZ)), 0);
            checkBlock(new BlockPos(new Vec3i(posX, posY + 1, posZ)), this.range);
            checkBlock(new BlockPos(new Vec3i(posX, posY + 2, posZ)), this.range);

            MainClass.infoLog("POIBlock ended.");

        } catch (Exception e) {
            MainClass.errorLog("\nError encountered in poi blocks feature.");
            e.printStackTrace();
        }
    }

    private void loadConfigurations() {
        POIBlocksConfigMap poiBlocksConfigMap = MainClass.config.getConfigMap().getPoiConfigMap().getBlocksConfigMap();
        this.enabled = poiBlocksConfigMap.isEnabled();
        this.detectFluidBlocks = poiBlocksConfigMap.isDetectFluidBlocks();
        this.range = poiBlocksConfigMap.getRange();
        this.playSound = poiBlocksConfigMap.isPlaySound();
        this.volume = poiBlocksConfigMap.getVolume();
        this.playSoundForOtherBlocks = poiBlocksConfigMap.isPlaySoundForOtherBlocks();
        this.interval = TimeUtils.Interval.inMilliseconds(poiBlocksConfigMap.getDelay(), this.interval);
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
        } else if (this.detectFluidBlocks && block instanceof FluidBlock) {
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);
            if (fluidState.getLevel() == 8) {
                fluidBlocks.put(diff, blockVec3dPos);
                soundType = "blocks";
            }

//            MainClass.speakWithNarrator(I18n.translate("narrate.apextended.poiblock.warn"), true);
        } else if (oreBlockList.stream().anyMatch($ -> $.test(blockState))) {
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
        } else if (blockList.stream().anyMatch($ -> $.test(blockState))) {
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
                MainClass.infoLog("{POIBlocks} Playing sound at x:%d y:%d z:%d".formatted((int) posX, (int) posY, (int) posZ));
                minecraftClient.world.playSound(minecraftClient.player, new BlockPos(new Vec3i((int) blockVec3dPos.x, (int) blockVec3dPos.y, (int) blockVec3dPos.z)), SoundEvents.ENTITY_ITEM_PICKUP,
                        SoundCategory.BLOCKS, volume, -5f);
            }

            if (marking && POIMarkingConfigMap.getInstance().isSuppressOtherWhenEnabled()) {
                if (!soundType.equalsIgnoreCase("mark")) {
                    MainClass.infoLog("{POIBlocks} Suppress sound at x:%d y:%d z:%d".formatted((int) posX, (int) posY, (int) posZ));
                }
                return;
            }

            MainClass.infoLog("{POIBlocks} Playing sound at x:%d y:%d z:%d".formatted((int) posX, (int) posY, (int) posZ));

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

    public void setMarking(boolean marking) {
        this.marking = marking;
    }

    public void setMarkedBlock(Block block) {
        if (block == null) {
            this.marking = false;
            this.markedBlock = s -> false;
        } else {
            this.marking = true;
            this.markedBlock = s -> s.isOf(block);
        }
    }
}