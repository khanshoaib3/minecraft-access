package com.github.khanshoaib3.minecraft_access.mixin;


import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * Narrates/Speaks the currently selected hotbar item's name and the action bar.
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    private int heldItemTooltipFade;

    @Shadow
    private ItemStack currentStack;

    @Inject(at = @At("TAIL"), method = "renderHeldItemTooltip")
    public void renderHeldItemTooltipMixin(DrawContext context, CallbackInfo ci) {
        if (this.heldItemTooltipFade == 38 && !this.currentStack.isEmpty()/*FIXME && Config.get(Config.getHelditemnarratorkey())*/) {
            MutableText mutableText = net.minecraft.text.Text.empty()
                    .append(String.valueOf(this.currentStack.getCount()))
                    .append(this.currentStack.getName())
                    .formatted(this.currentStack.getRarity().formatting);
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.other.hotbar", mutableText.getString()), true);
        }
    }

    @Inject(at = @At("HEAD"), method = "setOverlayMessage(Lnet/minecraft/text/Text;Z)V")
    public void speakActionbar(Text message, boolean tinted, CallbackInfo ci) {
        if (MainClass.config.getConfigMap().getOtherConfigsMap().isActionBarEnabled())
            MainClass.speakWithNarrator(message.getString(), true);
    }
}
