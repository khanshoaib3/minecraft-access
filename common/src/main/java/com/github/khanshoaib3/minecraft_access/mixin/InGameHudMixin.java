package com.github.khanshoaib3.minecraft_access.mixin;


import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.function.Function;

/**
 * Narrates/Speaks the currently selected hotbar item's name and the action bar.
 */
@Mixin(InGameHud.class)
public class InGameHudMixin {
    @Shadow
    private int heldItemTooltipFade;

    @Shadow
    private ItemStack currentStack;

    @Unique
    private String minecraft_access$previousContent = "";
    @Unique
    private int minecraft_access$previousStackObjectId = 0;
    @Unique
    private static final Function<String, String> minecraft_access$HotbarI18N = toSpeak -> I18n.translate("minecraft_access.other.hotbar", toSpeak);
    @Unique
    private static final Function<String, String> minecraft_access$EmptySlotI18N = toSpeak -> I18n.translate("minecraft_access.inventory_controls.empty_slot", toSpeak);

    /**
     * This method is continually invoked by the InGameHud.render(),
     * so we use previousContent
     */
    @Inject(at = @At("TAIL"), method = "renderHeldItemTooltip")
    public void renderHeldItemTooltipMixin(DrawContext context, CallbackInfo ci) {
        if (this.heldItemTooltipFade == 0 && this.currentStack.isEmpty()) {
            // Speak "empty slot" when the selected slot is empty
            minecraft_access$speakIfHeldChanged("", minecraft_access$EmptySlotI18N);
        }

        if (this.heldItemTooltipFade > 0 && !this.currentStack.isEmpty()) {
            // Speak held item's name and count
            MutableText mutableText = net.minecraft.text.Text.empty()
                    .append(String.valueOf(this.currentStack.getCount()))
                    .append(" ")
                    .append(this.currentStack.getName())
                    .formatted(this.currentStack.getRarity().formatting);

            String toSpeak = mutableText.getString();
            minecraft_access$speakIfHeldChanged(toSpeak, minecraft_access$HotbarI18N);
        }
    }

    @Unique private void minecraft_access$speakIfHeldChanged(String toSpeak, Function<String, String> i18n) {
        int currentStackHash = System.identityHashCode(this.currentStack);
        boolean stackChanged = this.minecraft_access$previousStackObjectId != currentStackHash;
        boolean contentChanged = !this.minecraft_access$previousContent.equals(toSpeak);

        if (stackChanged || contentChanged) {
            MainClass.speakWithNarrator(i18n.apply(toSpeak), true);
            this.minecraft_access$previousContent = toSpeak;
            this.minecraft_access$previousStackObjectId = currentStackHash;
        }
    }

    @Inject(at = @At("HEAD"), method = "setOverlayMessage(Lnet/minecraft/text/Text;Z)V")
    public void speakActionbar(Text message, boolean tinted, CallbackInfo ci) {
        if (MainClass.config.getConfigMap().getOtherConfigsMap().isActionBarEnabled())
            MainClass.speakWithNarrator(message.getString(), true);
    }
}
