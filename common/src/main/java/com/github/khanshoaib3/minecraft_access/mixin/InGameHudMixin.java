package com.github.khanshoaib3.minecraft_access.mixin;


import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.item.ItemStack;
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
    private String minecraft_access$previousItemName = "";
    @Unique
    private int minecraft_access$previousItemCount = 0;
    @Unique
    private static final Function<String, String> minecraft_access$HotbarI18N = toSpeak -> I18n.translate("minecraft_access.other.hotbar", toSpeak);
    @Unique
    private static final Function<String, String> minecraft_access$EmptySlotI18N = toSpeak -> I18n.translate("minecraft_access.inventory_controls.empty_slot", toSpeak);

    /**
     * This method is continually invoked by the InGameHud.render(),
     * so we use previousContent to check if the content has changed and need to be narrated.
     */
    @Inject(at = @At("TAIL"), method = "renderHeldItemTooltip")
    public void renderHeldItemTooltipMixin(MatrixStack matrixStack, CallbackInfo callbackInfo) {
        boolean currentStackIsEmpty = this.currentStack.isEmpty();
        if (this.heldItemTooltipFade == 0 && currentStackIsEmpty) {
            // Speak "empty slot" when the selected slot is empty
            minecraft_access$speakIfHeldChanged("", 0, minecraft_access$EmptySlotI18N);
        }

        if (!currentStackIsEmpty) {
            // Speak held item's name and count
            minecraft_access$speakIfHeldChanged(this.currentStack.getName().getString(), this.currentStack.getCount(), minecraft_access$HotbarI18N);
        }
    }

    @Unique
    private void minecraft_access$speakIfHeldChanged(String itemName, int itemCount, Function<String, String> i18n) {
        boolean nameChanged = !this.minecraft_access$previousItemName.equals(itemName);
        boolean countChanged = this.minecraft_access$previousItemCount != itemCount;
        boolean reportHeldItemsCountWhenChanged = OtherConfigsMap.getInstance().isReportHeldItemsCountWhenChanged();

        if (nameChanged) {
            String itemCountText = itemCount == 0 ? "" : itemCount + " ";
            MainClass.speakWithNarrator(i18n.apply(itemCountText + itemName), true);
        } else if (countChanged && reportHeldItemsCountWhenChanged) {
            MainClass.speakWithNarrator(String.valueOf(itemCount), true);
        }
        this.minecraft_access$previousItemName = itemName;
        this.minecraft_access$previousItemCount = itemCount;
    }

    @Inject(at = @At("HEAD"), method = "setOverlayMessage(Lnet/minecraft/text/Text;Z)V")
    public void speakActionbar(Text message, boolean tinted, CallbackInfo ci) {
        if (OtherConfigsMap.getInstance().isActionBarEnabled())
            MainClass.speakWithNarrator(message.getString(), true);
    }
}
