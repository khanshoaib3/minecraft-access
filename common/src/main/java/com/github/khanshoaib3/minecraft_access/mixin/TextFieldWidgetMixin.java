package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TextFieldWidget.class)
public class TextFieldWidgetMixin {
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

    /**
     * Add custom keypress handling.
     */
    @Inject(at = @At("HEAD"), method = "keyPressed", cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!minecraft_access$handleKeyPress(keyCode)) return;

        // Method executes to here means one of custom keystroke handling above is triggered,
        // so we want to cancel the logic in injected original method,
        // since its logic is also return after one handling triggered.
        cir.setReturnValue(true);
        cir.cancel();
    }

    @Unique
    private boolean minecraft_access$handleKeyPress(int keyCode) {
        return false;
    }
}
