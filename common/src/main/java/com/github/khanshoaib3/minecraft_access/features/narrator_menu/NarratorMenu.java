package com.github.khanshoaib3.minecraft_access.features.narrator_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMenu;
import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import com.github.khanshoaib3.minecraft_access.features.BiomeIndicator;
import com.github.khanshoaib3.minecraft_access.features.ReadCrosshair;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityUtils;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.KeyUtils;
import com.github.khanshoaib3.minecraft_access.utils.PositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
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
import org.lwjgl.glfw.GLFW;

import java.util.Arrays;
import java.util.Objects;
import java.util.stream.Stream;

/**
 * Opens a menu when F4 button is pressed (configurable) with few helpful options.
 */
public class NarratorMenu {
    private static MinecraftClient minecraftClient;
    private static final Keystroke menuKey;
    private static final Keystroke hotKey;
    /**
     * Prevent the f4 menu open in this situation:
     * press f4 + hot key to trigger function switch, first release the hot key, then release the f4 key.
     * The user intend to switch the function, not open the menu.
     */
    private static boolean hasFunctionSwitchedBeforeF4Released = false;
    /**
     * Prevent the f4 menu open again after f4 menu is just closed by pressing f4.
     * The menu is closed by pressing the f4, and is opened by releasing the f4,
     * so if you slowly press down the f4 while menu is opening, the menu will be opened again when you release the f4.
     */
    private static boolean isMenuJustClosed = false;
    private int hotKeyFunctionIndex = 0;
    private static final boolean[] LAST_RUN_HAS_DONE_FLAG = new boolean[10];

    static {
        Arrays.fill(LAST_RUN_HAS_DONE_FLAG, true);

        // config keystroke conditions
        KeyBindingsHandler kbh = KeyBindingsHandler.getInstance();
        menuKey = new Keystroke(() -> KeyUtils.isAnyPressed(kbh.narratorMenuKey));
        hotKey = new Keystroke(() -> KeyUtils.isAnyPressed(kbh.narratorMenuHotKey), Keystroke.TriggeredAt.PRESSED);
    }

    /**
     * Should be same order as NarratorMenuGUI.init()
     */
    private static final MenuFunction[] MENU_FUNCTIONS = new MenuFunction[]{
            new MenuFunction("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info",
                    GLFW.GLFW_KEY_1, GLFW.GLFW_KEY_KP_1,
                    NarratorMenu::getBlockAndFluidTargetInformation),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position",
                    GLFW.GLFW_KEY_2, GLFW.GLFW_KEY_KP_2,
                    NarratorMenu::getBlockAndFluidTargetPosition),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.light_level",
                    GLFW.GLFW_KEY_3, GLFW.GLFW_KEY_KP_3,
                    NarratorMenu::getLightLevel),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.find_water",
                    GLFW.GLFW_KEY_4, GLFW.GLFW_KEY_KP_4,
                    () -> MainClass.fluidDetector.findClosestWaterSource(true)),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.find_lava",
                    GLFW.GLFW_KEY_5, GLFW.GLFW_KEY_KP_5,
                    () -> MainClass.fluidDetector.findClosestLavaSource(true)),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.biome",
                    GLFW.GLFW_KEY_6, GLFW.GLFW_KEY_KP_6,
                    NarratorMenu::getBiome),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.time_of_day",
                    GLFW.GLFW_KEY_7, GLFW.GLFW_KEY_KP_7,
                    NarratorMenu::getTimeOfDay),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.xp",
                    GLFW.GLFW_KEY_8, GLFW.GLFW_KEY_KP_8,
                    NarratorMenu::getXP),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.refresh_screen_reader",
                    GLFW.GLFW_KEY_9, GLFW.GLFW_KEY_KP_9,
                    () -> ScreenReaderController.refreshScreenReader(true)),
            new MenuFunction("minecraft_access.narrator_menu.gui.button.open_config_menu",
                    GLFW.GLFW_KEY_0, GLFW.GLFW_KEY_KP_0,
                    () -> MinecraftClient.getInstance().setScreen(new ConfigMenu("config_menu"))),
    };

    private record MenuFunction(String configKey, int numberKeyCode, int keyPadKeyCode, Runnable func) {
    }

    public void update() {
        try {
            minecraftClient = MinecraftClient.getInstance();
            if (minecraftClient == null) return;
            if (minecraftClient.player == null) return;

            if (minecraftClient.currentScreen instanceof NarratorMenuGUI) {
                if (handleInMenuActions()) return;
            }

            // other menus is opened
            if (minecraftClient.currentScreen != null) return;

            // F3 + F4 triggers game mode changing function in vanilla game,
            // will not open the menu under this situation.
            boolean isF3KeyNotPressed = !KeyUtils.isF3Pressed();

            if (menuKey.isPressing() && hotKey.canBeTriggered()) {
                // Executes immediately, but will not execute twice until release and press the key again
                hasFunctionSwitchedBeforeF4Released = true;
                switchHotKeyFunction();
            } else if (hotKey.canBeTriggered()) {
                executeHotKeyFunction();
            } else if (menuKey.isReleased() && !isMenuJustClosed && isF3KeyNotPressed && !hasFunctionSwitchedBeforeF4Released) {
                // The F4 is pressed before and released at current tick
                // To make the narrator menu open AFTER release the F4 key
                openNarratorMenu();
            }

            if (menuKey.isReleased()) {
                hasFunctionSwitchedBeforeF4Released = false;
                isMenuJustClosed = false;
            }

            menuKey.updateStateForNextTick();
            hotKey.updateStateForNextTick();
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in NarratorMenu.");
            e.printStackTrace();
        }
    }

    /**
     * @return return early if the menu is closed.
     */
    private static boolean handleInMenuActions() {
        // Close the menu if the F4 key is pressed while the menu is opening
        if (menuKey.isPressing()) {
            isMenuJustClosed = true;
            Objects.requireNonNull(minecraftClient.currentScreen).close();
            return true;
        }

        // With Narrator Menu opened, listen to number keys pressing for executing corresponding functions
        // for the little performance improvement, will not use KeyUtils here.
        long handle = minecraftClient.getWindow().getHandle();
        Stream.of(MENU_FUNCTIONS)
                .filter(f -> InputUtil.isKeyPressed(handle, f.numberKeyCode())
                        || InputUtil.isKeyPressed(handle, f.keyPadKeyCode()))
                .findFirst()
                .ifPresent(f -> f.func().run());
        return false;
    }

    private void switchHotKeyFunction() {
        hotKeyFunctionIndex = (hotKeyFunctionIndex + 1) % MENU_FUNCTIONS.length;
        MenuFunction f = MENU_FUNCTIONS[hotKeyFunctionIndex];
        String functionName = I18n.translate(f.configKey());
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.keys.other.narrator_menu_hot_key_switch", functionName), true);
    }

    private void executeHotKeyFunction() {
        // in case pressing the key too frequently, only execute when last execution has done
        if (LAST_RUN_HAS_DONE_FLAG[hotKeyFunctionIndex]) {
            LAST_RUN_HAS_DONE_FLAG[hotKeyFunctionIndex] = false;
            MENU_FUNCTIONS[hotKeyFunctionIndex].func.run();
            LAST_RUN_HAS_DONE_FLAG[hotKeyFunctionIndex] = true;
        }
    }

    private void openNarratorMenu() {
        Screen screen = new NarratorMenuGUI("f4_menu");
        minecraftClient.setScreen(screen); // post 1.18
//                minecraftClient.openScreen(screen); // pre 1.18
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
            if (OtherConfigsMap.getInstance().isUse12HourTimeFormat()) {
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
