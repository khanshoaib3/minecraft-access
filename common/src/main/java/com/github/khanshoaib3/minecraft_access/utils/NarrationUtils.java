package com.github.khanshoaib3.minecraft_access.utils;

import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.ZombieVillagerEntity;
import net.minecraft.entity.passive.*;

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
}
