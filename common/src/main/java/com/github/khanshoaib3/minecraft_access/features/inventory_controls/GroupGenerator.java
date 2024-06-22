package com.github.khanshoaib3.minecraft_access.features.inventory_controls;

import com.github.khanshoaib3.minecraft_access.mixin.*;
import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import it.unimi.dsi.fastutil.objects.ObjectLinkedOpenHashSet;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.search.SearchManager;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.ItemGroup;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.screen.*;
import net.minecraft.screen.slot.FurnaceOutputSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.village.TradeOfferList;
import org.jetbrains.annotations.NotNull;

import java.util.*;

public class GroupGenerator {

    public static List<SlotsGroup> generateGroupsFromSlots(HandledScreenAccessor screen) {
        if (screen instanceof CreativeInventoryScreen creativeInventoryScreen) {
            return creativeInventoryGroups(creativeInventoryScreen);
        }

        if (screen instanceof InventoryScreen || screen instanceof CraftingScreen) {
            return inventoryAndCraftingScreensGroups(screen);
        }

        return commonGroups(screen);
    }

    private static @NotNull List<SlotsGroup> commonGroups(@NotNull HandledScreenAccessor screen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();

        List<Slot> slots = new ArrayList<>(screen.getHandler().slots);

        SlotsGroup hotbarGroup = new SlotsGroup("hotbar", null);
        SlotsGroup playerInventoryGroup = new SlotsGroup("player_inventory", null);
        SlotsGroup armourGroup = new SlotsGroup("armour", null);
        SlotsGroup offHandGroup = new SlotsGroup("off_hand", null);
        SlotsGroup itemOutputGroup = new SlotsGroup("item_output", null);
        SlotsGroup itemInputGroup = new SlotsGroup("item_input", null);
        SlotsGroup recipesGroup = new SlotsGroup("recipes", null);
        SlotsGroup tradesGroup = new SlotsGroup("trades", null);
        SlotsGroup fuelInputGroup = new SlotsGroup("fuel_input", null);
        SlotsGroup craftingOutputGroup = new SlotsGroup("crafting_output", null);
        SlotsGroup craftingInputGroup = new SlotsGroup("crafting_input", null);
        SlotsGroup bannerInputGroup = new SlotsGroup("banner_input", null);
        SlotsGroup dyeInputGroup = new SlotsGroup("dye_input", null);
        SlotsGroup patternInputGroup = new SlotsGroup("pattern_input", null);
        SlotsGroup netheriteIngotInputGroup = new SlotsGroup("netherite_ingot_input", null);
        SlotsGroup potionGroup = new SlotsGroup("potion", null);
        SlotsGroup ingredientGroup = new SlotsGroup("ingredient", null);
        SlotsGroup blockInventoryGroup = new SlotsGroup("block_inventory", null);
        SlotsGroup beaconConfirmButtonsGroup = new SlotsGroup("beacon_confirm_buttons", null);
        SlotsGroup primaryBeaconPowersButtonsGroup = new SlotsGroup("primary_beacon_powers_buttons", null);
        SlotsGroup secondaryBeaconPowersButtonsGroup = new SlotsGroup("secondary_beacon_powers_buttons", null);
        SlotsGroup lapisLazuliInputGroup = new SlotsGroup("lapis_lazuli_input", null);
        SlotsGroup enchantsGroup = new SlotsGroup("enchants", null);
        List<SlotItem> unknownSlots = new ArrayList<>(slots.size());

        for (Slot s : slots) {
            int index = ((SlotAccessor) s).getIndex();

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

            //<editor-fold desc="Group brewing stand screen slot items">
            if (screen.getHandler() instanceof BrewingStandScreenHandler && index >= 0 && index <= 2) {
                potionGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof BrewingStandScreenHandler && index == 3) {
                ingredientGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof BrewingStandScreenHandler && index == 4) {
                fuelInputGroup.slotItems.add(new SlotItem(s));
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

            //<editor-fold desc="Group beacon screen slot items">
            if (screen.getHandler() instanceof BeaconScreenHandler && index == 0) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            //<editor-fold desc="Group enchantment screen slot items">
            if (screen.getHandler() instanceof EnchantmentScreenHandler && index == 0) {
                itemInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (screen.getHandler() instanceof EnchantmentScreenHandler && index == 1) {
                lapisLazuliInputGroup.slotItems.add(new SlotItem(s));
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
                craftingOutputGroup.slotItems.add(new SlotItem(s));
                continue;
            }

            if (s.inventory instanceof CraftingInventory) {
                craftingInputGroup.slotItems.add(new SlotItem(s));
                continue;
            }
            //</editor-fold>

            unknownSlots.add(new SlotItem(s));
        }

        //<editor-fold desc="Group recipe group slot items if any">
        if (screen instanceof StonecutterScreen stonecutterScreen) {
            // Refer to StoneCutterScreen.java -->> renderRecipeIcons()
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

        //<editor-fold desc="Group beacon screen buttons (refer to BeaconScreen.java -->> init())">
        if (screen.getHandler() instanceof BeaconScreenHandler) {
            int l;
            int k;
            int j;
            int i;
            beaconConfirmButtonsGroup.slotItems.add(new SlotItem(173, screen.getY() + 107, "Done Button"));
            beaconConfirmButtonsGroup.slotItems.add(new SlotItem(199, screen.getY() + 107, "Cancel Button"));
            for (i = 0; i <= 2; ++i) {
                j = BeaconBlockEntity.EFFECTS_BY_LEVEL.get(i).size();
                k = j * 22 + (j - 1) * 2;
                for (l = 0; l < j; ++l) {
                    primaryBeaconPowersButtonsGroup.slotItems.add(new SlotItem(85 + l * 24 - k / 2, screen.getY() + 22 + i * 25));
                }
            }
            j = BeaconBlockEntity.EFFECTS_BY_LEVEL.get(3).size() + 1;
            k = j * 22 + (j - 1) * 2;
            for (l = 0; l < j - 1; ++l) {
                secondaryBeaconPowersButtonsGroup.slotItems.add(new SlotItem(176 + l * 24 - k / 2, screen.getY() + 47));
            }
            secondaryBeaconPowersButtonsGroup.slotItems.add(new SlotItem(176 + (j - 1) * 24 - k / 2, screen.getY() + 47));
        }
        //</editor-fold>

        //<editor-fold desc="Group enchantment screen enchant buttons (EnchantScreen.java -->> render())">
        if (MinecraftClient.getInstance().player != null && screen.getHandler() instanceof EnchantmentScreenHandler enchantmentScreenHandler) {
            boolean bl = MinecraftClient.getInstance().player.getAbilities().creativeMode;
            int i = enchantmentScreenHandler.getLapisCount();
            for (int j = 0; j < 3; ++j) {
                int k = enchantmentScreenHandler.enchantmentPower[j];
                Enchantment enchantment = Enchantment.byRawId(enchantmentScreenHandler.enchantmentId[j]);
                int l = enchantmentScreenHandler.enchantmentLevel[j];
                int m = j + 1;
                if (enchantment == null) break;
                StringBuilder clueText = new StringBuilder(Text.translatable("container.enchant.clue", enchantment.getName(l)).formatted(Formatting.WHITE).getString());
                if (!bl) {
                    if (MinecraftClient.getInstance().player.experienceLevel < k) {
                        clueText.append(Text.translatable("container.enchant.level.requirement", enchantmentScreenHandler.enchantmentPower[j]).formatted(Formatting.RED).getString());
                    } else {
                        MutableText mutableText = m == 1 ? Text.translatable("container.enchant.lapis.one") : Text.translatable("container.enchant.lapis.many", m);
                        clueText.append(mutableText.formatted(i >= m ? Formatting.GRAY : Formatting.RED).getString());
                        MutableText mutableText2 = m == 1 ? Text.translatable("container.enchant.level.one") : Text.translatable("container.enchant.level.many", m);
                        clueText.append(mutableText2.formatted(Formatting.GRAY).getString());
                    }
                }

                enchantsGroup.slotItems.add(new SlotItem(80, 21 + 19 * j, clueText.toString()));
            }
        }
        //</editor-fold>

        //<editor-fold desc="Group merchant trades">
        if (screen instanceof MerchantScreen merchantScreen) {
            MerchantScreenHandler merchantScreenHandler = merchantScreen.getScreenHandler();
            TradeOfferList tradeOfferList = merchantScreenHandler.getRecipes();
            if (!tradeOfferList.isEmpty()) {
                int i = (merchantScreen.width - screen.getBackgroundWidth()) / 2;
                int j = (merchantScreen.height - screen.getBackgroundHeight()) / 2;
                int k = j + 16 + 1;
                int l = i + 5 + 5;
                for (int z = 0; z < tradeOfferList.size() && z < 7; z++) {
                    int n = k + 11;

                    tradesGroup.slotItems.add(new SlotItem(l - screen.getX(), n - screen.getY(), z));
                    k += 20;
                }
            }
        }
        //</editor-fold>

        //<editor-fold desc="Add Groups to foundGroups, in the order of interactive frequency">

        // Inventory groups first, you always start a process with picking up items.
        //<editor-fold desc="Add Normal Inventory Groups to foundGroups">
        // Container inventory first (you open a container for items inside it)
        if (blockInventoryGroup.slotItems.size() > 0) {
            if (screen.getHandler() instanceof Generic3x3ContainerScreenHandler)
                blockInventoryGroup.mapTheGroupList(3);
            else if (screen.getHandler() instanceof GenericContainerScreenHandler)
                blockInventoryGroup.mapTheGroupList(9);
            else if (screen.getHandler() instanceof HopperScreenHandler)
                blockInventoryGroup.mapTheGroupList(5);
            foundGroups.add(blockInventoryGroup);
        }

        // Then normal inventory (you open inventory screen for items inside it)
        if (playerInventoryGroup.slotItems.size() > 0) {
            playerInventoryGroup.mapTheGroupList(9);
            foundGroups.add(playerInventoryGroup);
        }

        // Finally the hotbar (you may want to put picked items on the hotbar (num-key hotkeys are fast, yup))
        if (hotbarGroup.slotItems.size() > 0) {
            hotbarGroup.mapTheGroupList(9);
            foundGroups.add(hotbarGroup);
        }
        //</editor-fold>

        // Then the input and output groups, you may want to put picked items into the input slots and get the result from output slots.
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

        if (ingredientGroup.slotItems.size() > 0) {
            foundGroups.add(ingredientGroup);
        }

        if (potionGroup.slotItems.size() > 0) {
            potionGroup.mapTheGroupList(3);
            foundGroups.add(potionGroup);
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

        if (lapisLazuliInputGroup.slotItems.size() > 0) {
            foundGroups.add(lapisLazuliInputGroup);
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

        // Adjacency is used here instead of slot.inventory because some mods have non-adjacent slots in the same
        // inventory and adjacent slots in different inventories
        List<List<SlotItem>> unknownGroups = new ArrayList<>(unknownSlots.size());
        for (SlotItem slot : unknownSlots) {
            unknownGroups.stream()
                    .filter(group ->
                            group.stream()
                                    .anyMatch(groupSlot ->
                                            groupSlot.x == slot.x && (groupSlot.y == slot.y + 18 || groupSlot.y == slot.y - 18)
                                            || groupSlot.y == slot.y && (groupSlot.x == slot.x + 18 || groupSlot.x == slot.x - 18)
                                    )
                    )
                    .findFirst()
                    .or(() -> {
                        List<SlotItem> group = new ArrayList<>(unknownSlots.size());
                        unknownGroups.add(group);
                        return Optional.of(group);
                    })
                    .get()
                    .add(slot);
        }
        Map<String, Byte> usedNames = new HashMap<>(unknownGroups.size());
        for (List<SlotItem> group : unknownGroups) {
            if (group.stream()
                    .map(slot -> slot.slot.getClass().getSimpleName())
                    .distinct()
                    .limit(2)
                    .count() == 1) {
                String groupName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, group.get(0).slot.getClass().getSimpleName());
                if (usedNames.containsKey(groupName)) {
                    byte n = (byte) (usedNames.get(groupName) + 1);
                    usedNames.put(groupName, n);
                    groupName += String.format("_%d", n);
                } else {
                    usedNames.put(groupName, (byte) 1);
                }
                foundGroups.add(new SlotsGroup(groupName, group));
            } else {
                foundGroups.add(new SlotsGroup("unknown_group", group));
            }
        }
        //</editor-fold>

        // Then the non-item-related groups you want to interact with (after you put items into input slots, enchant for example).
        //<editor-fold desc="Add Screen Specific Groups">
        if (recipesGroup.slotItems.size() > 0) {
            recipesGroup.isScrollable = true;
            recipesGroup.mapTheGroupList(4);
            foundGroups.add(recipesGroup);
        }

        if (beaconConfirmButtonsGroup.slotItems.size() > 0) {
            beaconConfirmButtonsGroup.mapTheGroupList(2);
            foundGroups.add(beaconConfirmButtonsGroup);
        }

        if (primaryBeaconPowersButtonsGroup.slotItems.size() > 0) {
            primaryBeaconPowersButtonsGroup.mapTheGroupList(2);
            foundGroups.add(primaryBeaconPowersButtonsGroup);
        }

        if (secondaryBeaconPowersButtonsGroup.slotItems.size() > 0) {
            secondaryBeaconPowersButtonsGroup.mapTheGroupList(2);
            foundGroups.add(secondaryBeaconPowersButtonsGroup);
        }

        if (enchantsGroup.slotItems.size() > 0) {
            enchantsGroup.mapTheGroupList(3, true);
            foundGroups.add(enchantsGroup);
        }

        if (tradesGroup.slotItems.size() > 0) {
            tradesGroup.isScrollable = true;
            tradesGroup.mapTheGroupList(7, true);
            foundGroups.add(tradesGroup);
        }
        //</editor-fold>

        // Finally, the armour groups, low interactive frequency, will only show in inventory screen.
        //<editor-fold desc="Add Armour Inventory Groups">
        if (armourGroup.slotItems.size() > 0) {
            foundGroups.add(armourGroup);
        }

        if (offHandGroup.slotItems.size() > 0) {
            foundGroups.add(offHandGroup);
        }
        //</editor-fold>
        //</editor-fold>

        return foundGroups;
    }

    private static @NotNull List<SlotsGroup> inventoryAndCraftingScreensGroups(@NotNull HandledScreenAccessor screen) {
        List<SlotsGroup> foundGroups = commonGroups(screen);
        RecipeBookWidget recipeBookWidget = null;
        if (screen instanceof InventoryScreen inventoryScreen) {
            recipeBookWidget = inventoryScreen.getRecipeBookWidget();
        } else if (screen instanceof CraftingScreen craftingScreen) {
            recipeBookWidget = craftingScreen.getRecipeBookWidget();
        }

        if (recipeBookWidget == null || !recipeBookWidget.isOpen()) {
            return foundGroups;
        }

        RecipeBookWidgetAccessor recipeBookWidgetAccessor = (RecipeBookWidgetAccessor) recipeBookWidget;

        SlotsGroup recipesGroup = new SlotsGroup("recipes", null);
        List<AnimatedResultButton> slots = ((RecipeBookResultsAccessor) recipeBookWidgetAccessor.getRecipesArea()).getResultButtons();

        //<editor-fold desc="Get the recipe list (refer to RecipeBookWidget.java -->> refreshResults())">
        List<RecipeResultCollection> list = recipeBookWidgetAccessor.getRecipeBook().getResultsForGroup(recipeBookWidgetAccessor.getCurrentTab().getCategory());
        list.forEach(resultCollection -> resultCollection.computeCraftables(recipeBookWidgetAccessor.getRecipeFinder(), recipeBookWidgetAccessor.getCraftingScreenHandler().getCraftingWidth(), recipeBookWidgetAccessor.getCraftingScreenHandler().getCraftingHeight(), recipeBookWidgetAccessor.getRecipeBook()));
        ArrayList<RecipeResultCollection> finalRecipeSearchResultList = Lists.newArrayList(list);
        finalRecipeSearchResultList.removeIf(resultCollection -> !resultCollection.isInitialized());
        finalRecipeSearchResultList.removeIf(resultCollection -> !resultCollection.hasFittingRecipes());
        String string = recipeBookWidgetAccessor.getSearchField().getText();
        if (!string.isEmpty()) {
            ObjectLinkedOpenHashSet<RecipeResultCollection> objectSet = new ObjectLinkedOpenHashSet<>(MinecraftClient.getInstance().getSearchProvider(SearchManager.RECIPE_OUTPUT).findAll(string.toLowerCase(Locale.ROOT)));
            finalRecipeSearchResultList.removeIf(recipeResultCollection -> !objectSet.contains(recipeResultCollection));
        }
        if (recipeBookWidgetAccessor.getRecipeBook().isFilteringCraftable(recipeBookWidgetAccessor.getCraftingScreenHandler())) {
            finalRecipeSearchResultList.removeIf(resultCollection -> !resultCollection.hasCraftableRecipes());
        }
        //</editor-fold>

        for (int i = 0; i < slots.size() && i < finalRecipeSearchResultList.size(); i++) {
            AnimatedResultButton animatedResultButton = slots.get(i);
            int realX = animatedResultButton.getX() - screen.getX() + 10;
            int realY = animatedResultButton.getY() - screen.getY() + 10;
            recipesGroup.slotItems.add(new SlotItem(realX, realY));
        }

        if (recipesGroup.slotItems.size() > 0) {
            recipesGroup.isScrollable = true;
            recipesGroup.mapTheGroupList(5);

            // Put recipe book group behind the crafting output group,
            // so user can easily access crafting output after clicking on recipe book.
            if (screen instanceof CraftingScreen) {
                // player_inventory, hotbar, crafting_input, crafting_output <put here>
                foundGroups.add(recipesGroup);
            } else {
                // screen instanceof InventoryScreen
                // player_inventory, hotbar, crafting_input, crafting_output, <put here>, armour, off_hand
                foundGroups.add(foundGroups.size() - 2, recipesGroup);
            }
        }

        return foundGroups;
    }

    private static @NotNull List<SlotsGroup> creativeInventoryGroups(@NotNull CreativeInventoryScreen creativeInventoryScreen) {
        List<SlotsGroup> foundGroups = new ArrayList<>();
        List<Slot> slots = new ArrayList<>(creativeInventoryScreen.getScreenHandler().slots);
        if (CreativeInventoryScreenAccessor.getSelectedTab().getType() == ItemGroup.Type.INVENTORY) {
            SlotsGroup deleteItemGroup = new SlotsGroup("delete_items", null);
            SlotsGroup offHandGroup = new SlotsGroup("off_hand", null);
            SlotsGroup hotbarGroup = new SlotsGroup("hotbar", null);
            SlotsGroup armourGroup = new SlotsGroup("armour", null);
            SlotsGroup playerInventoryGroup = new SlotsGroup("player_inventory", null);

            for (Slot s : slots) {
                if (s.x < 0 || s.y < 0) continue;

                int index = ((SlotAccessor) s).getIndex();

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
            SlotsGroup hotbarGroup = new SlotsGroup("hotbar", null);
            SlotsGroup tabInventoryGroup = new SlotsGroup("tab_inventory", null);
            tabInventoryGroup.isScrollable = true;

            for (Slot s : slots) {
                if (s.x < 0 || s.y < 0) continue;

                int index = ((SlotAccessor) s).getIndex();

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
