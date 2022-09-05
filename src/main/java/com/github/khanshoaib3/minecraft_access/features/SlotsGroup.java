package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.mixin.SlotAccessor;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class SlotsGroup {
    public String name;
    public List<Slot> slots;
    private HashMap<Slot, String> namesMap;

    public SlotsGroup(String name, List<Slot> slots) {
        this.namesMap = new HashMap<Slot, String>();
        this.name = name;
        if (slots == null) {
            this.slots = new ArrayList<Slot>();
        } else {
            this.slots = slots;
        }
    }

    public void setSlotName(Slot slot, String name) {
        this.namesMap.put(slot, name);
    }

    public String getSlotName(Slot slot) {
        String output = this.namesMap.get(slot);
        return output != null ? output : "";
    }

    public Slot getFirstSlot() {
        return this.slots.get(0);
    }

    public Slot getLastSlot() {
        return this.slots.get(this.slots.size() - 1);
    }

    public boolean hasSlotAbove(Slot slot) {
        return slot.y != this.getFirstSlot().y;
    }

    public boolean hasSlotBelow(Slot slot) {
        return slot.y != this.getLastSlot().y;
    }

    public boolean hasSlotLeft(Slot slot) {
        return slot.x != this.getFirstSlot().x;
    }

    public boolean hasSlotRight(Slot slot) {
        return slot.x != this.getLastSlot().x;
    }

    private static List<Slot> getSlotNeighbours(Slot slot, List<Slot> allSlots) {
        List<Slot> neighbours = new ArrayList<Slot>();

        int deltaY = 0;
        while (true) {
            boolean rowHasSlots = false;
            List<Slot> sameY = new ArrayList<Slot>();
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
                    neighbours.add(s);
                }
            }

            if (!rowHasSlots || deltaX == 0) {
                break;
            }
            deltaY += 18;
        }
        return neighbours;
    }

    public static List<SlotsGroup> generateGroupsFromSlots(List<Slot> slots) {
        slots = new ArrayList<Slot>(slots);

        List<SlotsGroup> foundGroups = new ArrayList<SlotsGroup>();

        SlotsGroup hotbar = new SlotsGroup("hotbar", null);
        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();
            if (s.inventory instanceof PlayerInventory && index >= 0 && index <= 8) {
                hotbar.slots.add(s);
            }
        }
        slots.removeAll(hotbar.slots);

        SlotsGroup playerInventory = new SlotsGroup("inventory", null);
        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();
            if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                playerInventory.slots.add(s);
            }
        }
        slots.removeAll(playerInventory.slots);

        SlotsGroup playerArmor = new SlotsGroup("armor", null);
        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();
            if (s.inventory instanceof PlayerInventory && index >= 36 && index <= 39) {
                playerArmor.slots.add(s);
            }
        }
        slots.removeAll(playerArmor.slots);

        SlotsGroup offHand = new SlotsGroup("off_hand", null);
        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();
            if (s.inventory instanceof PlayerInventory && index == 40) {
                offHand.slots.add(s);
                break;
            }
        }
        slots.removeAll(offHand.slots);

        while (slots.size() > 0) {
            SlotsGroup group = new SlotsGroup(getInventoryName(slots.get(0)), getSlotNeighbours(slots.get(0), slots));
            slots.removeAll(group.slots);
            group.nameSlots();
            foundGroups.add(group);
        }

        if (playerArmor.slots.size() > 0) {
            foundGroups.add(playerArmor);
        }
        if (offHand.slots.size() > 0) {
            foundGroups.add(offHand);
        }
        if (playerInventory.slots.size() > 0) {
            foundGroups.add(playerInventory);
        }

        foundGroups.add(hotbar);
        return foundGroups;
    }

    private void nameSlots() {
        if (this.slots.get(0).inventory instanceof CraftingInventory) {
            int size = (int) Math.round(Math.sqrt(this.slots.size()));
            List<String> names = new ArrayList<String>();
            for (int row = 1; row <= size; row++) {
                for (int column = 1; column <= size; column++) {
//                    names.add(I18n.translate("narrate.apextended.slot.crafting", row, column));
                    names.add("%dx%d".formatted(row, column));
                }
            }
            for (int i = 0; i < this.slots.size(); i++) {
                Slot slot = this.slots.get(i);
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
