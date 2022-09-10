package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.RecipeBookWidgetAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.SlotAccessor;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.AbstractFurnaceScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.screen.slot.FurnaceFuelSlot;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.TradeOutputSlot;
import net.minecraft.village.MerchantInventory;
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
            foundGroups = forCreativeInventoryScreen(creativeInventoryScreen);
        } else if (screen instanceof InventoryScreen inventoryScreen) {
            foundGroups = forPlayerInventoryScreen(inventoryScreen);
        } else {
            foundGroups = forOtherScreens(screen);
        }
        return foundGroups;
    }

    private static @NotNull List<SlotsGroup> forOtherScreens(@NotNull HandledScreenAccessor screen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();

        List<Slot> slots = new ArrayList<>(screen.getHandler().slots);

        SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
        SlotsGroup inventoryGroup = new SlotsGroup("Inventory", null);
        SlotsGroup armourGroup = new SlotsGroup("Armour", null);
        SlotsGroup offHandGroup = new SlotsGroup("Off Hand", null);
        SlotsGroup craftingOutputGroup = new SlotsGroup("Crafting Output", null);
        SlotsGroup craftingInputGroup = new SlotsGroup("Crafting Input", null);
        SlotsGroup furnaceOutputGroup = new SlotsGroup("Furnace Output", null);
        SlotsGroup furnaceInputGroup = new SlotsGroup("Furnace Input", null);
        SlotsGroup fuelInputGroup = new SlotsGroup("Fuel Input", null);
        SlotsGroup tradeOutputGroup = new SlotsGroup("Trade Output", null);
        SlotsGroup tradeInputGroup = new SlotsGroup("Trade Input", null);

        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();

            if (s.inventory instanceof PlayerInventory && index >= 0 && index <= 8) {
                hotbarGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                inventoryGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index >= 36 && index <= 39) {
                armourGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index == 40) {
                offHandGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof CraftingResultInventory) {
                craftingOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof CraftingInventory) {
                craftingInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s instanceof FurnaceOutputSlot && index == 2) {
                furnaceOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s instanceof FurnaceFuelSlot && index == 1) {
                fuelInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (MinecraftClient.getInstance().currentScreen instanceof AbstractFurnaceScreen<?> && index == 0) {
                furnaceInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof MerchantInventory && !(s instanceof TradeOutputSlot)) {
                tradeInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s instanceof TradeOutputSlot) {
                tradeOutputGroup.slotItems.add(new SlotItem(s));
            }
        }

        if (armourGroup.slotItems.size() > 0)
            foundGroups.add(armourGroup);

        if (offHandGroup.slotItems.size() > 0)
            foundGroups.add(offHandGroup);

        if (inventoryGroup.slotItems.size() > 0) {
            inventoryGroup.mapTheGroupList(9);
            foundGroups.add(inventoryGroup);
        }

        if (hotbarGroup.slotItems.size() > 0) {
            hotbarGroup.mapTheGroupList(9);
            foundGroups.add(hotbarGroup);
        }

        if (craftingInputGroup.slotItems.size() > 0) {
            craftingInputGroup.setRowColumnPrefixForSlots();
            foundGroups.add(craftingInputGroup);
        }

        if (craftingOutputGroup.slotItems.size() > 0) {
            foundGroups.add(craftingOutputGroup);
        }

        if (furnaceInputGroup.slotItems.size() > 0) {
            foundGroups.add(furnaceInputGroup);
        }

        if (fuelInputGroup.slotItems.size() > 0) {
            foundGroups.add(fuelInputGroup);
        }

        if (furnaceOutputGroup.slotItems.size() > 0) {
            foundGroups.add(furnaceOutputGroup);
        }

        if (tradeInputGroup.slotItems.size() > 0) {
            tradeInputGroup.mapTheGroupList(4);
            foundGroups.add(tradeInputGroup);
        }

        if (tradeOutputGroup.slotItems.size() > 0) {
            foundGroups.add(tradeOutputGroup);
        }

        return foundGroups;
    }

    private static @NotNull List<SlotsGroup> forPlayerInventoryScreen(@NotNull InventoryScreen inventoryScreen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();

        MainClass.infoLog("Recipe Book:%s Tab:%s".formatted(inventoryScreen.getRecipeBookWidget().isOpen(), ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getCurrentTab().getCategory().name()));

        List<Slot> slots = new ArrayList<>(((HandledScreenAccessor) inventoryScreen).getHandler().slots);

        SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
        SlotsGroup inventoryGroup = new SlotsGroup("Inventory", null);
        SlotsGroup armourGroup = new SlotsGroup("Armour", null);
        SlotsGroup offHandGroup = new SlotsGroup("Off Hand", null);
        SlotsGroup craftingOutputGroup = new SlotsGroup("Crafting Output", null);
        SlotsGroup craftingInputGroup = new SlotsGroup("Crafting Input", null);

        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();

            if (s.inventory instanceof PlayerInventory && index >= 0 && index <= 8) {
                hotbarGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                inventoryGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index >= 36 && index <= 39) {
                armourGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index == 40) {
                offHandGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof CraftingResultInventory && index == 0) {
                craftingOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof CraftingInventory && index >= 0 && index <= 3) {
                craftingInputGroup.slotItems.add(new SlotItem(s));
            }
        }

        armourGroup.mapTheGroupList(4, true);
        inventoryGroup.mapTheGroupList(9);
        hotbarGroup.mapTheGroupList(9);
        craftingInputGroup.mapTheGroupList(2);
        craftingInputGroup.setRowColumnPrefixForSlots();

        foundGroups.add(armourGroup);
        foundGroups.add(offHandGroup);
        foundGroups.add(inventoryGroup);
        foundGroups.add(hotbarGroup);
        foundGroups.add(craftingInputGroup);
        foundGroups.add(craftingOutputGroup);


        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();
            int centreX = s.x + 9;
            int centreY = s.y + 9;

            MainClass.infoLog("Slot index:%d x:%d y:%d InvClass:%s SlotClass:%s".formatted(index, centreX, centreY, s.inventory.getClass().getName(), s.getClass().getName()));
        }
        int mouseX = (int) ((MinecraftClient.getInstance().mouse.getX() - MinecraftClient.getInstance().getWindow().getX()) / MinecraftClient.getInstance().getWindow().getScaleFactor()) - ((HandledScreenAccessor) inventoryScreen).getX();
        int mouseY = (int) ((MinecraftClient.getInstance().mouse.getY() - MinecraftClient.getInstance().getWindow().getY()) / MinecraftClient.getInstance().getWindow().getScaleFactor()) - ((HandledScreenAccessor) inventoryScreen).getY();
        MainClass.infoLog("Mouse x:%d y:%d".formatted(mouseX, mouseY));
        MainClass.infoLog("\n\n");

        return foundGroups;
    }

    private static @NotNull List<SlotsGroup> forCreativeInventoryScreen(@NotNull CreativeInventoryScreen creativeInventoryScreen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();
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
            inventoryGroup.mapTheGroupList(9);
            hotbarGroup.mapTheGroupList(9);

            foundGroups.add(armourGroup);
            foundGroups.add(offHandGroup);
            foundGroups.add(inventoryGroup);
            foundGroups.add(hotbarGroup);
            foundGroups.add(deleteItemGroup);
        } else {
            SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
            SlotsGroup tabInventoryGroup = new SlotsGroup("Tab Inventory", null);
            tabInventoryGroup.isScrollable = true;

            for (Slot s : slots) {
                if (s.x < 0 || s.y < 0) continue;

                int index = ((SlotAccessor) s).getInventoryIndex();

                if (index >= 0 && index <= 8 && s.inventory instanceof PlayerInventory) {
                    hotbarGroup.slotItems.add(new SlotItem(s));
                    continue;
                }

                if (index >= 0 && index <= 44) {
                    tabInventoryGroup.slotItems.add(new SlotItem(s));
                }
            }

            tabInventoryGroup.mapTheGroupList(9);
            hotbarGroup.mapTheGroupList(9);

            foundGroups.add(tabInventoryGroup);
            foundGroups.add(hotbarGroup);
        }
        return foundGroups;
    }
}
