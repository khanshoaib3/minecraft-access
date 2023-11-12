package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.util.SelectionManager;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Supplier;

@Mixin(SelectionManager.class)
public class SelectionManagerMixin {
    @Final
    @Shadow
    private Supplier<String> stringGetter;

    @Inject(at = @At("TAIL"), method = "putCursorAtEnd")
    public void speakTextOfSwitchedLine(CallbackInfo ci) {
        MainClass.speakWithNarratorIfNotEmpty(this.stringGetter.get(), true);
    }
}
