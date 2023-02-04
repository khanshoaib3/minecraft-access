package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.FlyingItemEntity;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Auto locks to the eye of ender when used.
 */
@Mixin(EyeOfEnderEntity.class)
public abstract class EyeOfEnderEntityMixin extends Entity implements FlyingItemEntity {
    @Shadow
    private int lifespan;

    public EyeOfEnderEntityMixin(EntityType<?> type, World world) {
        super(type, world);
    }

    @Inject(at = @At("HEAD"), method = "tick")
    private void tick(CallbackInfo callbackInfo) {
        if (this.lifespan != 1) return;
        if (!MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isAutoLockEyeOfEnderEntity())
            return;

        MainClass.infoLog("Auto locking to eye of ender entity");
        MainClass.lockingHandler.lockedOnEntity = this;
        MainClass.lockingHandler.lockedOnBlockEntries = "";
        MainClass.lockingHandler.lockedOnBlock = null;
        MainClass.lockingHandler.isLockedOntoLadder = false;

        MainClass.speakWithNarrator(I18n.translate("minecraft_access.point_of_interest.locking.tracking_eye_of_ender"), true);
    }
}
