package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.item.TooltipData;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;
import java.util.Optional;

@Mixin(DrawContext.class)
public class DrawContextMixin {
    @Unique private static String previousToooltipText = "";

    @Inject(at = @At("HEAD"), method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Lnet/minecraft/text/Text;II)V")
    private void speakDrawnTooltip1(TextRenderer textRenderer, Text text, int x, int y, CallbackInfo ci) {
        if (MinecraftClient.getInstance() == null) return;
        if (MinecraftClient.getInstance().currentScreen == null) return;

        checkAndSpeak(text.getString());
    }

    @Inject(at = @At("HEAD"), method = "drawTooltip(Lnet/minecraft/client/font/TextRenderer;Ljava/util/List;Ljava/util/Optional;II)V")
    private void speakDrawnTooltip2(TextRenderer textRenderer, List<Text> text, Optional<TooltipData> data, int x, int y, CallbackInfo ci) {
        if (MinecraftClient.getInstance() == null) return;
        if (MinecraftClient.getInstance().currentScreen == null) return;

        String toSpeak = "";
        for (Text t: text) {
            toSpeak += t.getString() + "\n";
        }

        checkAndSpeak(toSpeak);
    }

    @Unique
    private static void checkAndSpeak(String toSpeak) {
        if (previousToooltipText.equals(toSpeak)) return;
        if (toSpeak.isBlank()) return;

        previousToooltipText = toSpeak;
        MainClass.getScreenReader().say(previousToooltipText, true);
    }
}
