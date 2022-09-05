package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import com.mojang.text2speech.Narrator;
import me.shedaniel.cloth.api.client.events.v0.ClothClientHooks;
import me.shedaniel.cloth.api.client.events.v0.ScreenHooks;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import org.lwjgl.glfw.GLFW;

import java.util.List;

@SuppressWarnings("rawtypes")
public class InventoryControls {
    private static HandledScreenAccessor screen;
    private MinecraftClient minecraftClient;

    private Slot currentSlot = null;
    private SlotsGroup currentGroup;
    private List<SlotsGroup> slotsGroups;
    private final KeyBinding LEFT_KEY;
    private final KeyBinding RIGHT_KEY;
    private final KeyBinding UP_KEY;
    private final KeyBinding DOWN_KEY;
    private KeyBinding GROUP_KEY;
    private KeyBinding HOME_KEY;
    private KeyBinding END_KEY;
    private KeyBinding CLICK_KEY;
    private KeyBinding RIGHT_CLICK_KEY;

    private enum FocusDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public InventoryControls() {
        String categoryTranslationKey = "Inventory Controls"; //TODO add translation key instead

        UP_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Up", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                categoryTranslationKey
        ));

        RIGHT_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Right", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                categoryTranslationKey
        ));

        DOWN_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "Down", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_K,
                categoryTranslationKey
        ));

        LEFT_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "LEFT_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_J,
                categoryTranslationKey
        ));

        ClothClientHooks.SCREEN_INIT_POST.register(this::onScreenOpen);
        ClothClientHooks.SCREEN_KEY_PRESSED.register(this::onKeyPress);
    }

    private ActionResult onScreenOpen(MinecraftClient minecraftClient, Screen currentScreen, ScreenHooks screenHooks) {
        this.minecraftClient = minecraftClient;
        slotsGroups = null;
        screen = null;
        currentGroup = null;
        currentSlot = null;

        if (currentScreen instanceof HandledScreen) {
            screen = (HandledScreenAccessor) currentScreen;
            slotsGroups = SlotsGroup.generateGroupsFromSlots(screen.getHandler().slots);
            moveMouseToHome();
        }
        return ActionResult.PASS;
    }

    private ActionResult onKeyPress(MinecraftClient mc, Screen currentScreen, int keyCode, int scanCode,
                                           int modifiers) {
        if (screen != null) {
            if (this.LEFT_KEY.matchesKey(keyCode, scanCode)) {
                focusSlotAt(FocusDirection.LEFT);
            } else if (this.RIGHT_KEY.matchesKey(keyCode, scanCode)) {
                focusSlotAt(FocusDirection.RIGHT);
            } else if (this.UP_KEY.matchesKey(keyCode, scanCode)) {
                focusSlotAt(FocusDirection.UP);
            } else if (this.DOWN_KEY.matchesKey(keyCode, scanCode)) {
                focusSlotAt(FocusDirection.DOWN);
            } else if (this.GROUP_KEY.matchesKey(keyCode, scanCode)) {
                if (modifiers == GLFW.GLFW_MOD_SHIFT) {
                    focusGroupVertically(false);
                } else {
                    focusGroupVertically(true);
                }
                return ActionResult.SUCCESS;
            } else if (this.HOME_KEY.matchesKey(keyCode, scanCode)) {
                if (modifiers == GLFW.GLFW_MOD_SHIFT) {
                    focusEdgeGroup(false);
                } else {
                    focusEdgeSlot(false);
                }
            } else if (this.END_KEY.matchesKey(keyCode, scanCode)) {
                if (modifiers == GLFW.GLFW_MOD_SHIFT) {
                    focusEdgeGroup(true);
                } else {
                    focusEdgeSlot(true);
                }
            } else if (this.CLICK_KEY.matchesKey(keyCode, scanCode)) {
                MouseUtils.leftClick();
            } else if (this.RIGHT_CLICK_KEY.matchesKey(keyCode, scanCode)) {
                MouseUtils.rightClick();
            }
        }
        return ActionResult.PASS;
    }

    private void focusSlotAt(FocusDirection direction) {
        if (currentGroup == null) {
            focusGroupVertically(true);
            return;
        }
        if (currentSlot == null) {
            focusSlot(currentGroup.getFirstSlot());
            return;
        }
        int targetDeltaX = 0;
        int targetDeltaY = 0;
        switch (direction) {
            case UP -> {
                if (!currentGroup.hasSlotAbove(currentSlot)) {
////                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.above"));
                    Narrator.getNarrator().say("No slots above", true);
                    return;
                }
                targetDeltaY = -18;
            }
            case DOWN -> {
                if (!currentGroup.hasSlotBelow(currentSlot)) {
////                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.below"));
                    Narrator.getNarrator().say("No slots below", true);
                    return;
                }
                targetDeltaY = 18;
            }
            case LEFT -> {
                if (!currentGroup.hasSlotLeft(currentSlot)) {
////                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.left"));
                    Narrator.getNarrator().say("No slots left", true);
                    return;
                }
                targetDeltaX = -18;
            }
            case RIGHT -> {
                if (!currentGroup.hasSlotRight(currentSlot)) {
////                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.right"));
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
                focusSlot(s);
                break;
            }
        }
    }

    private void focusSlot(Slot slot) {
        currentSlot = slot;
        moveToSlot(currentSlot);
        String message = "";
        if (currentGroup.getSlotName(currentSlot).length() > 0) {
            message += currentGroup.getSlotName(currentSlot) + ". ";
        }
        if (!currentSlot.hasStack()) {
//            message += I18n.translate("narrate.apextended.invcon.emptySlot");
            message = "empty";
        } else {
            List<Text> lines = currentSlot.getStack().getTooltip(minecraftClient.player, TooltipContext.Default.NORMAL);
            for (Text line : lines) {
                message += line.getString() + ", ";
            }
        }
        if (message.length() > 0) {
            Narrator.getNarrator().say(message, true);
        }
    }

    private void focusEdgeSlot(boolean end) {
        if (currentGroup == null) {
            focusGroupVertically(true);
            return;
        }
        if (currentGroup.slots.size() == 1 && currentSlot != null) {
//            NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.onlyOneSlot"));
            Narrator.getNarrator().say("Only One Slot", true);
            return;
        }
        focusSlot(end ? currentGroup.getLastSlot() : currentGroup.getFirstSlot());
    }

    private void focusEdgeGroup(boolean last) {
        focusGroup(slotsGroups.get(last ? slotsGroups.size() - 1 : 0));
    }

    private void focusGroupVertically(boolean goBelow) {
        if (currentGroup == null) {
            focusGroup(slotsGroups.get(0));
        } else {
            int currentGroupIndex = slotsGroups.indexOf(currentGroup);
            int nextGroupIndex = currentGroupIndex + (goBelow ? 1 : -1);
            if (nextGroupIndex < 0) {
//                NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.reachedTopGroup"));
                Narrator.getNarrator().say("Reached Top Group", true);
                return;
            } else if (nextGroupIndex > slotsGroups.size() - 1) {
//                NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.reachedBottomGroup"));
                Narrator.getNarrator().say("Reached Bottom Group", true);
                return;
            } else {
                focusGroup(slotsGroups.get(nextGroupIndex));
            }
        }
    }

    private void focusGroup(SlotsGroup group) {
        currentGroup = group;
        currentSlot = null;
        moveMouseToHome();
//        NarratorPlus.narrate(currentGroup.getName());
        Narrator.getNarrator().say(currentGroup.getName(), true);
    }

    private void moveMouseToHome() {
        SlotsGroup lastGroup = slotsGroups.get(slotsGroups.size() - 1);
        Slot lastSlot = lastGroup.getLastSlot();
        moveMouseToScreenCoords(lastSlot.x + 19, lastSlot.y + 19);
    }

    private void moveToSlot(Slot slot) {
        if (slot == null) {
            return;
        }
        moveMouseToScreenCoords(slot.x + 9,slot.y + 9);
    }

    private void moveMouseToScreenCoords(int x, int y) {
        int targetX = (int) (minecraftClient.getWindow().getX() + ((screen.getX() + x) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((screen.getY() + y) * minecraftClient.getWindow().getScaleFactor()));

        MainClass.infoLog("%s %s".formatted(minecraftClient.getWindow().getX(), minecraftClient.getWindow().getY()));

        MouseUtils.move(targetX, targetY);
    }
}
