package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.*;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import com.mojang.text2speech.Narrator;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

/**
 * This features lets us use keyboard in inventory screens. Works with all default minecraft screens.
 * <p>
 * Key binds and combinations: (all key binds are re-mappable(except two keys) from the game's controls menu and these key binds do not interrupt with any other key with same key.)<br>
 * 1) Up Key (default: I) = Focus to slot above.<br>
 * 2) Right Key (default: L) = Focus to slot right.<br>
 * 3) Down Key (default: K) = Focus to slot down.<br>
 * 10) Left Mouse Click Sim Key (default: [) = Simulates left mouse click.<br>
 * 4) Left Key (default: J) = Focus to slot left.<br>
 * 5) Group Key (default: C) = Select next group.<br>
 * 6) Left Shift + Group Key = Select previous group.<br>
 * 7) Switch Tab Key (default: V) = Select next tab (only for creative inventory screen and inventory/crafting screen).<br>
 * 8) Left Shift + Switch Tab Key = Select previous tab (only for creative inventory screen and inventory/crafting screen).<br>
 * 9) Toggle Craftable Key (default: R) = Toggle between show all and show only craftable recipes in inventory/crafting screen.<br>
 * 10) Left Mouse Click Sim Key (default: [) = Simulates left mouse click.<br>
 * 11) Right Mouse Click Sim Key (default: ]) = Simulates right mouse click.<br>
 * 12) T Key (not re-mappable) = Select the search box.<br>
 * 13) Enter Key (not re-mappable) = Deselect the search box.<br>
 * </p>
 */
public class InventoryControls {
    public boolean shouldRun = true;
    private MinecraftClient minecraftClient;

    private HandledScreenAccessor previousScreen = null;
    private HandledScreenAccessor currentScreen = null;

    private List<SlotsGroup> currentSlotsGroupList = null;
    private SlotsGroup currentGroup = null;
    private int currentGroupIndex = 0;
    private SlotItem currentSlotItem = null;
    private String previousSlotText = "";

    private final KeyBinding groupKey;
    private final KeyBinding upKey;
    private final KeyBinding rightKey;
    private final KeyBinding downKey;
    private final KeyBinding leftKey;
    private final KeyBinding leftMouseClickKey;
    private final KeyBinding rightMouseClickKey;
    private final KeyBinding switchTabKey;
    private final KeyBinding toggleCraftableKey;

    private enum FocusDirection {
        UP("above"),
        DOWN("below"),
        LEFT("left"),
        RIGHT("right");

        private final String value;

        FocusDirection(String value) {
            this.value = value;
        }

        String getString() {
            return this.value;
        }
    }

    /**
     * Initializes the key bindings.
     */
    public InventoryControls() {
        String categoryTranslationKey = "Inventory Controls"; //TODO add translation key instead

        groupKey = new KeyBinding(
                "Group Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(groupKey);

        leftMouseClickKey = new KeyBinding(
                "Left mouse click sim key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(leftMouseClickKey);

        rightMouseClickKey = new KeyBinding(
                "Right mouse click sim key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(rightMouseClickKey);

        upKey = new KeyBinding(
                "Up key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(upKey);

        rightKey = new KeyBinding(
                "Right Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(rightKey);

        downKey = new KeyBinding(
                "Down Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(downKey);

        leftKey = new KeyBinding(
                "Left Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(leftKey);

        switchTabKey = new KeyBinding(
                "Switch tabs Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(switchTabKey);

        toggleCraftableKey = new KeyBinding(
                "Toggle craftable Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                categoryTranslationKey
        );
        KeyMappingRegistry.register(toggleCraftableKey);
    }

    public void update() {
        if (!this.shouldRun) return;
        this.minecraftClient = MinecraftClient.getInstance();

        if (minecraftClient == null) return;
        if (minecraftClient.player == null) return;
        if (minecraftClient.currentScreen == null) {
            previousScreen = null;
            currentScreen = null;
            currentGroupIndex = 0;
            currentGroup = null;
            return;
        }
        if (!(minecraftClient.currentScreen instanceof HandledScreen)) return;

        try {
            boolean wasAnyKeyPressed = keyListener();

            currentScreen = (HandledScreenAccessor) minecraftClient.currentScreen;
            currentSlotsGroupList = GroupGenerator.generateGroupsFromSlots(currentScreen);

            // On screen open
            if (previousScreen != currentScreen) {
                previousScreen = currentScreen;
                if (currentScreen instanceof AnvilScreen anvilScreen) {
                    ((AnvilScreenAccessor) anvilScreen).getNameField().setTextFieldFocused(false);
                }
                if (currentScreen instanceof CreativeInventoryScreen creativeInventoryScreen && ((CreativeInventoryScreenAccessor) creativeInventoryScreen).getSearchBox().isActive()) {
                    ((CreativeInventoryScreenAccessor) creativeInventoryScreen).getSearchBox().setTextFieldFocused(false);
                }

                //<editor-fold desc="Always open recipe book on screen open">
                RecipeBookWidget recipeBookWidget = null;
                if (currentScreen instanceof InventoryScreen inventoryScreen) {
                    recipeBookWidget = inventoryScreen.getRecipeBookWidget();
                } else if (currentScreen instanceof CraftingScreen craftingScreen) {
                    recipeBookWidget = craftingScreen.getRecipeBookWidget();
                }

                if (recipeBookWidget != null) {
                    if (!recipeBookWidget.isOpen()) recipeBookWidget.toggleOpen();
                    ((RecipeBookWidgetAccessor) recipeBookWidget).getSearchField().setTextFieldFocused(false);
                }
                //</editor-fold>

                refreshGroupListAndSelectFirstGroup(false); // Interrupt is false to let it speak the screen's name
            }

            if (currentSlotsGroupList.size() == 0) return;

            if (!previousSlotText.equals(getCurrentSlotNarrationText())) {
                previousSlotText = getCurrentSlotNarrationText();
                Narrator.getNarrator().say(previousSlotText, true);
            }

            // Pause the execution of this feature for 250 milliseconds
            if (wasAnyKeyPressed) {
                shouldRun = false;
                TimerTask timerTask = new TimerTask() {
                    @Override
                    public void run() {
                        shouldRun = true;
                    }
                };
                new Timer().schedule(timerTask, 250);
            }
        } catch (Exception e) {
            MainClass.errorLog("\nError encountered in Inventory Controls feature.");
            e.printStackTrace();
        }
    }

    /**
     * Handles the key inputs.
     * @return True if any key is pressed else false.
     */
    private boolean keyListener() {
        boolean isGroupKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(groupKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftClickKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(leftMouseClickKey.getBoundKeyTranslationKey()).getCode());
        boolean isRightCLickKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(rightMouseClickKey.getBoundKeyTranslationKey()).getCode());
        boolean isUpKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(upKey.getBoundKeyTranslationKey()).getCode());
        boolean isRightKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(rightKey.getBoundKeyTranslationKey()).getCode());
        boolean isDownKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(downKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(leftKey.getBoundKeyTranslationKey()).getCode());
        boolean isSwitchTabKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(switchTabKey.getBoundKeyTranslationKey()).getCode());
        boolean isToggleCraftableKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(toggleCraftableKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftShiftPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.left.shift").getCode());
        boolean isEnterPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.enter").getCode())
                || InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.keypad.enter").getCode());
        boolean isTPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.t").getCode());
        boolean disableInputForSearchBox = false;

        //<editor-fold desc="When using a search box">
        if (currentScreen instanceof CreativeInventoryScreen creativeInventoryScreen && ((CreativeInventoryScreenAccessor) creativeInventoryScreen).getSearchBox().isActive()) {
            disableInputForSearchBox = true;
            if (isEnterPressed) {
                MainClass.infoLog("Enter key pressed, deselecting the search box.");
                ((CreativeInventoryScreenAccessor) creativeInventoryScreen).getSearchBox().setTextFieldFocused(false);
                refreshGroupListAndSelectFirstGroup(true);
                return true;
            }
        }

        if (currentScreen instanceof AnvilScreen anvilScreen && ((AnvilScreenAccessor) anvilScreen).getNameField().isActive()) {
            disableInputForSearchBox = true;
            if (isEnterPressed) {
                MainClass.infoLog("Enter key pressed, deselecting the search box.");
                ((AnvilScreenAccessor) anvilScreen).getNameField().setTextFieldFocused(false);
                previousSlotText = "";
                return true;
            }
        }
        if (currentScreen instanceof InventoryScreen inventoryScreen && inventoryScreen.getRecipeBookWidget().isOpen() && ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getSearchField().isActive()) {
            disableInputForSearchBox = true;
            if (isEnterPressed) {
                MainClass.infoLog("Enter key pressed, deselecting the search box.");
                ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getSearchField().setTextFieldFocused(false);
                previousSlotText = "";
                return true;
            }
        }
        if (currentScreen instanceof CraftingScreen craftingScreen && craftingScreen.getRecipeBookWidget().isOpen() && ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getSearchField().isActive()) {
            disableInputForSearchBox = true;
            if (isEnterPressed) {
                MainClass.infoLog("Enter key pressed, deselecting the search box.");
                ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getSearchField().setTextFieldFocused(false);
                previousSlotText = "";
                return true;
            }
        }
        //</editor-fold>

        if (disableInputForSearchBox) return false; // Skip other key inputs if using a search box

        if (isGroupKeyPressed) {
            MainClass.infoLog("Group key pressed");
            changeGroup(!isLeftShiftPressed);
            return true;
        }
        if (isSwitchTabKeyPressed) {
            MainClass.infoLog("Switch Tab key pressed");
            if (currentScreen instanceof InventoryScreen || currentScreen instanceof CraftingScreen)
                changeRecipeTab(!isLeftShiftPressed);
            else if (currentScreen instanceof CreativeInventoryScreen)
                changeCreativeInventoryTab(!isLeftShiftPressed);

            return true;
        }
        if (isLeftClickKeyPressed) {
            MainClass.infoLog("Left click key pressed");
            MouseUtils.leftClick();
            return true;
        }
        if (isRightCLickKeyPressed) {
            MainClass.infoLog("Right click key pressed");
            MouseUtils.rightClick();
            return true;
        }
        if (isUpKeyPressed) {
            MainClass.infoLog("Up key pressed");
            if (isLeftShiftPressed && currentGroup.isScrollable) {
                if (currentScreen instanceof InventoryScreen inventoryScreen && inventoryScreen.getRecipeBookWidget().isOpen()) {
                    int x = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getRecipesArea()).getPrevPageButton().x + 3;
                    int y = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getRecipesArea()).getPrevPageButton().y + 3;
                    int targetX = (int) (minecraftClient.getWindow().getX() + (x * minecraftClient.getWindow().getScaleFactor()));
                    int targetY = (int) (minecraftClient.getWindow().getY() + (y * minecraftClient.getWindow().getScaleFactor()));

                    MouseUtils.moveAndLeftClick(targetX, targetY);
                    moveToSlotItem(currentSlotItem, 100);
                } else if (currentScreen instanceof CraftingScreen craftingScreen && craftingScreen.getRecipeBookWidget().isOpen()) {
                    int x = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getRecipesArea()).getPrevPageButton().x + 3;
                    int y = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getRecipesArea()).getPrevPageButton().y + 3;
                    int targetX = (int) (minecraftClient.getWindow().getX() + (x * minecraftClient.getWindow().getScaleFactor()));
                    int targetY = (int) (minecraftClient.getWindow().getY() + (y * minecraftClient.getWindow().getScaleFactor()));

                    MouseUtils.moveAndLeftClick(targetX, targetY);
                    moveToSlotItem(currentSlotItem, 100);
                } else {
                    MouseUtils.scrollUp();
                }
            } else {
                focusSlotItemAt(FocusDirection.UP);
            }
            return true;
        }
        if (isRightKeyPressed) {
            MainClass.infoLog("Right key pressed");
            focusSlotItemAt(FocusDirection.RIGHT);
            return true;
        }
        if (isDownKeyPressed) {
            MainClass.infoLog("Down key pressed");
            if (isLeftShiftPressed && currentGroup.isScrollable) {
                if (currentScreen instanceof InventoryScreen inventoryScreen && inventoryScreen.getRecipeBookWidget().isOpen()) {
                    int x = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getRecipesArea()).getNextPageButton().x + 3;
                    int y = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getRecipesArea()).getNextPageButton().y + 3;
                    int targetX = (int) (minecraftClient.getWindow().getX() + (x * minecraftClient.getWindow().getScaleFactor()));
                    int targetY = (int) (minecraftClient.getWindow().getY() + (y * minecraftClient.getWindow().getScaleFactor()));

                    MouseUtils.moveAndLeftClick(targetX, targetY);
                    moveToSlotItem(currentSlotItem, 100);
                } else if (currentScreen instanceof CraftingScreen craftingScreen && craftingScreen.getRecipeBookWidget().isOpen()) {
                    int x = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getRecipesArea()).getNextPageButton().x + 3;
                    int y = ((RecipeBookResultsAccessor) ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getRecipesArea()).getNextPageButton().y + 3;
                    int targetX = (int) (minecraftClient.getWindow().getX() + (x * minecraftClient.getWindow().getScaleFactor()));
                    int targetY = (int) (minecraftClient.getWindow().getY() + (y * minecraftClient.getWindow().getScaleFactor()));

                    MouseUtils.moveAndLeftClick(targetX, targetY);
                    moveToSlotItem(currentSlotItem, 100);
                } else {
                    MouseUtils.scrollDown();
                }
            } else {
                focusSlotItemAt(FocusDirection.DOWN);
            }
            return true;
        }
        if (isLeftKeyPressed) {
            MainClass.infoLog("Left key pressed");
            focusSlotItemAt(FocusDirection.LEFT);
            return true;
        }
        if (isTPressed) {
            if (currentScreen instanceof CreativeInventoryScreen creativeInventoryScreen && creativeInventoryScreen.getSelectedTab() == 5) {
                ((CreativeInventoryScreenAccessor) creativeInventoryScreen).getSearchBox().setTextFieldFocused(true);
                MainClass.infoLog("T key pressed, selecting the search box.");
            } else if (currentScreen instanceof AnvilScreen anvilScreen) {
                ((AnvilScreenAccessor) anvilScreen).getNameField().setTextFieldFocused(true);
                MainClass.infoLog("T key pressed, selecting the search box.");
            } else if (currentScreen instanceof InventoryScreen inventoryScreen && inventoryScreen.getRecipeBookWidget().isOpen()) {
                ((RecipeBookWidgetAccessor) inventoryScreen.getRecipeBookWidget()).getSearchField().setTextFieldFocused(true);
                MainClass.infoLog("T key pressed, selecting the search box.");
            } else if (currentScreen instanceof CraftingScreen craftingScreen && craftingScreen.getRecipeBookWidget().isOpen()) {
                ((RecipeBookWidgetAccessor) craftingScreen.getRecipeBookWidget()).getSearchField().setTextFieldFocused(true);
                MainClass.infoLog("T key pressed, selecting the search box.");
            }
            return true;
        }
        if (isToggleCraftableKeyPressed) {
            RecipeBookWidget recipeBookWidget = null;
            if (currentScreen instanceof InventoryScreen inventoryScreen) {
                recipeBookWidget = inventoryScreen.getRecipeBookWidget();
            } else if (currentScreen instanceof CraftingScreen craftingScreen) {
                recipeBookWidget = craftingScreen.getRecipeBookWidget();
            }

            if (recipeBookWidget == null) return false;
            if (!recipeBookWidget.isOpen()) return false;

            ToggleButtonWidget toggleCraftableButton = ((RecipeBookWidgetAccessor) recipeBookWidget).getToggleCraftableButton();

            int x = toggleCraftableButton.x + 8;
            int y = toggleCraftableButton.y + 4;

            int targetX = (int) (minecraftClient.getWindow().getX() + (x * minecraftClient.getWindow().getScaleFactor()));
            int targetY = (int) (minecraftClient.getWindow().getY() + (y * minecraftClient.getWindow().getScaleFactor()));

            MouseUtils.moveAndLeftClick(targetX, targetY);
            moveToSlotItem(currentSlotItem, 100);

            MainClass.infoLog("Recipe toggle key pressed, Showing %s".formatted(toggleCraftableButton.isToggled() ? "all" : "craftable only"));
            Narrator.getNarrator().say("Showing %s".formatted(toggleCraftableButton.isToggled() ? "all" : "craftable only"), true);
            return true;
        }

        return false;
    }

    /**
     * Focuses a slot item in the specified direction if available.
     * @param focusDirection The direction of the slot item to focus.
     */
    private void focusSlotItemAt(FocusDirection focusDirection) {
        if (currentGroup == null) {
            changeGroup(true);
            return;
        }
        if (currentSlotItem == null) {
            focusSlotItem(currentGroup.getFirstGroupItem(), true);
            return;
        }

        SlotItem slotItem = getGroupItemInDirection(focusDirection);
        if (slotItem == null) {
            Narrator.getNarrator().say("No slot %s".formatted(focusDirection.getString()), true); //TODO use i18n instead
            return;
        }

        focusSlotItem(slotItem, true);
    }

    /**
     * Returns the slot item in the specified direction if available.
     * @param focusDirection The direction of the slot item.
     * @return The object of the slot item if found else null.
     */
    private SlotItem getGroupItemInDirection(FocusDirection focusDirection) {
        switch (focusDirection) {
            case UP -> {
                if (!currentGroup.hasSlotItemAbove(currentSlotItem)) return null;

                if (currentSlotItem.upSlotItem != null) return currentSlotItem.upSlotItem;

                for (SlotItem item : currentGroup.slotItems) {
                    if (item.x == currentSlotItem.x && item.y == currentSlotItem.y - 18) {
                        return item;
                    }
                }
            }
            case RIGHT -> {
                if (!currentGroup.hasSlotItemRight(currentSlotItem)) return null;

                if (currentSlotItem.rightSlotItem != null) return currentSlotItem.rightSlotItem;

                for (SlotItem item : currentGroup.slotItems) {
                    if (item.x == currentSlotItem.x + 18 && item.y == currentSlotItem.y) {
                        return item;
                    }
                }
            }
            case DOWN -> {
                if (!currentGroup.hasSlotItemBelow(currentSlotItem)) return null;

                if (currentSlotItem.downSlotItem != null) return currentSlotItem.downSlotItem;

                for (SlotItem item : currentGroup.slotItems) {
                    if (item.x == currentSlotItem.x && item.y == currentSlotItem.y + 18) {
                        return item;
                    }
                }
            }
            case LEFT -> {
                if (!currentGroup.hasSlotItemLeft(currentSlotItem)) return null;

                if (currentSlotItem.leftSlotItem != null) return currentSlotItem.leftSlotItem;

                for (SlotItem item : currentGroup.slotItems) {
                    if (item.x == currentSlotItem.x - 18 && item.y == currentSlotItem.y) {
                        return item;
                    }
                }
            }
        }

        return null;
    }

    /**
     * Focuses at the specified slot item in the current group and narrate its details.
     * @param slotItem The object of the slot item to focus.
     * @param interrupt Whether to stop the narrator from speaking the previous message or not.
     */
    private void focusSlotItem(@NotNull SlotItem slotItem, boolean interrupt) {
        currentSlotItem = slotItem;
        moveToSlotItem(currentSlotItem);
//        MainClass.infoLog("Slot %d/%d selected".formatted(slotItem.slot.getIndex() + 1, currentGroup.slotItems.size()));

        String toSpeak = getCurrentSlotNarrationText();
        if (toSpeak.length() > 0) {
            previousSlotText = toSpeak;
            Narrator.getNarrator().say(toSpeak, interrupt);
        }
    }

    /**
     * Moves the mouse cursor over to the slot item specified.
     * @param slotItem The object of the slot item to move the mouse cursor over to.
     */
    private void moveToSlotItem(SlotItem slotItem) {
        if (slotItem == null) return;

        int x = slotItem.x;
        int y = slotItem.y;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((currentScreen.getX() + x) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((currentScreen.getY() + y) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.move(targetX, targetY);
    }

    /**
     * Moves the mouse cursor over to the specified slot item after some delay.
     * @param slotItem The object of the slot item to move the mouse cursor over to.
     * @param delay The delay in milliseconds.
     */
    @SuppressWarnings("SameParameterValue")
    private void moveToSlotItem(SlotItem slotItem, int delay) {
        if (slotItem == null) return;

        int x = slotItem.x;
        int y = slotItem.y;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((currentScreen.getX() + x) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((currentScreen.getY() + y) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.moveAfterDelay(targetX, targetY, delay);
    }

    /**
     * Get the details of the current slot item to narrate.
     * @return The details of the current slot item.
     */
    private String getCurrentSlotNarrationText() {
        if (currentSlotItem.slot == null) {
            return Objects.requireNonNullElse(currentSlotItem.getNarratableText(), "Unknown");
        }

        if (!currentSlotItem.slot.hasStack()) {
            return "%s Empty Slot".formatted(currentGroup.getSlotPrefix(currentSlotItem.slot)); //TODO use i18n here
        }

        String info = "%s %d".formatted(currentGroup.getSlotPrefix(currentSlotItem.slot), currentSlotItem.slot.getStack().getCount());
        StringBuilder toolTipString = new StringBuilder();
        List<Text> toolTipList = currentSlotItem.slot.getStack().getTooltip(minecraftClient.player, TooltipContext.Default.NORMAL);
        for (Text line : toolTipList) {
            toolTipString.append(line.getString());
        }

        return "%s %s".formatted(info, toolTipString.toString());
    }

    /**
     * Change the selected group.
     * @param goForward Whether to switch to next group or previous group.
     */
    private void changeGroup(boolean goForward) {
        int nextGroupIndex = currentGroupIndex + (goForward ? 1 : -1);
        nextGroupIndex = MathHelper.clamp(nextGroupIndex, 0, currentSlotsGroupList.size() - 1);

        if (nextGroupIndex == currentGroupIndex) return; // Skip if already at the first or last group

        currentGroupIndex = nextGroupIndex;
        currentGroup = currentSlotsGroupList.get(currentGroupIndex);
        MainClass.infoLog("Group(name:%s) %d/%d selected".formatted(currentGroup.getGroupName(), currentGroupIndex + 1, currentSlotsGroupList.size()));
        Narrator.getNarrator().say("%s %s Group selected".formatted(currentGroup.isScrollable ? "Scrollable" : "", currentGroup.getGroupName()), true);

        focusSlotItem(currentGroup.getFirstGroupItem(), false);
    }

    /**
     * Refreshes the current group list and selects the first group.
     * @param interrupt Whether to stop the narrator from speaking the previous message or not.
     */
    private void refreshGroupListAndSelectFirstGroup(boolean interrupt) {
        currentSlotsGroupList = GroupGenerator.generateGroupsFromSlots(currentScreen);
        if (currentSlotsGroupList.size() == 0) return;

        currentGroupIndex = 0;
        currentGroup = currentSlotsGroupList.get(0);
        MainClass.infoLog("Group(name:%s) %d/%d selected".formatted(currentGroup.getGroupName(), currentGroupIndex + 1, currentSlotsGroupList.size()));
        Narrator.getNarrator().say("%s %s Group selected".formatted(currentGroup.isScrollable ? "Scrollable" : "", currentGroup.getGroupName()), interrupt);
        focusSlotItem(currentGroup.getFirstGroupItem(), false);
    }

    /**
     * Changes the selected tab for creative inventory screen.
     * @param goForward Whether to switch to next tab or previous tab.
     */
    private void changeCreativeInventoryTab(boolean goForward) {
        if (!(currentScreen instanceof CreativeInventoryScreen creativeInventoryScreen)) return;

        int nextTabIndex = creativeInventoryScreen.getSelectedTab() + (goForward ? 1 : -1);
        nextTabIndex = MathHelper.clamp(nextTabIndex, 0, 11);

        ((CreativeInventoryScreenAccessor) creativeInventoryScreen).invokeSetSelectedTab(ItemGroup.GROUPS[nextTabIndex]);
        MainClass.infoLog("Tab(name:%s) %d/%d selected".formatted(ItemGroup.GROUPS[nextTabIndex].getName(), nextTabIndex + 1, 12));
        Narrator.getNarrator().say("Tab %s selected".formatted(ItemGroup.GROUPS[nextTabIndex].getDisplayName().getString()), true);

        refreshGroupListAndSelectFirstGroup(false);
    }

    /**
     * Changes the selected tab for inventory/crafting screen.
     * @param goForward Whether to switch to next tab or previous tab.
     */
    private void changeRecipeTab(boolean goForward) {
        RecipeBookWidget recipeBookWidget = null;
        if (currentScreen instanceof InventoryScreen inventoryScreen) {
            recipeBookWidget = inventoryScreen.getRecipeBookWidget();
        } else if (currentScreen instanceof CraftingScreen craftingScreen) {
            recipeBookWidget = craftingScreen.getRecipeBookWidget();
        }

        if (recipeBookWidget == null) return;
        if (!recipeBookWidget.isOpen()) return;

        RecipeBookWidgetAccessor recipeBookWidgetAccessor = (RecipeBookWidgetAccessor) recipeBookWidget;
        int currentTabIndex = recipeBookWidgetAccessor.getTabButtons().indexOf(recipeBookWidgetAccessor.getCurrentTab());

        int nextTabIndex = currentTabIndex + (goForward ? 1 : -1);
        nextTabIndex = MathHelper.clamp(nextTabIndex, 0, recipeBookWidgetAccessor.getTabButtons().size() - 1);

        int y = recipeBookWidgetAccessor.getTabButtons().get(nextTabIndex).y + 9;
        int x = recipeBookWidgetAccessor.getTabButtons().get(nextTabIndex).x + 9;

        int targetX = (int) (minecraftClient.getWindow().getX() + (x * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + (y * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.moveAndLeftClick(targetX, targetY);
        moveToSlotItem(currentSlotItem, 100);

        MainClass.infoLog("Change tab to %s".formatted(recipeBookWidgetAccessor.getCurrentTab().getCategory().name()));
    }
}
