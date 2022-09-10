package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;

import java.util.List;

public class SlotItem {
    public SlotItem upSlotItem;
    public SlotItem rightSlotItem;
    public SlotItem downSlotItem;
    public SlotItem leftSlotItem;

    public int x;
    public int y;

    public Slot slot;
    public String narratableText;

    public SlotItem(Slot slot) {
        this.slot = slot;
        this.x = slot.x + 9;
        this.y = slot.y + 9;
        upSlotItem = null;
        rightSlotItem = null;
        downSlotItem = null;
        leftSlotItem = null;
        narratableText = null;
    }

    public SlotItem(int x, int y, ItemStack itemStack) {
        this.x = x;
        this.y = y;
        if (MinecraftClient.getInstance().currentScreen != null) {
            List<Text> toolTip = MinecraftClient.getInstance().currentScreen.getTooltipFromItem(itemStack);
            StringBuilder toolTipString = new StringBuilder();
            for (Text text: toolTip) {
                toolTipString.append(text.getString()).append(",");
            }

            narratableText = "%s %s".formatted(itemStack.getCount(), toolTipString);
        } else {
            narratableText = "%s %s".formatted(itemStack.getCount(), itemStack.getName().getString());
        }
        upSlotItem = null;
        rightSlotItem = null;
        downSlotItem = null;
        leftSlotItem = null;
        slot = null;
    }

}
