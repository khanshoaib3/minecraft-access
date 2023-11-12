package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Util;
import org.apache.logging.log4j.util.Strings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.regex.Pattern;

/**
 * Mainly add custom keypress handling
 * to simulate screen reader's text speaking behavior when editing text in input fields.
 */
@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
    @Shadow
    private String text;
    @Shadow
    private int selectionStart;
    @Unique
    private String minecraft_access$previousSelectedText = "";
    @Unique
    private String minecraft_access$previousText = "";

    /**
     * Prevents any character input if alt is held down.
     * This logic is for "alt + num key to repeat chat message" function in {@link ChatScreenMixin}
     */
    @Inject(at = @At("HEAD"), method = "charTyped", cancellable = true)
    private void charTyped(char chr, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!Screen.hasAltDown()) return;

        cir.setReturnValue(false);
        cir.cancel();
    }

    @Inject(at = @At("RETURN"), method = "keyPressed")
    private void speakSelectedText(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        TextFieldWidgetAccessor accessor = (TextFieldWidgetAccessor) this;
        if (!accessor.callIsActive()) {
            return;
        }
        String selectedText = accessor.callGetSelectedText();

        if (Strings.isNotBlank(selectedText)) {
            // text selected
            String textToSpeak = I18n.translate("minecraft_access.other.selected", selectedText);
            if (this.minecraft_access$previousSelectedText.length() > selectedText.length()) {
                // part of previous selected text is unselected
                // only speak unselected text
                textToSpeak = I18n.translate("minecraft_access.other.unselected", minecraft_access$getUnselectedText(selectedText));
            }
            MainClass.speakWithNarrator(textToSpeak, true);
        } else {
            // text unselected
            boolean someTextIsErased = this.minecraft_access$previousText.length() > this.text.length();
            // don't speak if no previous selected text
            // don't speak if text is erased since this will cover erasing narration
            if (Strings.isNotBlank(this.minecraft_access$previousSelectedText) && !someTextIsErased) {
                MainClass.speakWithNarrator(I18n.translate("minecraft_access.other.unselected", this.minecraft_access$previousSelectedText), true);
            }
        }

        this.minecraft_access$previousSelectedText = selectedText;
        this.minecraft_access$previousText = this.text;
    }

    @Unique
    private String minecraft_access$getUnselectedText(String selectedText) {
        return this.minecraft_access$previousSelectedText.replaceFirst(Pattern.quote(selectedText), "");
    }

    @Inject(at = @At("HEAD"), method = "eraseCharacters")
    private void speakErasedText(int characterOffset, CallbackInfo ci) {
        int cursorPos = Util.moveCursor(this.text, this.selectionStart, characterOffset);
        int startPos = Math.min(cursorPos, this.selectionStart);
        int endPos = Math.max(cursorPos, this.selectionStart);
        String erasedText = this.text.substring(startPos, endPos);
        if (Strings.isNotBlank(erasedText)) {
            MainClass.speakWithNarrator(erasedText, true);
        }
    }
}
