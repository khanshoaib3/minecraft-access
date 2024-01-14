package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.MobSpawnerLogicAccessor;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
import net.minecraft.block.*;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.enums.ComparatorMode;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.registry.tag.BlockTags;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.math.Vec3i;
import org.apache.logging.log4j.util.Strings;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * Translate input objects to narration text.
 */
public class NarrationUtils {
    /**
     * One redstone wire must be connected with another wire at one of three positions: [side, side down, side up],
     * since we are checking if the wire is connecting with ALL directions, only take one sample position (x+1) is enough.
     */
    public static final Set<Vec3i> THREE_SAMPLE_POSITIONS = Set.of(new Vec3i(1, 0, 0), new Vec3i(1, -1, 0), new Vec3i(1, 1, 0));
    public static final Predicate<BlockState> IS_REDSTONE_WIRE = (BlockState state) -> state.getBlock() instanceof RedstoneWireBlock;

    public static String narrateEntity(Entity entity) {
        String text = entity.getName().getString();

        // Add its type in front of its name if it has been renamed with name tag,
        // so even if there are two different types of entities that named the same name,
        // the mod can make the player tell the difference:
        // "Cat Neko", "Dog Neko"...
        if (entity.hasCustomName()) {
            text = I18n.translate(entity.getType().getTranslationKey()) + " " + text;
        }

        if (entity instanceof AnimalEntity animalEntity) {

            if (animalEntity instanceof TameableEntity tameableEntity) {
                text = tameableEntity.isTamed() ? I18n.translate("minecraft_access.read_crosshair.is_tamed", text) : text;
            }

            if (animalEntity instanceof SheepEntity sheepEntity) {
                text = getSheepInfo(sheepEntity, text);
            } else if (animalEntity instanceof CatEntity catEntity) {
                text = catEntity.isInSittingPose() ? addSittingInfo(text) : text;
            } else if (animalEntity instanceof WolfEntity wolfEntity) {
                text = wolfEntity.isInSittingPose() ? addSittingInfo(text) : text;
            } else if (animalEntity instanceof FoxEntity foxEntity) {
                text = foxEntity.isSitting() ? addSittingInfo(text) : text;
            } else if (animalEntity instanceof ParrotEntity parrotEntity) {
                text = parrotEntity.isInSittingPose() ? addSittingInfo(text) : text;
            } else if (animalEntity instanceof PandaEntity pandaEntity) {
                text = pandaEntity.isSitting() ? addSittingInfo(text) : text;
            } else if (animalEntity instanceof CamelEntity camelEntity) {
                text = camelEntity.isSitting() ? addSittingInfo(text) : text;
            }

            if (animalEntity.isBaby())
                text = I18n.translate("minecraft_access.read_crosshair.animal_entity_baby", text);
            if (animalEntity.isLeashed())
                text = I18n.translate("minecraft_access.read_crosshair.animal_entity_leashed", text);
        }

        if (entity instanceof HostileEntity) {
            if (entity instanceof ZombieVillagerEntity zombieVillagerEntity) {
                text = zombieVillagerEntity.isConverting() ?
                        I18n.translate("minecraft_access.read_crosshair.zombie_villager_is_curing", text) :
                        text;
            }
        }

        return text;
    }

    private static String addSittingInfo(String currentQuery) {
        return I18n.translate("minecraft_access.read_crosshair.is_sitting", currentQuery);
    }

    private static String getSheepInfo(SheepEntity sheepEntity, String currentQuery) {
        String dyedColor = sheepEntity.getColor().getName();
        String translatedColor = I18n.translate("minecraft_access.color." + dyedColor);
        String shearable = sheepEntity.isShearable() ?
                I18n.translate("minecraft_access.read_crosshair.shearable", currentQuery) :
                I18n.translate("minecraft_access.read_crosshair.not_shearable", currentQuery);
        return translatedColor + " " + shearable;
    }

    public static String narrateNumber(double d) {
        return d >= 0 ? String.valueOf(d) : I18n.translate("minecraft_access.other.negative", -d);
    }

    public static String narrateRelativePositionOfPlayerAnd(BlockPos blockPos) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return "up";
        if (minecraftClient.player == null) return "up";

        Direction dir = minecraftClient.player.getHorizontalFacing();

//        Vec3d diff = minecraftClient.player.getEyePos().subtract(Vec3d.ofCenter(blockPos)); // post 1.18
        Vec3d diff = new Vec3d(minecraftClient.player.getX(), minecraftClient.player.getEyeY(), minecraftClient.player.getZ()).subtract(Vec3d.ofCenter(blockPos)); // pre 1.18
        BlockPos diffBlockPos = new BlockPos((int) diff.x, (int) diff.y, (int) diff.z); // post 1.20
//        BlockPos diffBlockPos = new BlockPos(Math.round(diff.x), Math.round(diff.y), Math.round(diff.z));

        String diffXBlockPos = "";
        String diffYBlockPos = "";
        String diffZBlockPos = "";

        if (diffBlockPos.getX() != 0) {
            if (dir == Direction.NORTH) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "right", "left");
            } else if (dir == Direction.SOUTH) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "left", "right");
            } else if (dir == Direction.EAST) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "away", "behind");
            } else if (dir == Direction.WEST) {
                diffXBlockPos = getDifferenceString(diffBlockPos.getX(), "behind", "away");
            }
        }

        if (diffBlockPos.getY() != 0) {
            diffYBlockPos = getDifferenceString(diffBlockPos.getY(), "up", "down");
        }

        if (diffBlockPos.getZ() != 0) {
            if (dir == Direction.SOUTH) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "away", "behind");
            } else if (dir == Direction.NORTH) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "behind", "away");
            } else if (dir == Direction.EAST) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "right", "left");
            } else if (dir == Direction.WEST) {
                diffZBlockPos = getDifferenceString(diffBlockPos.getZ(), "left", "right");
            }
        }

        String text;
        if (dir == Direction.NORTH || dir == Direction.SOUTH)
            text = String.format("%s  %s  %s", diffZBlockPos, diffYBlockPos, diffXBlockPos);
        else
            text = String.format("%s  %s  %s", diffXBlockPos, diffYBlockPos, diffZBlockPos);
        return text;
    }

    public static String getDifferenceString(int blocks, String key1, String key2) {
        return I18n.translate("minecraft_access.util.position_difference_" + (blocks < 0 ? key1 : key2), Math.abs(blocks));
    }

    public static String narrateCoordinatesOf(BlockPos blockPos) {
        try {
            String posX = narrateNumber(blockPos.getX());
            String posY = narrateNumber(blockPos.getY());
            String posZ = narrateNumber(blockPos.getZ());
            return String.format("%s x %s y %s z", posX, posY, posZ);
        } catch (Exception e) {
            MainClass.errorLog("An error occurred when getting position narration.", e);
            return "";
        }
    }

    /**
     * @param pos  block position (in the client world)
     * @param side if side is provided, then the invoker is ReadCrosshair
     * @return toSpeak, currentQuery
     */
    public static Pair<String, String> narrateBlock(BlockPos pos, String side) {
        MinecraftClient client = MinecraftClient.getInstance();
        if (Objects.isNull(client)) return new Pair<>("", "");
        ClientWorld clientWorld = client.world;
        if (clientWorld == null) return new Pair<>("", "");

        // Since Minecraft uses flyweight pattern for blocks and entities,
        // All same type of blocks share one singleton Block instance,
        // While every block keep their states with a BlockState instance.
        WorldUtils.BlockInfo blockInfo = WorldUtils.getBlockInfo(pos).orElseThrow();
        BlockPos blockPos = blockInfo.pos();
        BlockState blockState = blockInfo.state();
        Block block = blockInfo.type();
        BlockEntity blockEntity = blockInfo.entity();

        // Difference between toSpeak and currentQuery:
        // currentQuery is used for checking condition, toSpeak is actually the one to be spoken.
        // currentQuery is checked to not speak the same block repeatedly, two blocks can have same name.
        String name = block.getName().getString();
        String toSpeak = Strings.isBlank(side) ? name : name + " " + side;
        String currentQuery = name + side;

        // Different special narration (toSpeak) about different type of blocks
        try {
            if (blockEntity != null) {
                // The all signs tag include all types of signs, so it should also work with the hanging signs in 1.20.x
                if (blockState.isIn(BlockTags.ALL_SIGNS)) {
                    toSpeak = getSignInfo((SignBlockEntity) blockEntity, client.player, toSpeak);
                } else if (blockEntity instanceof BeehiveBlockEntity beehiveBlockEntity) {
                    Pair<String, String> beehiveInfo = getBeehiveInfo(beehiveBlockEntity, blockState, toSpeak, currentQuery);
                    toSpeak = beehiveInfo.getLeft();
                    currentQuery = beehiveInfo.getRight();
                }
            }

            if (block instanceof PlantBlock || block instanceof CocoaBlock) {
                Pair<String, String> cropsInfo = getCropsInfo(block, blockState, toSpeak, currentQuery);
                toSpeak = cropsInfo.getLeft();
                currentQuery = cropsInfo.getRight();
            }

            // Check if farmland is wet
            if (block instanceof FarmlandBlock && blockState.get(FarmlandBlock.MOISTURE) == FarmlandBlock.MAX_MOISTURE) {
                toSpeak = I18n.translate("minecraft_access.crop.wet_farmland", toSpeak);
                currentQuery = I18n.translate("minecraft_access.crop.wet_farmland", currentQuery);
            }

            // Speak monster spawner mob type
            if (blockEntity instanceof MobSpawnerBlockEntity spawner) {
                // Will not support non-vanilla custom configured multiple-mob spawner (like generated with command)
                Entity entity = ((MobSpawnerLogicAccessor) spawner.getLogic()).getRenderedEntity();
                // Monster spawners that gotten from creative creating screen is empty.
                String entityName = "Empty";
                if (entity != null) {
                    entityName = entity.getDisplayName().getString();
                }
                toSpeak = entityName + " " + toSpeak;
                currentQuery = entityName + currentQuery;
            }

            // Redstone related
            Pair<String, String> redstoneRelatedInfo = getRedstoneRelatedInfo(clientWorld, blockPos, block, blockState, toSpeak, currentQuery);
            toSpeak = redstoneRelatedInfo.getLeft();
            currentQuery = redstoneRelatedInfo.getRight();

        } catch (Exception e) {
            MainClass.errorLog("An error occurred while adding narration text for special blocks", e);
        }

        return new Pair<>(toSpeak, currentQuery);
    }

    private static String getSignInfo(SignBlockEntity signEntity, ClientPlayerEntity player, String toSpeak) {
        String[] lines = new String[4];

        for (int i = 0; i < 4; i++) {
//            lines[i] = signEntity.getTextOnRow(i, false).getString(); // Pre 1.20.x
            lines[i] = signEntity.getText(signEntity.isPlayerFacingFront(player)).getMessage(i, false).getString();
        }
        String content = String.join(", ", lines);
        return I18n.translate("minecraft_access.read_crosshair.sign_" + (signEntity.isPlayerFacingFront(player) ? "front" : "back") + "_content", toSpeak, content);
    }

    private static @NotNull Pair<String, String> getRedstoneRelatedInfo(ClientWorld world, BlockPos blockPos, Block block, BlockState blockState, String toSpeak, String currentQuery) {
        boolean isEmittingPower = world.isEmittingRedstonePower(blockPos, Direction.DOWN);
        boolean isReceivingPower = world.isReceivingRedstonePower(blockPos);

        if (block instanceof PistonBlock) {
            String facing = blockState.get(PistonBlock.FACING).getName();
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction." + facing));
            currentQuery += "facing " + facing;
            if (isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if ((block instanceof GlowLichenBlock || block instanceof RedstoneLampBlock) && (isReceivingPower || isEmittingPower)) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
            currentQuery += "powered";
//        } else if ((block instanceof RedstoneTorchBlock || block instanceof LeverBlock || block instanceof AbstractButtonBlock) && isEmittingPower) { // pre 1.19.3
        } else if (block instanceof RedstoneWireBlock) {
            Pair<String, String> p = getRedstoneWireInfo(blockState, blockPos, toSpeak, currentQuery);
            toSpeak = p.getLeft();
            currentQuery = p.getRight();
        } else if ((block instanceof RedstoneTorchBlock || block instanceof LeverBlock || block instanceof ButtonBlock) && isEmittingPower) { // From 1.19.3
            toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
            currentQuery += "powered";
        } else if (block instanceof DoorBlock doorBlock && doorBlock.isOpen(blockState)) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.opened", toSpeak);
            currentQuery += "open";
        } else if (block instanceof HopperBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction." + blockState.get(HopperBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(HopperBlock.FACING).getName();
            if (isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.locked", toSpeak);
                currentQuery += "locked";
            }
        } else if (block instanceof ObserverBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction." + blockState.get(ObserverBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(ObserverBlock.FACING).getName();
            if (isEmittingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if (block instanceof DispenserBlock) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.facing", toSpeak, I18n.translate("minecraft_access.direction." + blockState.get(DispenserBlock.FACING).getName()));
            currentQuery += "facing " + blockState.get(DispenserBlock.FACING).getName();
            if (isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
        } else if (block instanceof ComparatorBlock) {
            ComparatorMode mode = blockState.get(ComparatorBlock.MODE);
            Direction facing = blockState.get(ComparatorBlock.FACING);
            String correctFacing = I18n.translate("minecraft_access.direction." + PlayerPositionUtils.getOppositeDirectionKey(facing.getName()).toLowerCase());
            toSpeak = I18n.translate("minecraft_access.read_crosshair.comparator_info", toSpeak, correctFacing, mode);
            if (isReceivingPower) {
                toSpeak = I18n.translate("minecraft_access.read_crosshair.powered", toSpeak);
                currentQuery += "powered";
            }
            currentQuery += "mode:" + mode + " facing:" + correctFacing;
        } else if (block instanceof RepeaterBlock) {
            boolean locked = blockState.get(RepeaterBlock.LOCKED);
            int delay = blockState.get(RepeaterBlock.DELAY);
            Direction facing = blockState.get(ComparatorBlock.FACING);
            String correctFacing = I18n.translate("minecraft_access.direction." + PlayerPositionUtils.getOppositeDirectionKey(facing.getName()).toLowerCase());

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

        return new Pair<>(toSpeak, currentQuery);
    }

    private static @NotNull Pair<String, String> getRedstoneWireInfo(BlockState blockState, BlockPos pos, String toSpeak, String currentQuery) {
        int powerLevel = blockState.get(RedstoneWireBlock.POWER);
        if (powerLevel > 0) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.redstone_wire_power", toSpeak, powerLevel);
            currentQuery += "power level " + powerLevel;
        }

        List<String> connectedDirections = Direction.Type.HORIZONTAL.stream()
                .map(direction -> {
                    String directionName = I18n.translate("minecraft_access.direction." + direction.getName());

                    switch (blockState.get(RedstoneWireBlock.DIRECTION_TO_WIRE_CONNECTION_PROPERTY.get(direction))) {
                        case UP -> {
                            return directionName + " " + I18n.translate("minecraft_access.direction.up");
                        }
                        case SIDE -> {
                            return directionName;
                        }
                        default -> {
                            return null;
                        }
                    }
                })
                .filter(Objects::nonNull)
                .collect(Collectors.toList());

        // Unconnected redstone dust now has all direction block states set to "side" since 20w18a (before 1.16)
        // https://minecraft.wiki/w/Redstone_Dust
        // So here is an additional check to see if the redstone wire is really connected to all directions
        if (connectedDirections.size() == 4) {
            Optional<Boolean> result = WorldUtils.checkAnyOfSurroundingBlocks(pos, THREE_SAMPLE_POSITIONS, IS_REDSTONE_WIRE);
            if (result.isEmpty()) return new Pair<>(toSpeak, currentQuery);
            // return early if we find out that this wire is not connected to all directions
            if (Boolean.FALSE.equals(result.get())) return new Pair<>(toSpeak, currentQuery);
        }

        String directionsToSpeak = String.join(I18n.translate("minecraft_access.other.words_connection"), connectedDirections);
        toSpeak = I18n.translate("minecraft_access.read_crosshair.redstone_wire_connection", toSpeak, directionsToSpeak);
        currentQuery += "connected to " + connectedDirections;

        return new Pair<>(toSpeak, currentQuery);
    }

    private static @NotNull Pair<String, String> getBeehiveInfo(BeehiveBlockEntity blockEntity, BlockState blockState, String toSpeak, String currentQuery) {
        boolean isSmoked = blockEntity.isSmoked();
        int honeyLevel = blockState.get(BeehiveBlock.HONEY_LEVEL);
        Direction facingDirection = blockState.get(BeehiveBlock.FACING);

        if (isSmoked) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.bee_hive_smoked", toSpeak);
            currentQuery += "smoked";
        }

        if (honeyLevel > 0) {
            toSpeak = I18n.translate("minecraft_access.read_crosshair.bee_hive_honey_level", toSpeak, honeyLevel);
            currentQuery += ("honey-level:" + honeyLevel);
        }

        toSpeak = I18n.translate("minecraft_access.read_crosshair.bee_hive_facing", toSpeak, facingDirection.getName());
        currentQuery += ("facing:" + facingDirection.getName());

        return new Pair<>(toSpeak, currentQuery);
    }

    /**
     * Blocks that can be planted and have growing stages (age) and harvestable.<br>
     * Including wheat, carrot, potato, beetroot, nether wart, cocoa bean,
     * torch flower, pitcher crop.<br>
     * Watermelon vein and pumpkin vein are not harvestable so not be included here.
     */
    private static @NotNull Pair<String, String> getCropsInfo(Block block, BlockState blockState, String toSpeak, String currentQuery) {
        int currentAge, maxAge;

        if (block instanceof CropBlock) {
            if (block instanceof BeetrootsBlock) {
                // Beetroots has a different max_age as 3
                currentAge = blockState.get(BeetrootsBlock.AGE);
                maxAge = BeetrootsBlock.BEETROOTS_MAX_AGE;
            } else if (block instanceof TorchflowerBlock) {
                currentAge = blockState.get(TorchflowerBlock.AGE);
                maxAge = 2;
            } else {
                // While wheat, carrots, potatoes has max_age as 7
                currentAge = blockState.get(CropBlock.AGE);
                maxAge = CropBlock.MAX_AGE;
            }
        } else if (block instanceof CocoaBlock) {
            currentAge = blockState.get(CocoaBlock.AGE);
            maxAge = CocoaBlock.MAX_AGE;
        } else if (block instanceof NetherWartBlock) {
            currentAge = blockState.get(NetherWartBlock.AGE);
            // The max_age of NetherWartBlock hasn't been translated, for future compatibility, hard code it.
            maxAge = 3;
        } else if (block instanceof PitcherCropBlock) {
            currentAge = blockState.get(PitcherCropBlock.AGE);
            maxAge = 4;
        } else {
            return new Pair<>(toSpeak, currentQuery);
        }

        String configKey = checkCropRipeLevel(currentAge, maxAge);
        return new Pair<>(I18n.translate(configKey, toSpeak), I18n.translate(configKey, currentQuery));
    }

    /**
     * @return corresponding ripe level text config key
     */
    private static String checkCropRipeLevel(Integer current, int max) {
        if (current >= max) {
            return "minecraft_access.crop.ripe";
        } else if (current < max / 2) {
            return "minecraft_access.crop.seedling";
        } else {
            return "minecraft_access.crop.half_ripe";
        }
    }
}
