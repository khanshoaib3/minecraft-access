package com.github.khanshoaib3.minecraft_access.features.narrator_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMenu;
import com.github.khanshoaib3.minecraft_access.features.BiomeIndicator;
import com.github.khanshoaib3.minecraft_access.features.ReadCrosshair;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityUtils;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.PositionUtils;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.InputUtil;
import net.minecraft.fluid.FluidState;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.MutableText;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.Biome;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * Opens a menu when F4 button is pressed (configurable) with few helpful options.
 */
public class NarratorMenu {
    private static MinecraftClient minecraftClient;
    private static boolean isMenuKeyPressedPreviousTick = false;
    private static boolean isHotKeyPressedPreviousTick = false;
    private static boolean isHotKeySwitchedPreviousTick = false;
    private int hotKeyFunctionIndex = 0;
    private static final boolean[] LAST_RUN_HAS_DONE_FLAG = new boolean[10];

    static {
        Arrays.fill(LAST_RUN_HAS_DONE_FLAG, true);
    }

    /**
     * Should be same order as NarratorMenuGUI.init()
     */
    private static final MenuFunction[] MENU_FUNCTIONS = new MenuFunction[]{
            new MenuFunction("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info",
                    InputUtil.GLFW_KEY_1, InputUtil.GLFW_KEY_KP_1,
                    NarratorMenu::getBlockAndFluidTargetInformation),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position",
                    InputUtil.GLFW_KEY_2, InputUtil.GLFW_KEY_KP_2,
                    NarratorMenu::getBlockAndFluidTargetPosition),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.light_level",
                    InputUtil.GLFW_KEY_3, InputUtil.GLFW_KEY_KP_3,
                    NarratorMenu::getLightLevel),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.find_water",
                    InputUtil.GLFW_KEY_4, InputUtil.GLFW_KEY_KP_4,
                    () -> MainClass.fluidDetector.findClosestWaterSource(true)),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.find_lava",
                    InputUtil.GLFW_KEY_5, InputUtil.GLFW_KEY_KP_5,
                    () -> MainClass.fluidDetector.findClosestLavaSource(true)),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.biome",
                    InputUtil.GLFW_KEY_6, InputUtil.GLFW_KEY_KP_6,
                    NarratorMenu::getBiome),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.time_of_day",
                    InputUtil.GLFW_KEY_7, InputUtil.GLFW_KEY_KP_7,
                    NarratorMenu::getTimeOfDay),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.xp",
                    InputUtil.GLFW_KEY_8, InputUtil.GLFW_KEY_KP_8,
                    NarratorMenu::getXP),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.refresh_screen_reader",
                    InputUtil.GLFW_KEY_9, InputUtil.GLFW_KEY_KP_9,
                    () -> ScreenReaderController.refreshScreenReader(true)),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.open_config_menu",
                    InputUtil.GLFW_KEY_0, InputUtil.GLFW_KEY_KP_0,
                    () -> MinecraftClient.getInstance().setScreen(new ConfigMenu("config_menu"))),
    };

    private record MenuFunction(String configKey, int numberKeyCode, int keyPadKeyCode, Runnable func) {
    }

    public void update() {
        try {
            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;

            // With Narrator Menu opened, listen to number keys pressing for executing corresponding functions
            if (minecraftClient.currentScreen instanceof NarratorMenuGUI) {
                long handle = minecraftClient.getWindow().getHandle();
                Stream.of(MENU_FUNCTIONS)
                        .filter(f -> InputUtil.isKeyPressed(handle, f.numberKeyCode())
                                || InputUtil.isKeyPressed(handle, f.keyPadKeyCode()))
                        .findFirst()
                        .ifPresent(f -> f.func().run());
            }

            if (minecraftClient.currentScreen != null) return;

            boolean isNarratorMenuKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.narratorMenuKey);
            boolean isNarratorMenuHotKeyPressed = KeyBindingsHandler.isPressed(MainClass.keyBindingsHandler.narratorMenuHotKey);

            // F3 + F4 triggers game mode changing function in vanilla game, will not open the menu under this situation.
            boolean isF3KeyNotPressed = !KeyBindingsHandler.isF3KeyPressed();

            // The F4 is pressed before and released at current tick
            // To make the narrator menu open AFTER release the F4 key
            boolean openTheMenuScreen = !isNarratorMenuKeyPressed && isMenuKeyPressedPreviousTick;

            // Opposite to menu open, executes immediately,
            // but will not execute twice until release and press the key again
            boolean triggerHotKey = isNarratorMenuHotKeyPressed && !isHotKeyPressedPreviousTick;

            if (isNarratorMenuKeyPressed && triggerHotKey) {
                // for prevent the menu open this time after release the F4 key
                // the user intend to switch the function, not open the menu
                isHotKeySwitchedPreviousTick = true;
                // switch to the next narrator menu function
                hotKeyFunctionIndex = (hotKeyFunctionIndex + 1) % MENU_FUNCTIONS.length;
                MenuFunction f = MENU_FUNCTIONS[hotKeyFunctionIndex];
                String functionName = I18n.translate(f.configKey());
                MainClass.speakWithNarrator(I18n.translate("minecraft_access.keys.other.narrator_menu_hot_key_switch", functionName), true);

            } else if (triggerHotKey) {
                // in case pressing the key too frequently, only execute when last execution has done
                if (LAST_RUN_HAS_DONE_FLAG[hotKeyFunctionIndex]) {
                    LAST_RUN_HAS_DONE_FLAG[hotKeyFunctionIndex] = false;
                    MENU_FUNCTIONS[hotKeyFunctionIndex].func.run();
                    LAST_RUN_HAS_DONE_FLAG[hotKeyFunctionIndex] = true;
                }

            } else if (openTheMenuScreen && isF3KeyNotPressed && !isHotKeySwitchedPreviousTick) {
                Screen screen = new NarratorMenuGUI("f4_menu");
                minecraftClient.setScreen(screen); // post 1.18
//                minecraftClient.openScreen(screen); // pre 1.18
            }

            // update the states for next tick
            isMenuKeyPressedPreviousTick = isNarratorMenuKeyPressed;
            isHotKeyPressedPreviousTick = isNarratorMenuHotKeyPressed;
            // clean the state when F4 is released
            if (openTheMenuScreen) isHotKeySwitchedPreviousTick = false;

        } catch (Exception e) {
            MainClass.errorLog("An error occurred in NarratorMenu.");
            e.printStackTrace();
        }
    }

    public static void getBlockAndFluidTargetInformation() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.cameraEntity == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            HitResult hit = minecraftClient.cameraEntity.raycast(20.0, 0.0f, true);
            if (hit == null)
                return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava()
                    && checkForFluidHit(minecraftClient, hit, false)) return;

            switch (hit.getType()) {
                case MISS, ENTITY -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case BLOCK -> {
                    try {
                        BlockHitResult blockHit = (BlockHitResult) hit;
                        BlockPos blockPos = blockHit.getBlockPos();

                        BlockState blockState = minecraftClient.world.getBlockState(blockPos);
                        Block block = blockState.getBlock();
                        MutableText mutableText = block.getName();

                        String text = mutableText.getString() + ", " + PositionUtils.getPositionDifference(blockPos);
                        MainClass.speakWithNarrator(text, true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBlockAndFluidTargetPosition() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.cameraEntity == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            HitResult hit = minecraftClient.cameraEntity.raycast(20.0, 0.0f, true);
            if (hit == null)
                return;

            if (!minecraftClient.player.isSwimming() && !minecraftClient.player.isSubmergedInWater() && !minecraftClient.player.isInsideWaterOrBubbleColumn() && !minecraftClient.player.isInLava()
                    && checkForFluidHit(minecraftClient, hit, true)) return;

            switch (hit.getType()) {
                case MISS, ENTITY -> MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.target_missed"), true);
                case BLOCK -> {
                    try {
                        BlockHitResult blockHitResult = (BlockHitResult) hit;
                        BlockPos blockPos = blockHitResult.getBlockPos();
                        MainClass.speakWithNarrator(PositionUtils.getPosition(blockPos), true);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean checkForFluidHit(MinecraftClient minecraftClient, HitResult fluidHit, boolean onlyPosition) {
        if (minecraftClient == null) return false;
        if (minecraftClient.world == null) return false;
        if (minecraftClient.currentScreen != null) return false;

        if (fluidHit.getType() == HitResult.Type.BLOCK) {
            BlockPos blockPos = ((BlockHitResult) fluidHit).getBlockPos();
            FluidState fluidState = minecraftClient.world.getFluidState(blockPos);

            String name = ReadCrosshair.getFluidName(fluidState.getRegistryEntry());
            if (name.equals("block.minecraft.empty")) return false;
            if (name.contains("block.minecraft."))
                name = name.replace("block.minecraft.", ""); // Remove `block.minecraft.` for unsupported languages

            if (onlyPosition) {
                MainClass.speakWithNarrator(PositionUtils.getPosition(blockPos), true);
                return true;
            }

            int level = fluidState.getLevel();
            String levelString = "";
            if (level < 8) levelString = I18n.translate("minecraft_access.read_crosshair.fluid_level", level);

            String toSpeak = name + levelString + ", " + PositionUtils.getPositionDifference(blockPos);

            MainClass.speakWithNarrator(toSpeak, true);
            return true;
        }
        return false;
    }

    public static void getLightLevel() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            int light = minecraftClient.world.getLightLevel(minecraftClient.player.getBlockPos());
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.light_level", light), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getBiome() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();

            RegistryEntry<Biome> var27 = minecraftClient.world.getBiome(minecraftClient.player.getBlockPos());
            String name = I18n.translate(BiomeIndicator.getBiomeName(var27));
            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.biome", name), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getXP() {
        try {
            if (minecraftClient.player == null) return;

            minecraftClient.player.closeScreen();

            MainClass.speakWithNarrator(I18n.translate("minecraft_access.narrator_menu.xp", ClientPlayerEntityUtils.getExperienceLevel(), ClientPlayerEntityUtils.getExperienceProgress()), true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void getTimeOfDay() {
        try {
            if (minecraftClient.player == null) return;
            if (minecraftClient.world == null) return;

            minecraftClient.player.closeScreen();
            long daytime = minecraftClient.player.world.getTimeOfDay() + 6000;
            int hours = (int) (daytime / 1000) % 24;
            int minutes = (int) ((daytime % 1000) * 60 / 1000);

            String translationKey = "minecraft_access.narrator_menu.time_of_day";
            if (MainClass.config.getConfigMap().getOtherConfigsMap().isUse12HourTimeFormat()) {
                if (hours > 12) {
                    hours -= 12;
                    translationKey += "_pm";
                } else if (hours == 12) {
                    translationKey += "_pm";
                } else {
                    translationKey += "_am";
                }
            }

            String toSpeak = "%02d:%02d".formatted(hours, minutes);
            toSpeak = I18n.translate(translationKey, toSpeak);
            MainClass.speakWithNarrator(toSpeak, true);
        } catch (Exception e) {
            MainClass.errorLog("An error occurred while speaking time of day.");
            e.printStackTrace();
        }
    }
}
