package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.apache.logging.log4j.util.Strings;
import org.lwjgl.glfw.GLFW;
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
    @Shadow
    private int selectionEnd;
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

    @Inject(at = @At("HEAD"), method = "keyPressed")
    private void speakCursorHoveredOverText(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        TextFieldWidgetAccessor accessor = (TextFieldWidgetAccessor) this;
        if (!accessor.callIsActive()) {
            return;
        }
        // is selecting, let the selecting text speaking method do the job
        if (Screen.hasShiftDown()) {
            return;
        }

        switch (keyCode) {
            case GLFW.GLFW_KEY_LEFT: {
                if (Screen.hasControlDown()) {
                    String hoveredText = this.getCursorHoveredOverText(accessor.callGetWordSkipPosition(-1));
                    MainClass.speakWithNarratorIfNotEmpty(hoveredText, true);
                } else {
                    String hoveredText = this.getCursorHoveredOverText(accessor.callGetCursorPosWithOffset(-1));
                    MainClass.speakWithNarratorIfNotEmpty(hoveredText, true);
                }
                return;
            }
            case GLFW.GLFW_KEY_RIGHT: {
                if (Screen.hasControlDown()) {
                    String hoveredText = this.getCursorHoveredOverText(accessor.callGetWordSkipPosition(1));
                    MainClass.speakWithNarratorIfNotEmpty(hoveredText, true);
                } else {
                    String hoveredText = this.getCursorHoveredOverText(accessor.callGetCursorPosWithOffset(1));
                    MainClass.speakWithNarratorIfNotEmpty(hoveredText, true);
                }
            }
        }
    }

    /**
     * Speak selected text when more text is selected,
     * speak unselected text when less text is selected.
     */
    @Inject(at = @At("RETURN"), method = "keyPressed")
    private void speakSelectedText(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        TextFieldWidgetAccessor accessor = (TextFieldWidgetAccessor) this;
        if (!accessor.callIsActive()) {
            return;
        }
        String selectedText = accessor.callGetSelectedText();

        if (Strings.isNotEmpty(selectedText)) {
            // text selected
            String textToSpeak = selectedText;
            if (this.minecraft_access$previousSelectedText.length() > selectedText.length()) {
                // part of previous selected text is unselected
                // only speak unselected text
                textToSpeak = minecraft_access$getUnselectedText(selectedText);
            }
            MainClass.speakWithNarrator(textToSpeak, true);
        } else {
            // text unselected
            boolean someTextIsErased = this.minecraft_access$previousText.length() > this.text.length();
            // don't speak if text is erased since this will cover erasing narration
            if (!someTextIsErased) {
                MainClass.speakWithNarratorIfNotEmpty(this.minecraft_access$previousSelectedText, true);
            }
        }

        this.minecraft_access$previousSelectedText = selectedText;
        this.minecraft_access$previousText = this.text;
    }

    @Unique
    private String minecraft_access$getUnselectedText(String selectedText) {
        // Use Pattern.quote() to prevent that unescaped regex characters make String.replaceFirst() throw exp
        return this.minecraft_access$previousSelectedText.replaceFirst(Pattern.quote(selectedText), "");
    }

    @Inject(at = @At("HEAD"), method = "eraseCharacters")
    private void speakErasedText(int characterOffset, CallbackInfo ci) {
        int cursorPos = ((TextFieldWidgetAccessor) this).callGetCursorPosWithOffset(characterOffset);
        // select all text (ctrl+a) will not change the cursor position,
        // if we delete all text then, the erasedText will be a wrong value (one char ahead of cursor)
        // don't speak under this condition
        boolean allTextAreSelected = this.selectionEnd == 0;
        if (!allTextAreSelected) {
            String erasedText = getCursorHoveredOverText(cursorPos);
            MainClass.speakWithNarratorIfNotEmpty(erasedText, true);
        }
    }

    @Unique
    private String getCursorHoveredOverText(int changedCursorPos) {
        int currentCursorPos = this.selectionStart;
        int startPos = Math.min(changedCursorPos, currentCursorPos);
        int endPos = Math.max(changedCursorPos, currentCursorPos);
        return startPos == endPos ? "" : this.text.substring(startPos, endPos);
    }
}
