package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.mojang.text2speech.NarratorWindows;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Environment(EnvType.CLIENT)
@Mixin(NarratorWindows.class)
public class NarratorWindowsMixin {

    @Inject(at = @At("HEAD"), method = "say", remap = false, cancellable = true)
    public void say(String msg, boolean interrupt, CallbackInfo info) {
        if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized()) {
            if (MinecraftClient.getInstance().options.getNarrator().getValue().shouldNarrateSystem())
                MainClass.getScreenReader().say(msg, interrupt);

            info.cancel();
        }
    }
}
