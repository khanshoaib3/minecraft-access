package com.github.khanshoaib3.minecraft_access.features.InventoryControls;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.*;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import com.mojang.text2speech.Narrator;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.*;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.Pair;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

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

    private final KeyBinding leftKey;
    private final KeyBinding rightKey;
    private final KeyBinding upKey;
    private final KeyBinding downKey;
    private final KeyBinding groupKey;
    private final KeyBinding leftMouseClickKey;
    private final KeyBinding rightMouseClickKey;
    private final KeyBinding switchTabKey;
    private final KeyBinding recipeToggleKey;

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

    public InventoryControls() {
        String categoryTranslationKey = "Inventory Controls"; //TODO add translation key instead

        groupKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "GROUP_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                categoryTranslationKey
        ));

        leftMouseClickKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "CLICK_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                categoryTranslationKey
        ));

        rightMouseClickKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "RIGHT_CLICK_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                categoryTranslationKey
        ));

        upKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "UP_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                categoryTranslationKey
        ));

        rightKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "RIGHT_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                categoryTranslationKey
        ));

        downKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "DOWN_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                categoryTranslationKey
        ));

        leftKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "LEFT_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                categoryTranslationKey
        ));

        switchTabKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Switch Tab Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_V,
                categoryTranslationKey
        ));

        recipeToggleKey = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Toggle Recipe Book Key", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_R,
                categoryTranslationKey
        ));
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

    private boolean keyListener() {
        boolean isGroupKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(groupKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftClickKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(leftMouseClickKey.getBoundKeyTranslationKey()).getCode());
        boolean isRightCLickKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(rightMouseClickKey.getBoundKeyTranslationKey()).getCode());
        boolean isUpKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(upKey.getBoundKeyTranslationKey()).getCode());
        boolean isRightKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(rightKey.getBoundKeyTranslationKey()).getCode());
        boolean isDownKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(downKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(leftKey.getBoundKeyTranslationKey()).getCode());
        boolean isSwitchTabKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(switchTabKey.getBoundKeyTranslationKey()).getCode());
        boolean isRecipeToggleCraftableKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(recipeToggleKey.getBoundKeyTranslationKey()).getCode());
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
                focusSlotAt(FocusDirection.UP);
            }
            return true;
        }
        if (isRightKeyPressed) {
            MainClass.infoLog("Right key pressed");
            focusSlotAt(FocusDirection.RIGHT);
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
            }else {
                focusSlotAt(FocusDirection.DOWN);
            }
            return true;
        }
        if (isLeftKeyPressed) {
            MainClass.infoLog("Left key pressed");
            focusSlotAt(FocusDirection.LEFT);
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
        if (isRecipeToggleCraftableKeyPressed) {
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

    private void changeCreativeInventoryTab(boolean goForward) {
        if (!(currentScreen instanceof CreativeInventoryScreen creativeInventoryScreen)) return;

        int nextTabIndex = creativeInventoryScreen.getSelectedTab() + (goForward ? 1 : -1);
        nextTabIndex = MathHelper.clamp(nextTabIndex, 0, 11);

        ((CreativeInventoryScreenAccessor) creativeInventoryScreen).invokeSetSelectedTab(ItemGroup.GROUPS[nextTabIndex]);
        MainClass.infoLog("Tab(name:%s) %d/%d selected".formatted(ItemGroup.GROUPS[nextTabIndex].getName(), nextTabIndex + 1, 12));
        Narrator.getNarrator().say("Tab %s selected".formatted(ItemGroup.GROUPS[nextTabIndex].getDisplayName().getString()), true);

        refreshGroupListAndSelectFirstGroup(false);
    }

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

    private void focusSlotAt(FocusDirection direction) {
        if (currentGroup == null) {
            changeGroup(true);
            return;
        }
        if (currentSlotItem == null) {
            focusGroupItem(currentGroup.getFirstGroupItem(), true);
            return;
        }

        SlotItem slotItem = getGroupItemInDirection(direction);
        if (slotItem == null) {
            Narrator.getNarrator().say("No slot %s".formatted(direction.getString()), true); //TODO use i18n instead
            return;
        }

        focusGroupItem(slotItem, true);
    }

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

    private void focusGroupItem(@NotNull SlotItem slotItem, boolean interrupt) {
        currentSlotItem = slotItem;
        moveToSlotItem(currentSlotItem);
//        MainClass.infoLog("Slot %d/%d selected".formatted(slotItem.slot.getIndex() + 1, currentGroup.slotItems.size()));

        String toSpeak = getCurrentSlotNarrationText();
        if (toSpeak.length() > 0) {
            previousSlotText = toSpeak;
            Narrator.getNarrator().say(toSpeak, interrupt);
        }
    }

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

    private void changeGroup(boolean goForward) {
        int nextGroupIndex = currentGroupIndex + (goForward ? 1 : -1);
        nextGroupIndex = MathHelper.clamp(nextGroupIndex, 0, currentSlotsGroupList.size() - 1);

        if (nextGroupIndex == currentGroupIndex) return; // Skip if already at the first or last group

        currentGroupIndex = nextGroupIndex;
        currentGroup = currentSlotsGroupList.get(currentGroupIndex);
        MainClass.infoLog("Group(name:%s) %d/%d selected".formatted(currentGroup.getGroupName(), currentGroupIndex + 1, currentSlotsGroupList.size()));
        Narrator.getNarrator().say("%s %s Group selected".formatted(currentGroup.isScrollable ? "Scrollable" : "", currentGroup.getGroupName()), true);

        focusGroupItem(currentGroup.getFirstGroupItem(), false);
    }

    private void refreshGroupListAndSelectFirstGroup(boolean interrupt) {
        currentSlotsGroupList = GroupGenerator.generateGroupsFromSlots(currentScreen);
        if (currentSlotsGroupList.size() == 0) return;

        currentGroupIndex = 0;
        currentGroup = currentSlotsGroupList.get(0);
        MainClass.infoLog("Group(name:%s) %d/%d selected".formatted(currentGroup.getGroupName(), currentGroupIndex + 1, currentSlotsGroupList.size()));
        Narrator.getNarrator().say("%s %s Group selected".formatted(currentGroup.isScrollable ? "Scrollable" : "", currentGroup.getGroupName()), interrupt);
        focusGroupItem(currentGroup.getFirstGroupItem(), false);
    }

    @SuppressWarnings("SameParameterValue")
    private void moveToSlotItem(SlotItem slotItem, int delay) {
        if (slotItem == null) return;

        int x = slotItem.x;
        int y = slotItem.y;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((currentScreen.getX() + x) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((currentScreen.getY() + y) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.moveAfterDelay(targetX, targetY, delay);
    }

    private void moveToSlotItem(SlotItem slotItem) {
        if (slotItem == null) return;

        int x = slotItem.x;
        int y = slotItem.y;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((currentScreen.getX() + x) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((currentScreen.getY() + y) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.move(targetX, targetY);
    }
}
