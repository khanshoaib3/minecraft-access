package com.github.khanshoaib3.minecraft_access.forge.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.NarratorManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NarratorManager.class)
public class NarratorMixin {
    @Inject(at = @At("HEAD"), method = "narrate(Ljava/lang/String;)V", cancellable = true)
    private void narrate(String text, CallbackInfo callbackInfo) {
        if (MainClass.getScreenReader() != null && MainClass.getScreenReader().isInitialized()) {
            if (MinecraftClient.getInstance().options.getNarrator().getValue().shouldNarrateSystem()) {
                MainClass.getScreenReader().say(text, MainClass.interrupt);
            }

            MainClass.interrupt = true;
            callbackInfo.cancel();
        }
    }
}
