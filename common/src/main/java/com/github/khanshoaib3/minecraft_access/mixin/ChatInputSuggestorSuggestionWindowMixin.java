package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.suggestion.Suggestion;
import net.minecraft.client.gui.screen.ChatInputSuggestor;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.ingame.AbstractCommandBlockScreen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

/**
 * Since text modifying narrations are suppressed in {@link ScreenNarratorMixin},
 * manually speak (command) suggestions (in {@link AbstractCommandBlockScreen} and {@link ChatScreen}).
 */
@Mixin(ChatInputSuggestor.SuggestionWindow.class)
public class ChatInputSuggestorSuggestionWindowMixin {
    @Shadow
    private int lastNarrationIndex;
    @Shadow
    private int selection;
    @Shadow
    @Final
    private List<Suggestion> suggestions;

    /**
     * Simplify the narration of the command suggestions.
     */
    @Inject(at = @At("HEAD"), method = "getNarration", cancellable = true)
    private void speakSuggestion(CallbackInfoReturnable<Text> cir) {
        // Don't know why they update this value here
        this.lastNarrationIndex = this.selection;

        Suggestion suggestion = this.suggestions.get(this.selection);
        Message message = suggestion.getTooltip();
        String textToSpeak = "%dx%d %s".formatted(this.selection + 1, this.suggestions.size(), suggestion.getText());

        if (message != null) {
            textToSpeak = I18n.translate("minecraft_access.other.selected", textToSpeak + " " + message.getString());
        } else {
            textToSpeak = I18n.translate("minecraft_access.other.selected", textToSpeak);
        }
        cir.setReturnValue(Text.of(textToSpeak));
        cir.cancel();
    }

    @Inject(at = @At("HEAD"), method = "complete")
    private void speakCompletion(CallbackInfo ci) {
        String selected = this.suggestions.get(this.selection).getText();
        MainClass.speakWithNarratorIfNotEmpty(selected, true);
    }
}
