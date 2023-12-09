package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.item.ItemStack;

import java.util.function.Function;

public class SpeakHeldItem {
    private String previousItemName = "";
    private int previousItemCount = 0;
    public static final Function<String, String> HOTBAR_I18N = toSpeak -> I18n.translate("minecraft_access.other.hotbar", toSpeak);
    public static final Function<String, String> EMPTY_SLOT_I18N = toSpeak -> I18n.translate("minecraft_access.inventory_controls.empty_slot", toSpeak);

    public void speakHeldItem(ItemStack currentStack, int heldItemTooltipFade) {
        boolean currentStackIsEmpty = currentStack.isEmpty();
        if (heldItemTooltipFade == 0 && currentStackIsEmpty) {
            // Speak "empty slot" when the selected slot is empty
            speakIfHeldChanged("", 0, SpeakHeldItem.EMPTY_SLOT_I18N);
        }

        if (!currentStackIsEmpty) {
            // Speak held item's name and count
            speakIfHeldChanged(currentStack.getName().getString(), currentStack.getCount(), SpeakHeldItem.HOTBAR_I18N);
        }
    }

    private void speakIfHeldChanged(String itemName, int itemCount, Function<String, String> i18n) {
        boolean nameChanged = !previousItemName.equals(itemName);
        boolean countChanged = previousItemCount != itemCount;
        boolean reportHeldItemsCountWhenChanged = OtherConfigsMap.getInstance().isReportHeldItemsCountWhenChanged();

        if (nameChanged) {
            String itemCountText = itemCount == 0 ? "" : itemCount + " ";
            MainClass.speakWithNarrator(i18n.apply(itemCountText + itemName), true);
        } else if (countChanged && reportHeldItemsCountWhenChanged) {
            MainClass.speakWithNarrator(String.valueOf(itemCount), true);
        }
        previousItemName = itemName;
        previousItemCount = itemCount;
    }
}
