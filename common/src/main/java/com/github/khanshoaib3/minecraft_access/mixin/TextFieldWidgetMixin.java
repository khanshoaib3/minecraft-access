package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

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
        // is selecting, let the selecting text speaking method do the job instead
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
        MainClass.speakWithNarratorIfNotEmpty(selectedText, true);
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
