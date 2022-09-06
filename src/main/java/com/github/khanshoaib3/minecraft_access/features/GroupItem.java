package com.github.khanshoaib3.minecraft_access.features;

import net.minecraft.screen.slot.Slot;

public class GroupItem {
    public GroupItem upGroupItem;
    public GroupItem rightGroupItem;
    public GroupItem downGroupItem;
    public GroupItem leftGroupItem;

    public int x;
    public int y;

    public Slot slot;

    public GroupItem(Slot slot) {
        this.slot = slot;
        this.x = slot.x + 9;
        this.y = slot.y + 9;
        upGroupItem = null;
        rightGroupItem = null;
        downGroupItem = null;
        leftGroupItem = null;
    }

    public GroupItem(int x, int y, GroupItem upGroupItem, GroupItem rightGroupItem, GroupItem downGroupItem, GroupItem leftGroupItem){
        this.x = x;
        this.y = y;
        this.upGroupItem = upGroupItem;
        this.rightGroupItem = rightGroupItem;
        this.downGroupItem = downGroupItem;
        this.leftGroupItem = leftGroupItem;
    }
}
