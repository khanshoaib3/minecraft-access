package com.github.khanshoaib3.minecraft_access.fabric.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.mojang.text2speech.NarratorLinux;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(NarratorLinux.class)
public class NarratorLinuxMixin {

    @Inject(at = @At("HEAD"), method = "say", remap = false, cancellable = true)
    public void say(String msg, boolean interrupt, CallbackInfo info) {
        if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized()) {
            if (MinecraftClient.getInstance().options.getNarrator().getValue().shouldNarrateSystem())
                MainClass.getScreenReader().say(msg, interrupt);

            info.cancel();

        }
    }

    // Always set as active even if `flite` library is not installed in system
    @Inject(at = @At("TAIL"), method = "active", cancellable = true, remap = false)
    public void active(CallbackInfoReturnable<Boolean> cir) {
        cir.setReturnValue(true);
    }
}
