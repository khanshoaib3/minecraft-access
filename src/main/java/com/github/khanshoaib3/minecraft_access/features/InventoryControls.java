package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.mixin.HandledScreenAccessor;
import com.github.khanshoaib3.minecraft_access.utils.MouseUtils;
import com.mojang.text2speech.Narrator;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.screen.slot.Slot;
import net.minecraft.text.Text;
import net.minecraft.util.math.MathHelper;
import org.lwjgl.glfw.GLFW;

import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

@SuppressWarnings("rawtypes")
public class InventoryControls {
    public boolean shouldRun = true;
    private static HandledScreenAccessor screen;
    private MinecraftClient minecraftClient;

    private Slot currentSlot = null;
    private SlotsGroup currentGroup = null;
    private int currentGroupIndex = 0;
    private List<SlotsGroup> slotsGroups;

    private final KeyBinding LEFT_KEY;
    private final KeyBinding RIGHT_KEY;
    private final KeyBinding UP_KEY;
    private final KeyBinding DOWN_KEY;
    private final KeyBinding GROUP_KEY;
    private final KeyBinding CLICK_KEY;
    private final KeyBinding RIGHT_CLICK_KEY;

    private enum FocusDirection {
        UP, DOWN, LEFT, RIGHT
    }

    public InventoryControls() {
        String categoryTranslationKey = "Inventory Controls"; //TODO add translation key instead

        GROUP_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "GROUP_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_C,
                categoryTranslationKey
        ));

        CLICK_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "CLICK_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET,
                categoryTranslationKey
        ));

        RIGHT_CLICK_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "RIGHT_CLICK_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET,
                categoryTranslationKey
        ));

        UP_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "UP_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_I,
                categoryTranslationKey
        ));

        RIGHT_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "RIGHT_KEY", //TODO add translation key instead
                InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_L,
                categoryTranslationKey
        ));

        DOWN_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "DOWN_KEY", //TODO add translation key instead
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
    }

    private void init(MinecraftClient minecraftClient) {
        this.minecraftClient = minecraftClient;
        slotsGroups = null;
        screen = null;

        if (minecraftClient.currentScreen instanceof HandledScreen) {
            screen = (HandledScreenAccessor) minecraftClient.currentScreen;
            slotsGroups = SlotsGroup.generateGroupsFromSlots(screen.getHandler().slots);
//            moveMouseToHome();
        }
    }

    public void update() {
        if (!this.shouldRun) return;

        try {
            boolean wasAnyKeyPressed = false;

            boolean isGroupKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(GROUP_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isLeftClickKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(CLICK_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isRightCLickKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(RIGHT_CLICK_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isUpKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(UP_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isRightKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(RIGHT_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isDownKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(DOWN_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isLeftKeyPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey(LEFT_KEY.getBoundKeyTranslationKey()).getCode());
            boolean isLeftShiftPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(), InputUtil.fromTranslationKey("key.keyboard.left.shift").getCode());

            init(MinecraftClient.getInstance());

            if (isGroupKeyPressed) {
                MainClass.infoLog("Group key pressed");
                wasAnyKeyPressed = true;
                focusGroupVertically(!isLeftShiftPressed);
            } else if (isLeftClickKeyPressed) {
                MouseUtils.leftClick();
                MainClass.infoLog("Left click key pressed");
                wasAnyKeyPressed = true;
            } else if (isRightCLickKeyPressed) {
                MouseUtils.rightClick();
                MainClass.infoLog("Right click key pressed");
                wasAnyKeyPressed = true;
            } else if (isUpKeyPressed) {
                focusSlotAt(FocusDirection.UP);
                MainClass.infoLog("Up key pressed");
                wasAnyKeyPressed = true;
            } else if (isRightKeyPressed) {
                focusSlotAt(FocusDirection.RIGHT);
                MainClass.infoLog("Right key pressed");
                wasAnyKeyPressed = true;
            } else if (isDownKeyPressed) {
                MainClass.infoLog("Down key pressed");
                wasAnyKeyPressed = true;
                focusSlotAt(FocusDirection.DOWN);
            } else if (isLeftKeyPressed) {
                focusSlotAt(FocusDirection.LEFT);
                MainClass.infoLog("Left key pressed");
                wasAnyKeyPressed = true;
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
//                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.above"));
                    Narrator.getNarrator().say("No slots above", true);
                    return;
                }
                targetDeltaY = -18;
            }
            case DOWN -> {
                if (!currentGroup.hasSlotBelow(currentSlot)) {
//                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.below"));
                    Narrator.getNarrator().say("No slots below", true);
                    return;
                }
                targetDeltaY = 18;
            }
            case LEFT -> {
                if (!currentGroup.hasSlotLeft(currentSlot)) {
//                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.left"));
                    Narrator.getNarrator().say("No slots left", true);
                    return;
                }
                targetDeltaX = -18;
            }
            case RIGHT -> {
                if (!currentGroup.hasSlotRight(currentSlot)) {
//                    NarratorPlus.narrate(I18n.translate("narrate.apextended.invcon.noSlots.right"));
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

    private void focusGroupVertically(boolean goBelow) {
        int nextGroupIndex = currentGroupIndex + (goBelow ? 1 : -1);
        nextGroupIndex = MathHelper.clamp(nextGroupIndex, 0, slotsGroups.size() - 1);

        MainClass.infoLog(currentGroupIndex + "\t" + nextGroupIndex);
        currentGroupIndex = nextGroupIndex;
        currentGroup = slotsGroups.get(currentGroupIndex);
        focusSlot(currentGroup.getFirstSlot());
    }

    private void moveToSlot(Slot slot) {
        if (slot == null) {
            return;
        }
        int x = slot.x + 9;
        int y = slot.y + 9;

        int targetX = (int) (minecraftClient.getWindow().getX() + ((screen.getX() + x) * minecraftClient.getWindow().getScaleFactor()));
        int targetY = (int) (minecraftClient.getWindow().getY() + ((screen.getY() + y) * minecraftClient.getWindow().getScaleFactor()));

        MouseUtils.move(targetX, targetY);
    }
}
