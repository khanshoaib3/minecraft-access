package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.*;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.LoomScreen;
import net.minecraft.client.gui.screen.ingame.StonecutterScreen;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.util.registry.RegistryEntry;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class GroupGenerator {
    public static List<SlotsGroup> generateGroupsFromSlots(HandledScreenAccessor screen) {
        List<SlotsGroup> foundGroups;

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

        // TODO use i18n instead
        SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
        SlotsGroup playerInventoryGroup = new SlotsGroup("Player Inventory", null);
        SlotsGroup armourGroup = new SlotsGroup("Armour", null);
        SlotsGroup offHandGroup = new SlotsGroup("Off Hand", null);
        SlotsGroup itemOutputGroup = new SlotsGroup("Item Output", null);
        SlotsGroup itemInputGroup = new SlotsGroup("Item Input", null);
        SlotsGroup recipesGroup = new SlotsGroup("Recipes", null);
        SlotsGroup fuelInputGroup = new SlotsGroup("Fuel Input", null);
        SlotsGroup craftingOutputGroup = new SlotsGroup("Crafting Output", null);
        SlotsGroup craftingInputGroup = new SlotsGroup("Crafting Input", null);
        SlotsGroup bannerInputGroup = new SlotsGroup("Banner Input", null);
        SlotsGroup dyeInputGroup = new SlotsGroup("Dye Input", null);
        SlotsGroup patternInputGroup = new SlotsGroup("Pattern Input", null);
        SlotsGroup netheriteIngotInputGroup = new SlotsGroup("Netherite Ingot Input", null);
        SlotsGroup blockInventoryGroup = new SlotsGroup("Block Inventory", null);
        SlotsGroup unknownGroup = new SlotsGroup("Unknown", null);

        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getInventoryIndex();

            //<editor-fold desc="Group player inventory slot items">
            if (s.inventory instanceof PlayerInventory && index >= 0 && index <= 8) {
                hotbarGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            if (s.inventory instanceof PlayerInventory && index >= 9 && index <= 35) {
                playerInventoryGroup.slotItems.add(new SlotItem(s));
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
            //</editor-fold>

            //<editor-fold desc="Group furnace(blast furnace, regular furnace and smoker) screen slot items">
            if (screen.getHandler() instanceof AbstractFurnaceScreenHandler && index == 2) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof AbstractFurnaceScreenHandler && index == 1) {
                fuelInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof AbstractFurnaceScreenHandler && index == 0) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group villager trading screen slot items">
            if (screen.getHandler() instanceof MerchantScreenHandler && (index == 0 || index == 1)) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof MerchantScreenHandler && index == 2) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group stone cutter screen slot items">
            if (screen.getHandler() instanceof StonecutterScreenHandler && index == 0) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof StonecutterScreenHandler && index == 1) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group cartography screen slot items">
            if (screen.getHandler() instanceof CartographyTableScreenHandler && (index == 0 || index == 1)) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof CartographyTableScreenHandler && index == 2) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group loom screen slot items">
            if (screen.getHandler() instanceof LoomScreenHandler && index == 0) {
                if (s.inventory.size() == 3) {
                    bannerInputGroup.slotItems.add(new SlotItem(s));
                } else if (s.inventory.size() == 1) {
                    itemOutputGroup.slotItems.add(new SlotItem(s));
                }
                continue;
            }

            if (screen.getHandler() instanceof LoomScreenHandler && index == 1) {
                dyeInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof LoomScreenHandler && index == 2) {
                patternInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group forging screen(smithing and anvil screens) slot items">
            if (screen.getHandler() instanceof ForgingScreenHandler && (index == 0 || index == 1)) {
                if (screen.getHandler() instanceof SmithingScreenHandler && index == 1)
                    netheriteIngotInputGroup.slotItems.add(new SlotItem(s));
                else
                    itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof ForgingScreenHandler && index == 2) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group grind stone screen slot items">
            if (screen.getHandler() instanceof GrindstoneScreenHandler && (index == 0 || index == 1)) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof GrindstoneScreenHandler && index == 2) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group storage container(chests, hopper, dispenser, etc.) inventory slot items">
            if (screen.getHandler() instanceof GenericContainerScreenHandler && s.inventory instanceof SimpleInventory) {
                blockInventoryGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof Generic3x3ContainerScreenHandler && s.inventory instanceof SimpleInventory) {
                blockInventoryGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof HopperScreenHandler && s.inventory instanceof SimpleInventory) {
                blockInventoryGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group crafting related slot items">
            if (s.inventory instanceof CraftingResultInventory && !(s instanceof FurnaceOutputSlot)) {
                itemOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof CraftingInventory) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            unknownGroup.slotItems.add(new SlotItem(s));
        }

        //<editor-fold desc="Group recipe group slot items if any">
        if (screen instanceof StonecutterScreen stonecutterScreen) {
            // Refer to StonecutterScreen.java -->> renderRecipeIcons()
            int x = screen.getX() + 52;
            int y = screen.getY() + 14;
            int scrollOffset = ((StonecutterScreenAccessor) stonecutterScreen).getScrollOffset();

            for (int i = scrollOffset; i < scrollOffset + 12 && i < stonecutterScreen.getScreenHandler().getAvailableRecipeCount(); ++i) {
                int j = i - scrollOffset;
                int k = x + j % 4 * 16;
                int l = j / 4;
                int m = y + l * 18 + 2;

                int realX = k - screen.getX() + 8;
                int realY = m - screen.getY() + 8;
                recipesGroup.slotItems.add(new SlotItem(realX, realY, j));
            }
        }

        if (screen instanceof LoomScreen loomScreen) {
            // TODO add tutorial on wiki
            // Refer to LoomScreen.java -->> drawBackground()
            int i = screen.getX();
            int j = screen.getY();
            if (((LoomScreenAccessor) loomScreen).isCanApplyDyePattern()) {
                int l = i + 60;
                int m = j + 13;
                List<RegistryEntry<BannerPattern>> list = loomScreen.getScreenHandler().getBannerPatterns();
                block0:
                for (int n = 0; n < 4; ++n) {
                    for (int o = 0; o < 4; ++o) {
                        int p = n + ((LoomScreenAccessor) loomScreen).getVisibleTopRow();
                        int q = p * 4 + o;
                        if (q >= list.size()) break block0;
                        int r = l + o * 14;
                        int s = m + n * 14;

                        int realX = r - screen.getX() + 8;
                        int realY = s - screen.getY() + 8;

                        recipesGroup.slotItems.add(new SlotItem(realX, realY, n, o));
                    }
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Add Inventory Groups to foundGroups">
        if (armourGroup.slotItems.size() > 0) {
            foundGroups.add(armourGroup);
        }

        if (offHandGroup.slotItems.size() > 0) {
            foundGroups.add(offHandGroup);
        }

        if (playerInventoryGroup.slotItems.size() > 0) {
            playerInventoryGroup.mapTheGroupList(9);
            foundGroups.add(playerInventoryGroup);
        }

        if (hotbarGroup.slotItems.size() > 0) {
            hotbarGroup.mapTheGroupList(9);
            foundGroups.add(hotbarGroup);
        }

        if (blockInventoryGroup.slotItems.size() > 0) {
            if (screen.getHandler() instanceof Generic3x3ContainerScreenHandler)
                blockInventoryGroup.mapTheGroupList(3);
            else if (screen.getHandler() instanceof GenericContainerScreenHandler)
                blockInventoryGroup.mapTheGroupList(9);
            else if (screen.getHandler() instanceof HopperScreenHandler)
                blockInventoryGroup.mapTheGroupList(5);
            foundGroups.add(blockInventoryGroup);
        }
        //</editor-fold>

        //<editor-fold desc="Add Input Groups to foundGroups">
        if (craftingInputGroup.slotItems.size() > 0) {
            craftingInputGroup.setRowColumnPrefixForSlots();
            foundGroups.add(craftingInputGroup);
        }

        if (itemInputGroup.slotItems.size() > 0) {
            itemInputGroup.mapTheGroupList(4);
            foundGroups.add(itemInputGroup);
        }

        if (fuelInputGroup.slotItems.size() > 0) {
            foundGroups.add(fuelInputGroup);
        }

        if (netheriteIngotInputGroup.slotItems.size() > 0) {
            foundGroups.add(netheriteIngotInputGroup);
        }

        if (bannerInputGroup.slotItems.size() > 0) {
            foundGroups.add(bannerInputGroup);
        }

        if (dyeInputGroup.slotItems.size() > 0) {
            foundGroups.add(dyeInputGroup);
        }

        if (patternInputGroup.slotItems.size() > 0) {
            foundGroups.add(patternInputGroup);
        }
        //</editor-fold>

        //<editor-fold desc="Add Recipe Groups to foundGroups">
        if (recipesGroup.slotItems.size() > 0) {
            recipesGroup.isScrollable = true;
            recipesGroup.mapTheGroupList(4);
            foundGroups.add(recipesGroup);
        }
        //</editor-fold>

        //<editor-fold desc="Add Output Groups to foundGroups">
        if (craftingOutputGroup.slotItems.size() > 0) {
            foundGroups.add(craftingOutputGroup);
        }

        if (itemOutputGroup.slotItems.size() > 0) {
            itemOutputGroup.mapTheGroupList(4);
            foundGroups.add(itemOutputGroup);
        }

        if (unknownGroup.slotItems.size() > 0) {
            foundGroups.add(unknownGroup);
        }
        //</editor-fold>

        return foundGroups;
    }

    private static @NotNull List<SlotsGroup> forPlayerInventoryScreen(@NotNull InventoryScreen inventoryScreen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();

        MainClass.infoLog("Recipe Book:%s Tab:%s".formatted(inventoryScreen.getRecipeBookWidget().isOpen(), ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getCurrentTab().getCategory().name()));

        List<Slot> slots = new ArrayList<>(((HandledScreenAccessor) inventoryScreen).getHandler().slots);

        SlotsGroup hotbarGroup = new SlotsGroup("Hotbar", null);
        SlotsGroup playerInventoryGroup = new SlotsGroup("Player Inventory", null);
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
                playerInventoryGroup.slotItems.add(new SlotItem(s));
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
        playerInventoryGroup.mapTheGroupList(9);
        hotbarGroup.mapTheGroupList(9);
        craftingInputGroup.mapTheGroupList(2);
        craftingInputGroup.setRowColumnPrefixForSlots();

        foundGroups.add(armourGroup);
        foundGroups.add(offHandGroup);
        foundGroups.add(playerInventoryGroup);
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
            SlotsGroup playerInventoryGroup = new SlotsGroup("Player Inventory", null);

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
                    playerInventoryGroup.slotItems.add(new SlotItem(s));
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
            playerInventoryGroup.mapTheGroupList(9);
            hotbarGroup.mapTheGroupList(9);

            foundGroups.add(armourGroup);
            foundGroups.add(offHandGroup);
            foundGroups.add(playerInventoryGroup);
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
