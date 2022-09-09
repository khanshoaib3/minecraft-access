package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.CreativeInventoryScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.mixin.SlotAccessor;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import com.mojang.text2speech.Narrator;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.item.ItemGroup;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.jetbrains.annotations.NotNull;
import org.lwjgl.glfw.GLFW;

import java.util.List;
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
    private GroupItem currentGroupItem = null;

    private final KeyBinding leftKey;
    private final KeyBinding rightKey;
    private final KeyBinding upKey;
    private final KeyBinding downKey;
    private final KeyBinding groupKey;
    private final KeyBinding leftMouseClickKey;
    private final KeyBinding rightMouseClickKey;
    private final KeyBinding switchTabKey;

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
    }

    public void update() {
        if (!this.shouldRun) return;
        this.minecraftClient = MinecraftClient.getInstance();

        if (minecraftClient == null) return;
        if (minecraftClient.player == null) return;
        if (minecraftClient.currentScreen == null) {
            previousScreen = null;
            currentScreen = null;
            return;
        }
        if (!(minecraftClient.currentScreen instanceof HandledScreen)) return;

        try {
            boolean wasAnyKeyPressed = keyListener();

            currentScreen = (HandledScreenAccessor) minecraftClient.currentScreen;

            if (previousScreen != currentScreen) {
                // Focus at the first slot of the first group if a new screen is opened or the total number of group changes
                previousScreen = currentScreen;
                refreshGroupListAndSelectFirstGroup(true);
            }

            if (currentSlotsGroupList.size() == 0) return;

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

    private void refreshGroupListAndSelectFirstGroup(boolean interrupt) {
        currentSlotsGroupList = SlotsGroup.generateGroupsFromSlots(currentScreen);
        if(currentSlotsGroupList.size()==0) return;

        currentGroup = currentSlotsGroupList.get(0);
        Narrator.getNarrator().say("Group %s selected".formatted(currentGroup.name), interrupt);
        focusGroupItem(currentGroup.getFirstGroupItem(), true);
    }

    private boolean keyListener() {
        boolean isGroupKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(groupKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftClickKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(leftMouseClickKey.getBoundKeyTranslationKey()).getCode());
        boolean isRightCLickKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(rightMouseClickKey.getBoundKeyTranslationKey()).getCode());
        boolean isUpKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(upKey.getBoundKeyTranslationKey()).getCode());
        boolean isRightKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(rightKey.getBoundKeyTranslationKey()).getCode());
        boolean isDownKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(downKey.getBoundKeyTranslationKey()).getCode());
        boolean isSwitchTabKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(switchTabKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftKeyPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey(leftKey.getBoundKeyTranslationKey()).getCode());
        boolean isLeftShiftPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.left.shift").getCode());

        if (isGroupKeyPressed) {
            MainClass.infoLog("Group key pressed");
            changeGroup(!isLeftShiftPressed);
            return true;
        } else if (isSwitchTabKeyPressed) {
            MainClass.infoLog("Switch Tab key pressed");
            changeTab(!isLeftShiftPressed);
            return true;
        } else if (isLeftClickKeyPressed) {
            MouseUtils.leftClick();
            MainClass.infoLog("Left click key pressed");
            return true;
        } else if (isRightCLickKeyPressed) {
            MouseUtils.rightClick();
            MainClass.infoLog("Right click key pressed");
            return true;
        } else if (isUpKeyPressed) {
            focusSlotAt(FocusDirection.UP);
            MainClass.infoLog("Up key pressed");
            return true;
        } else if (isRightKeyPressed) {
            focusSlotAt(FocusDirection.RIGHT);
            MainClass.infoLog("Right key pressed");
            return true;
        } else if (isDownKeyPressed) {
            MainClass.infoLog("Down key pressed");
            focusSlotAt(FocusDirection.DOWN);
            return true;
        } else if (isLeftKeyPressed) {
            focusSlotAt(FocusDirection.LEFT);
            MainClass.infoLog("Left key pressed");
            return true;
        }
        return false;
    }

    private void changeTab(boolean goForward) {
        if (!(minecraftClient.currentScreen instanceof CreativeInventoryScreen creativeInventoryScreen)) return;

        int nextTabIndex = creativeInventoryScreen.getSelectedTab() + (goForward ? 1 : -1);
        nextTabIndex = MathHelper.clamp(nextTabIndex, 0, 11);

        ((CreativeInventoryScreenAccessor) creativeInventoryScreen).invokeSetSelectedTab(ItemGroup.GROUPS[nextTabIndex]);
        MainClass.infoLog("Tab(name:%s) %d/%d selected".formatted(ItemGroup.GROUPS[nextTabIndex].getName(), nextTabIndex + 1, 12));
        Narrator.getNarrator().say("Tab %s selected".formatted(ItemGroup.GROUPS[nextTabIndex].getDisplayName().getString()), true);

        refreshGroupListAndSelectFirstGroup(false);
    }

    private void focusSlotAt(FocusDirection direction) {
        if (currentGroup == null) {
            changeGroup(true);
            return;
        }
        if (currentGroupItem == null) {
            focusGroupItem(currentGroup.getFirstGroupItem(), false);
            return;
        }

        GroupItem groupItem = getGroupItemInDirection(direction);
        if (groupItem == null) {
            Narrator.getNarrator().say("No slot %s".formatted(direction.getString()), true); //TODO use i18n instead
            return;
        }

        focusGroupItem(groupItem, false);
    }

    private GroupItem getGroupItemInDirection(FocusDirection focusDirection) {
        switch (focusDirection) {
            case UP -> {
                if (!currentGroup.hasGroupItemAbove(currentGroupItem)) return null;

                if (currentGroupItem.upGroupItem != null) return currentGroupItem.upGroupItem;

                for (GroupItem item : currentGroup.groupItems) {
                    if (item.x == currentGroupItem.x && item.y == currentGroupItem.y - 18) {
                        return item;
                    }
                }
            }
            case RIGHT -> {
                if (!currentGroup.hasGroupItemRight(currentGroupItem)) return null;

                if (currentGroupItem.rightGroupItem != null) return currentGroupItem.rightGroupItem;

                for (GroupItem item : currentGroup.groupItems) {
                    if (item.x == currentGroupItem.x + 18 && item.y == currentGroupItem.y) {
                        return item;
                    }
                }
            }
            case DOWN -> {
                if (!currentGroup.hasGroupItemBelow(currentGroupItem)) return null;

                if (currentGroupItem.downGroupItem != null) return currentGroupItem.downGroupItem;

                for (GroupItem item : currentGroup.groupItems) {
                    if (item.x == currentGroupItem.x && item.y == currentGroupItem.y + 18) {
                        return item;
                    }
                }
            }
            case LEFT -> {
                if (!currentGroup.hasGroupItemLeft(currentGroupItem)) return null;

                if (currentGroupItem.leftGroupItem != null) return currentGroupItem.leftGroupItem;

                for (GroupItem item : currentGroup.groupItems) {
                    if (item.x == currentGroupItem.x - 18 && item.y == currentGroupItem.y) {
                        return item;
                    }
                }
            }
        }

        return null;
    }

    private void focusGroupItem(@NotNull GroupItem groupItem, boolean afterChangingGroup) {
        currentGroupItem = groupItem;
        moveToSlot(currentGroupItem);
        MainClass.infoLog("Slot %d/%d selected".formatted(groupItem.slot.getIndex() + 1, currentGroup.groupItems.size()));

        MainClass.infoLog("Index:%d Class:%s".formatted(((SlotAccessor) groupItem.slot).getInventoryIndex(), groupItem.slot.getClass().toString()));

        if (!currentGroupItem.slot.hasStack()) {
            // I18n.translate("minecraft_access.inventory_controls.empty_slot");
            Narrator.getNarrator().say("%s Empty groupItem".formatted(currentGroup.getSlotName(currentGroupItem.slot)), !afterChangingGroup);
            return;
        }

        String info = "%s %d".formatted(currentGroup.getSlotName(currentGroupItem.slot), currentGroupItem.slot.getStack().getCount());
        StringBuilder toolTipString = new StringBuilder();
        List<Text> toolTipList = currentGroupItem.slot.getStack().getTooltip(minecraftClient.player, TooltipContext.Default.NORMAL);
        for (Text line : toolTipList) {
            toolTipString.append(line.getString());
        }

        String toSpeak = "%s %s".formatted(info, toolTipString.toString());
        if (toSpeak.length() > 0)
            Narrator.getNarrator().say(toSpeak, !afterChangingGroup);
    }

    private void changeGroup(boolean goForward) {
        int nextGroupIndex = currentGroupIndex + (goForward ? 1 : -1);
        nextGroupIndex = MathHelper.clamp(nextGroupIndex, 0, currentSlotsGroupList.size() - 1);

        if (nextGroupIndex == currentGroupIndex) return; // Skip if already at the first or last group

        currentGroupIndex = nextGroupIndex;
        currentGroup = currentSlotsGroupList.get(currentGroupIndex);
        MainClass.infoLog("Group(name:%s) %d/%d selected".formatted(currentGroup.name, currentGroupIndex + 1, currentSlotsGroupList.size()));
        Narrator.getNarrator().say("Group %s selected".formatted(currentGroup.name), true);

        focusGroupItem(currentGroup.getFirstGroupItem(), true);
    }

    private void moveToSlot(GroupItem groupItem) {
        if (groupItem == null) return;

        // To get the centre of the groupItem
        int centreX = groupItem.x/* + 9*/;
        int centreY = groupItem.y/* + 9*/;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((currentScreen.getX() + centreX) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((currentScreen.getY() + centreY) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.move(targetX, targetY);
    }
}
