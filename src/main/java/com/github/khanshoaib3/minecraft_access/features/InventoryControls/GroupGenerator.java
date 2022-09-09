package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.SlotAccessor;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GroupGenerator {
    private static @NotNull List<SlotItem> getSlotNeighbours(Slot slot, @NotNull List<Slot> allSlots) {
        List<SlotItem> neighbours = new ArrayList<>();

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
                    neighbours.add(new SlotItem(s));
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
                        deleteItemGroup.slotItems.add(new SlotItem(s));
                        continue;
                    }

                    if (index >= 5 && index <= 8) {
                        armourGroup.slotItems.add(new SlotItem(s));
                        continue;
                    }

                    if (index >= 9 && index <= 35) {
                        inventoryGroup.slotItems.add(new SlotItem(s));
                        continue;
                    }

                    if (index >= 36 && index <= 44) {
                        hotbarGroup.slotItems.add(new SlotItem(s));
                        continue;
                    }

                    if (index == 45) {
                        offHandGroup.slotItems.add(new SlotItem(s));
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
                        hotbarGroup.slotItems.add(new SlotItem(s));
                        continue;
                    }

                    if(index>=0&&index<=44){
                        tabInventoryGroup.slotItems.add(new SlotItem(s));
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
                    hotbar.slotItems.add(new SlotItem(s));
                    toRemove.add(s);
                    continue;
                }
                if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                    playerInventory.slotItems.add(new SlotItem(s));
                    toRemove.add(s);
                    continue;
                }
                if (s.inventory instanceof PlayerInventory && index >= 36 && index <= 39) {
                    playerArmor.slotItems.add(new SlotItem(s));
                    toRemove.add(s);
                    continue;
                }
                if (s.inventory instanceof PlayerInventory && index == 40) {
                    offHand.slotItems.add(new SlotItem(s));
                    toRemove.add(s);
                }
            }

            if (playerArmor.slotItems.size() > 0)
                foundGroups.add(playerArmor);

            if (offHand.slotItems.size() > 0)
                foundGroups.add(offHand);

            if (playerInventory.slotItems.size() > 0)
                foundGroups.add(playerInventory);

            if (playerInventory.slotItems.size() > 0)
                foundGroups.add(hotbar);

            slots.removeAll(toRemove);

            while (slots.size() > 0) {
                SlotsGroup group = new SlotsGroup(getInventoryName(slots.get(0)), getSlotNeighbours(slots.get(0), slots));
                for (SlotItem item : group.slotItems) {
                    slots.remove(item.slot);
                }
                group.setRowColumnPrefixForSlots();
                foundGroups.add(group);
            }
        }
        return foundGroups;
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
}
