package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.util.SelectionManager;
import net.minecraft.util.Util;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(SelectionManager.class)
public class SelectionManagerMixin {
    @Final
    @Shadow
    private Supplier<String> stringGetter;
    @Shadow
    private int selectionStart;
    @Shadow
    private int selectionEnd;

    @Inject(at = @At("TAIL"), method = "putCursorAtEnd")
    public void speakTextOfSwitchedLine(CallbackInfo ci) {
        MainClass.speakWithNarratorIfNotEmpty(this.stringGetter.get(), true);
    }

    @Inject(at = @At("HEAD"), method = "delete(I)V")
    private void speakErasedText(int offset, CallbackInfo ci) {
        int cursorPos = Util.moveCursor(this.stringGetter.get(), this.selectionStart, offset);
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
        return startPos == endPos ? "" : this.stringGetter.get().substring(startPos, endPos);
    }
}
