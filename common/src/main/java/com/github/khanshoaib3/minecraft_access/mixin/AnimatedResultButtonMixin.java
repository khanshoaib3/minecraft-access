package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AnimatedResultButton.class)
public class AnimatedResultButtonMixin {
//    @Inject(at = @At("HEAD"), method = "appendNarrations", cancellable = true) // Pre 1.19.3
    @Inject(at = @At("HEAD"), method = "appendClickableNarrations", cancellable = true) // From 1.19.3
    private void appendNarrations(NarrationMessageBuilder builder, CallbackInfo callbackInfo) {
        //TODO re-check if it still works
        ItemStack itemStack = ((AnimatedResultButtonAccessor) this).callGetResults().get(((AnimatedResultButtonAccessor) this).getCurrentResultIndex()).getOutput();
        String toSpeak = "%s %d %s".formatted(((AnimatedResultButtonAccessor) this).getResultCollection().hasCraftableRecipes() ? "Craftable" : "Not craftable", itemStack.getCount(), itemStack.getName().getString());

        MainClass.speakWithNarrator(toSpeak, true);

        callbackInfo.cancel();
    }
}
