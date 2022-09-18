package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.MerchantScreen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Screen.class)
public class ScreenMixin {
    @Inject(at = @At("HEAD"), method = "addElementNarrations*", cancellable = true)
    private void addElementNarrations(NarrationMessageBuilder builder, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().currentScreen instanceof MerchantScreen) {
            callbackInfo.cancel();
        }
    }
}
