package com.github.khanshoaib3.minecraft_access.utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3d;

/**
 * Translate input objects to narration text.
 */
public class NarrationUtils {
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
}
