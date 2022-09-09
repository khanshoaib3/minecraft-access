package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.SlotAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class SlotsGroup {
    public String name;
    public List<GroupItem> groupItems;
    private final HashMap<Slot, String> namesMap;
    public boolean isScrollable = false;

    public SlotsGroup(String name, List<GroupItem> groupItems) {
        this.namesMap = new HashMap<>();
        this.name = name;
        this.groupItems = Objects.requireNonNullElseGet(groupItems, ArrayList::new);
    }

    public void setSlotName(Slot slot, String name) {
        this.namesMap.put(slot, name);
    }

    public String getSlotName(Slot slot) {
        String output = this.namesMap.get(slot);
        return output != null ? output : "";
    }

    public GroupItem getFirstGroupItem() {
        return this.groupItems.get(0);
    }

    public GroupItem getLastGroupItem() {
        return this.groupItems.get(this.groupItems.size() - 1);
    }

    public boolean hasGroupItemAbove(@NotNull GroupItem groupItem) {
        return (groupItem.upGroupItem != null) || (groupItem.y != this.getFirstGroupItem().y);
    }

    public boolean hasGroupItemBelow(@NotNull GroupItem groupItem) {
        return (groupItem.downGroupItem != null) || (groupItem.y != this.getLastGroupItem().y);
    }

    public boolean hasGroupItemLeft(@NotNull GroupItem groupItem) {
        return (groupItem.leftGroupItem != null) || (groupItem.x != this.getFirstGroupItem().x);
    }

    public boolean hasGroupItemRight(@NotNull GroupItem groupItem) {
        return (groupItem.rightGroupItem != null) || (groupItem.x != this.getLastGroupItem().x);
    }

    private static @NotNull List<GroupItem> getSlotNeighbours(Slot slot, @NotNull List<Slot> allSlots) {
        List<GroupItem> neighbours = new ArrayList<>();

        int deltaY = 0;
        while (true) {
            boolean rowHasSlots = false;
            List<Slot> sameY = new ArrayList<>();
            for (Slot s : allSlots) {
                if (s.y == slot.y + deltaY) {
                    sameY.add(s);
                    rowHasSlots = true;
                }
            }
            int deltaX = 0;
            for (Slot s : sameY) {
                if (s.x == slot.x + deltaX && s.y == slot.y + deltaY) {
                    deltaX += 18;
                    neighbours.add(new GroupItem(s));
                }
            }

            if (!rowHasSlots || deltaX == 0) {
                break;
            }
            deltaY += 18;
        }
        return neighbours;
    }

    public static List<SlotsGroup> generateGroupsFromSlots(HandledScreenAccessor screen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();

        if (screen instanceof CreativeInventoryScreen creativeInventoryScreen) {
            List<Slot> slots = new ArrayList<>(creativeInventoryScreen.getScreenHandler().slots);

            if (creativeInventoryScreen.getSelectedTab() == 11) {
                SlotsGroup deleteItemGroup = new SlotsGroup("Delete Items", null);
                SlotsGroup offHandGroup = new SlotsGroup("Off Hand", null);
                SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
                SlotsGroup armourGroup = new SlotsGroup("Armour", null); //FIXME
                SlotsGroup inventoryGroup = new SlotsGroup("Inventory", null);

                for (Slot s : slots) {
                    if (s.x < 0 || s.y < 0) continue;

                    int index = ((SlotAccessor) s).getInventoryIndex();

                    if (index == 0) {
                        deleteItemGroup.groupItems.add(new GroupItem(s));
                        continue;
                    }

                    if (index >= 5 && index <= 8) {
                        armourGroup.groupItems.add(new GroupItem(s));
                        continue;
                    }

                    if (index >= 9 && index <= 35) {
                        inventoryGroup.groupItems.add(new GroupItem(s));
                        continue;
                    }

                    if (index >= 36 && index <= 44) {
                        hotbarGroup.groupItems.add(new GroupItem(s));
                        continue;
                    }

                    if (index == 45) {
                        offHandGroup.groupItems.add(new GroupItem(s));
                    }
                }

                armourGroup.mapTheGroupList(2, true);
                inventoryGroup.mapTheGroupList(9, false);
                hotbarGroup.mapTheGroupList(9, false);

                foundGroups.add(armourGroup);
                foundGroups.add(offHandGroup);
                foundGroups.add(inventoryGroup);
                foundGroups.add(hotbarGroup);
                foundGroups.add(deleteItemGroup);
            } else if (creativeInventoryScreen.getSelectedTab()==5) {
                // Skip if in search item tab
                return foundGroups;
            } else {
                SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
                SlotsGroup tabInventoryGroup = new SlotsGroup("Tab Inventory", null);
                tabInventoryGroup.isScrollable = true;

                for (Slot s : slots) {
                    if (s.x < 0 || s.y < 0) continue;

                    int index = ((SlotAccessor) s).getInventoryIndex();

                    if(index>=0&& index<=8 && s.inventory instanceof PlayerInventory){
                        hotbarGroup.groupItems.add(new GroupItem(s));
                        continue;
                    }

                    if(index>=0&&index<=44){
                        tabInventoryGroup.groupItems.add(new GroupItem(s));
                    }
//                    int centreX = s.x + 9;
//                    int centreY = s.y + 9;

//                    MainClass.infoLog("Slot index:%d x:%d y:%d InvClass:%s SlotClass:%s".formatted(index, centreX, centreY, s.inventory.getClass().getName(), s.getClass().getName()));
                }
//                int mouseX = (int) ((MinecraftClient.getInstance().mouse.getX() - MinecraftClient.getInstance().getWindow().getX()) / MinecraftClient.getInstance().getWindow().getScaleFactor()) - screen.getX();
//                int mouseY = (int) ((MinecraftClient.getInstance().mouse.getY() - MinecraftClient.getInstance().getWindow().getY()) / MinecraftClient.getInstance().getWindow().getScaleFactor()) - screen.getY();
//                MainClass.infoLog("Mouse x:%d y:%d".formatted(mouseX, mouseY));
//                MainClass.infoLog("\n\n");

                tabInventoryGroup.mapTheGroupList(9, false);
                hotbarGroup.mapTheGroupList(9, false);

                foundGroups.add(tabInventoryGroup);
                foundGroups.add(hotbarGroup);
            }
        } else /*if (screen instanceof InventoryScreen) */ {
            List<Slot> slots = new ArrayList<>(screen.getHandler().slots);
            List<Slot> toRemove = new ArrayList<>();

            SlotsGroup hotbar = new SlotsGroup("hotbar", null);
            SlotsGroup playerInventory = new SlotsGroup("inventory", null);
            SlotsGroup playerArmor = new SlotsGroup("armor", null);
            SlotsGroup offHand = new SlotsGroup("off_hand", null);
            for (Slot s : slots) {
                int index = ((SlotAccessor) s).getInventoryIndex();

                if (s.inventory instanceof PlayerInventory && index >= 0 && index <= 8) {
                    hotbar.groupItems.add(new GroupItem(s));
                    toRemove.add(s);
                    continue;
                }
                if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                    playerInventory.groupItems.add(new GroupItem(s));
                    toRemove.add(s);
                    continue;
                }
                if (s.inventory instanceof PlayerInventory && index >= 36 && index <= 39) {
                    playerArmor.groupItems.add(new GroupItem(s));
                    toRemove.add(s);
                    continue;
                }
                if (s.inventory instanceof PlayerInventory && index == 40) {
                    offHand.groupItems.add(new GroupItem(s));
                    toRemove.add(s);
                }
            }

            if (playerArmor.groupItems.size() > 0)
                foundGroups.add(playerArmor);

            if (offHand.groupItems.size() > 0)
                foundGroups.add(offHand);

            if (playerInventory.groupItems.size() > 0)
                foundGroups.add(playerInventory);

            if (playerInventory.groupItems.size() > 0)
                foundGroups.add(hotbar);

            slots.removeAll(toRemove);

            while (slots.size() > 0) {
                SlotsGroup group = new SlotsGroup(getInventoryName(slots.get(0)), getSlotNeighbours(slots.get(0), slots));
                for (GroupItem item : group.groupItems) {
                    slots.remove(item.slot);
                }
                group.nameSlots();
                foundGroups.add(group);
            }
        }
        return foundGroups;
    }

    // Maps the list into 2d form like a matrix, the factor being the no. of columns and transpose is whether to transpose the matrix or not
    private void mapTheGroupList(int factor, boolean transpose) {
        int size = this.groupItems.size();
        for (int i = 0; i < size; i++) {
            int above = i - factor;
            int right = i + 1;
            int below = i + factor;
            int left = i - 1;

            if (above >= 0)
                if (transpose)
                    this.groupItems.get(i).leftGroupItem = this.groupItems.get(above);
                else
                    this.groupItems.get(i).upGroupItem = this.groupItems.get(above);

            if (right < size && right % factor != 0)
                if (transpose)
                    this.groupItems.get(i).downGroupItem = this.groupItems.get(right);
                else
                    this.groupItems.get(i).rightGroupItem = this.groupItems.get(right);

            if (below < size)
                if (transpose)
                    this.groupItems.get(i).rightGroupItem = this.groupItems.get(below);
                else
                    this.groupItems.get(i).downGroupItem = this.groupItems.get(below);

            if (left >= 0 && (left + 1) % factor != 0)
                if (transpose)
                    this.groupItems.get(i).upGroupItem = this.groupItems.get(left);
                else
                    this.groupItems.get(i).leftGroupItem = this.groupItems.get(left);
        }
    }

    private void nameSlots() {
        if (this.groupItems.get(0).slot.inventory instanceof CraftingInventory) {
            int size = (int) Math.round(Math.sqrt(this.groupItems.size()));
            List<String> names = new ArrayList<>();
            for (int row = 1; row <= size; row++) {
                for (int column = 1; column <= size; column++) {
//                    names.add(I18n.translate("narrate.apextended.slot.crafting", row, column));
                    names.add("%dx%d".formatted(row, column));
                }
            }
            for (int i = 0; i < this.groupItems.size(); i++) {
                Slot slot = this.groupItems.get(i).slot;
                String name = names.get(i);
                this.setSlotName(slot, name);
            }
        }
    }

    public static String getInventoryName(Slot slot) {
        if (slot.inventory instanceof CraftingResultInventory) {
            return "crafting_output";
        } else if (slot.inventory instanceof CraftingInventory) {
            return "crafting_input";
        }

        if (slot instanceof FurnaceFuelSlot) {
            return "fuel_input";
        } else if (slot instanceof FurnaceOutputSlot) {
            return "furnace_output";
        }
        return "group";
    }

    public String getName() {
//        return I18n.translate("narrate.apextended.slotGroup." + name);
        return name;
    }
}
