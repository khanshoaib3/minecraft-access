package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import net.minecraft.screen.slot.Slot;

public class SlotItem {
    public SlotItem upSlotItem;
    public SlotItem rightSlotItem;
    public SlotItem downSlotItem;
    public SlotItem leftSlotItem;

    public int x;
    public int y;

    public Slot slot;

    public SlotItem(Slot slot) {
        this.slot = slot;
        this.x = slot.x + 9;
        this.y = slot.y + 9;
        upSlotItem = null;
        rightSlotItem = null;
        downSlotItem = null;
        leftSlotItem = null;
    }

    public SlotItem(int x, int y){
        this.x = x;
        this.y = y;
        upSlotItem = null;
        rightSlotItem = null;
        downSlotItem = null;
        leftSlotItem = null;
        slot = null;
    }
}
