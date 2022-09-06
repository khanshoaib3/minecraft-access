package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
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
import net.minecraft.screen.slot.Slot;
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

    private List<SlotsGroup> previousSlotsGroupList = null;
    private List<SlotsGroup> currentSlotsGroupList = null;
    private SlotsGroup currentGroup = null;
    private int currentGroupIndex = 0;
    private Slot currentSlot = null;

    private final KeyBinding leftKey;
    private final KeyBinding rightKey;
    private final KeyBinding upKey;
    private final KeyBinding downKey;
    private final KeyBinding groupKey;
    private final KeyBinding leftMouseClickKey;
    private final KeyBinding rightMouseClickKey;

    private enum FocusDirection {
        UP, DOWN, LEFT, RIGHT
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
    }

    public void update() {
        if (!this.shouldRun) return;
        this.minecraftClient = MinecraftClient.getInstance();

        if (minecraftClient == null) return;
        if (minecraftClient.player == null) return;
        if (minecraftClient.currentScreen == null) {
            previousScreen = null;
            previousSlotsGroupList = null;
            currentScreen = null;
            return;
        }
        if (!(minecraftClient.currentScreen instanceof HandledScreen)) return;

        try {
            currentScreen = (HandledScreenAccessor) minecraftClient.currentScreen;
            currentSlotsGroupList = SlotsGroup.generateGroupsFromSlots(currentScreen);

            if (previousScreen != currentScreen || (previousSlotsGroupList != null && previousSlotsGroupList.size() != currentSlotsGroupList.size())) {
                // Focus at the first slot of the first group if a new screen is opened or the total number of group changes
                previousScreen = currentScreen;
                previousSlotsGroupList = currentSlotsGroupList;

                currentGroup = currentSlotsGroupList.get(0);
                Narrator.getNarrator().say("Group %s selected".formatted(currentGroup.name), true);
                focusSlot(currentGroup.getFirstSlot(), true);
            }

            boolean wasAnyKeyPressed = keyListener();

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
        boolean isLeftShiftPressed = InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.left.shift").getCode());

        if (isGroupKeyPressed) {
            MainClass.infoLog("Group key pressed");
            changeGroup(!isLeftShiftPressed);
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

    private void focusSlotAt(FocusDirection direction) {
        if (currentGroup == null) {
            changeGroup(true);
            return;
        }
        if (currentSlot == null) {
            focusSlot(currentGroup.getFirstSlot(), false);
            return;
        }

        int targetDeltaX = 0;
        int targetDeltaY = 0;
        switch (direction) {
            case UP -> {
                if (!currentGroup.hasSlotAbove(currentSlot)) {
                    // NarratorPlus.narrate(I18n.translate("minecraft_access.inventory_controls.no_slots.above"));
                    Narrator.getNarrator().say("No slots above", true);
                    return;
                }
                targetDeltaY = -18;
            }
            case DOWN -> {
                if (!currentGroup.hasSlotBelow(currentSlot)) {
                    // NarratorPlus.narrate(I18n.translate("minecraft_access.inventory_controls.no_slots.below"));
                    Narrator.getNarrator().say("No slots below", true);
                    return;
                }
                targetDeltaY = 18;
            }
            case LEFT -> {
                if (!currentGroup.hasSlotLeft(currentSlot)) {
                    // NarratorPlus.narrate(I18n.translate("minecraft_access.inventory_controls.no_slots.left"));
                    Narrator.getNarrator().say("No slots left", true);
                    return;
                }
                targetDeltaX = -18;
            }
            case RIGHT -> {
                if (!currentGroup.hasSlotRight(currentSlot)) {
                    // NarratorPlus.narrate(I18n.translate("minecraft_access.inventory_controls.no_slots.right"));
                    Narrator.getNarrator().say("No slots right", true);
                    return;
                }
                targetDeltaX = 18;
            }
        }
        int targetX = currentSlot.x + targetDeltaX;
        int targetY = currentSlot.y + targetDeltaY;
        for (Slot s : currentGroup.slots) {
            if (s.x == targetX && s.y == targetY) {
                focusSlot(s, false);
                break;
            }
        }
    }

    private void focusSlot(@NotNull Slot slot, boolean afterChangingGroup) {
        currentSlot = slot;
        moveToSlot(currentSlot);
        MainClass.infoLog("Slot %d/%d selected".formatted(slot.getIndex() + 1, currentGroup.slots.size()));

        MainClass.infoLog("Index:%d Class:%s".formatted(((SlotAccessor)slot).getInventoryIndex(), slot.getClass().toString()));

        if (!currentSlot.hasStack()) {
            // I18n.translate("minecraft_access.inventory_controls.empty_slot");
            Narrator.getNarrator().say("%s Empty slot".formatted(currentGroup.getSlotName(currentSlot)), !afterChangingGroup);
            return;
        }

        String info = "%s %d".formatted(currentGroup.getSlotName(currentSlot), currentSlot.getStack().getCount());
        StringBuilder toolTipString = new StringBuilder();
        List<Text> toolTipList = currentSlot.getStack().getTooltip(minecraftClient.player, TooltipContext.Default.NORMAL);
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

        focusSlot(currentGroup.getFirstSlot(), true);
    }

    private void moveToSlot(Slot slot) {
        if (slot == null) return;

        // To get the centre of the slot
        int centreX = slot.x + 9;
        int centreY = slot.y + 9;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((currentScreen.getX() + centreX) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((currentScreen.getY() + centreY) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.move(targetX, targetY);
    }
}
