package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.SlotAccessor;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
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
    public List<Slot> slots;
    private final HashMap<Slot, String> namesMap;

    public SlotsGroup(String name, List<Slot> slots) {
        this.namesMap = new HashMap<>();
        this.name = name;
        this.slots = Objects.requireNonNullElseGet(slots, ArrayList::new);
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

    public boolean hasSlotAbove(@NotNull Slot slot) {
        return slot.y != this.getFirstSlot().y;
    }

    public boolean hasSlotBelow(@NotNull Slot slot) {
        return slot.y != this.getLastSlot().y;
    }

    public boolean hasSlotLeft(@NotNull Slot slot) {
        return slot.x != this.getFirstSlot().x;
    }

    public boolean hasSlotRight(@NotNull Slot slot) {
        return slot.x != this.getLastSlot().x;
    }

    private static @NotNull List<Slot> getSlotNeighbours(Slot slot, @NotNull List<Slot> allSlots) {
        List<Slot> neighbours = new ArrayList<>();

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

    public static List<SlotsGroup> generateGroupsFromSlots(HandledScreenAccessor screen) {

        List<SlotsGroup> foundGroups = new ArrayList<>();

        if (screen instanceof InventoryScreen inventoryScreen) {
            List<Slot> slots = new ArrayList<>(inventoryScreen.getScreenHandler().slots);

            SlotsGroup hotbar = new SlotsGroup("hotbar", null);
            SlotsGroup playerInventory = new SlotsGroup("inventory", null);
            SlotsGroup playerArmor = new SlotsGroup("armor", null);
            SlotsGroup offHand = new SlotsGroup("off_hand", null);
            for (Slot s : slots) {
                int index = ((SlotAccessor) s).getInventoryIndex();
                if (s.inventory instanceof PlayerInventory && index >= 0 && index <= 8) {
                    hotbar.slots.add(s);
                }
            }
            slots.removeAll(hotbar.slots);

            for (Slot s : slots) {
                int index = ((SlotAccessor) s).getInventoryIndex();
                if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                    playerInventory.slots.add(s);
                }
            }
            slots.removeAll(playerInventory.slots);

            for (Slot s : slots) {
                int index = ((SlotAccessor) s).getInventoryIndex();
                if (s.inventory instanceof PlayerInventory && index >= 36 && index <= 39) {
                    playerArmor.slots.add(s);
                }
            }
            slots.removeAll(playerArmor.slots);

            for (Slot s : slots) {
                int index = ((SlotAccessor) s).getInventoryIndex();
                if (s.inventory instanceof PlayerInventory && index == 40) {
                    offHand.slots.add(s);
                    break;
                }
            }
            slots.removeAll(offHand.slots);

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

            while (slots.size() > 0) {
                SlotsGroup group = new SlotsGroup(getInventoryName(slots.get(0)), getSlotNeighbours(slots.get(0), slots));
                slots.removeAll(group.slots);
                group.nameSlots();
                foundGroups.add(group);
            }
        } else if (screen instanceof CreativeInventoryScreen creativeInventoryScreen) {
            List<Slot> slots = new ArrayList<>(creativeInventoryScreen.getScreenHandler().slots);

            MainClass.infoLog("Selected tab %d size %d".formatted(creativeInventoryScreen.getSelectedTab(), creativeInventoryScreen.getScreenHandler().slots.size()));
            if (creativeInventoryScreen.getSelectedTab() == 11) {
//                MainClass.infoLog("\n\n");
                SlotsGroup deleteItemGroup = new SlotsGroup("Delete Items", null);
                SlotsGroup offHandGroup = new SlotsGroup("Off Hand", null);
                SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
                SlotsGroup armourGroup = new SlotsGroup("Armour", null); //FIXME
                SlotsGroup inventoryGroup = new SlotsGroup("Inventory", null);

                for (Slot s : slots) {
                    if (s.x < 0 || s.y < 0) continue;

                    int index = ((SlotAccessor) s).getInventoryIndex();

                    if (index == 0) {
                        deleteItemGroup.slots.add(s);
                        continue;
                    }

                    if(index >= 5 && index <= 8){
                        armourGroup.slots.add(s);
                        continue;
                    }
                    if(index >= 9 && index<=35){
                        inventoryGroup.slots.add(s);
                        continue;
                    }

                    if (index >= 36 && index <= 44) {
                        hotbarGroup.slots.add(s);
                        continue;
                    }

                    if (index == 45) {
                        offHandGroup.slots.add(s);
                        continue;
                    }

//                    int centreX = s.x + 9;
//                    int centreY = s.y + 9;

//                    int targetX = (int) (MinecraftClient.getInstance().getWindow().getX() + ((screen.getX() + centreX) * MinecraftClient.getInstance().getWindow().getScaleFactor()));
//                    int targetY = (int) (MinecraftClient.getInstance().getWindow().getY() + ((screen.getY() + centreY) * MinecraftClient.getInstance().getWindow().getScaleFactor()));

//                    MainClass.infoLog("Slot index:%d x:%d y:%d InvClass:%s SlotClass:%s".formatted(index, targetX, targetY, s.inventory.getClass().getName(), s.getClass().getName()));
                }
//                MainClass.infoLog("Mouse x:%d y:%d".formatted((int) MinecraftClient.getInstance().mouse.getX(), (int) MinecraftClient.getInstance().mouse.getY()));
//                MainClass.infoLog("\n\n");

                foundGroups.add(armourGroup);
                foundGroups.add(offHandGroup);
                foundGroups.add(inventoryGroup);
                foundGroups.add(hotbarGroup);
                foundGroups.add(deleteItemGroup);
            }
        }

//        while (slots.size() > 0) {
//            SlotsGroup group = new SlotsGroup(getInventoryName(slots.get(0)), getSlotNeighbours(slots.get(0), slots));
//            slots.removeAll(group.slots);
//            group.nameSlots();
//            foundGroups.add(group);
//        }
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
