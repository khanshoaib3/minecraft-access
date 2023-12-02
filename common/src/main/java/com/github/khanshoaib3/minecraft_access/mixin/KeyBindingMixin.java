package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.utils.system.KeyUtils;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public class KeyBindingMixin {
    @Shadow
    private InputUtil.Key boundKey;

    /**
     * According to {@link InputUtil.Type}, code of non-keyboard keys (mouse keys 0~7 + unknown key -1) are less than 8.
     * Since the {@link KeyBinding} only supports 1 binding per key
     * (meaning the last to register is the winner and be handled for checking isPressed()),
     * our multiple-keybindings-on-one-key usage is not supported.
     */
    @Inject(at = @At("HEAD"), method = "isPressed", cancellable = true)
    private void checkMultipleBindingsOnSameKeyIsPressed(CallbackInfoReturnable<Boolean> cir) {
        // If this keybinding is bound to a non-keyboard key, execute the original method.
        // If this keybinding is bound to a keyboard-key,
        // let's use our key-pressing-check logic to circumvent the limitation.
        int keyCode = this.boundKey.getCode();
        if (keyCode > 7) {
            cir.cancel();
            cir.setReturnValue(KeyUtils.isOnePressed(keyCode));
        }
    }
}
