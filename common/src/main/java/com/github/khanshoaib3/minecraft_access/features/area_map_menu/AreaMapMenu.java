package com.github.khanshoaib3.minecraft_access.features.area_map_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.AreaMapConfigMap;
import com.github.khanshoaib3.minecraft_access.features.ReadCrosshair;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.github.khanshoaib3.minecraft_access.utils.condition.IntervalKeystroke;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import com.github.khanshoaib3.minecraft_access.utils.condition.MenuKeystroke;
import com.github.khanshoaib3.minecraft_access.utils.position.Orientation;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.system.KeyUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

/**
 * This menu gives user a bird eye view of surrounding area.
 * It plays the role of the Map function in other games.
 * User can move a virtual cursor to explore the area (speak out pointed block's information).
 * Open the AreaMap menu with F6.
 */
@SuppressWarnings("unused")
public class AreaMapMenu {
    private static final AreaMapMenu instance;

    private static final MenuKeystroke menuKey;
    private static final IntervalKeystroke[] cursorMovingKeys = new IntervalKeystroke[6];
    private static final IntervalKeystroke cursorResetKey;
    private static final IntervalKeystroke mapLockKey;
    public static final Set<Pair<IntervalKeystroke, Orientation>> CURSOR_MOVING_DIRECTIONS = new HashSet<>(6);

    private boolean enabled;
    private BlockPos cursor;
    private boolean mapLocked;

    static {
        instance = new AreaMapMenu();

        menuKey = new MenuKeystroke(() -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapMenuKey));

        int keyInterval = AreaMapConfigMap.getInstance().getDelayInMilliseconds();
        int cursorMovingKeyIndex = 0;
        for (var p : List.<Pair<Orientation, BooleanSupplier>>of(
                new Pair<>(Orientation.NORTH, () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapNorthKey)),
                new Pair<>(Orientation.SOUTH, () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapSouthKey)),
                new Pair<>(Orientation.WEST, () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapWestKey)),
                new Pair<>(Orientation.EAST, () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapEastKey)),
                new Pair<>(Orientation.UP, () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapUpKey)),
                new Pair<>(Orientation.DOWN, () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapDownKey))
        )) {
            cursorMovingKeys[cursorMovingKeyIndex] = new IntervalKeystroke(p.getRight(), Keystroke.TriggeredAt.PRESSING, Interval.inMilliseconds(keyInterval));
            CURSOR_MOVING_DIRECTIONS.add(new Pair<>(cursorMovingKeys[cursorMovingKeyIndex], p.getLeft()));
            cursorMovingKeyIndex += 1;
        }

        cursorResetKey = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapCursorResetKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));

        mapLockKey = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapMapLockKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));
    }

    public static AreaMapMenu getInstance() {
        return instance;
    }

    public void update() {
        try {
            MinecraftClient client = MinecraftClient.getInstance();
            if (client == null) return;
            if (client.player == null) return;

            // functional core, imperative shell, for easier testing
            execute(client);
        } catch (Exception e) {
            MainClass.errorLog("An error occurred in AreaMapMenu.", e);
        }
    }

    public void execute(MinecraftClient client) {
        updateConfigs();
        if (!enabled) return;

        if (client.currentScreen == null) {
            if (menuKey.canOpenMenu()) {
                openAreaMapMenu();
                updateMapStates();
            }
        } else {
            if (client.currentScreen instanceof AreaMapMenuGUI) {
                if (menuKey.closeMenuIfMenuKeyPressing()) return;
                handleInMenuActions();
            } else {
                // other menus is opened currently, won't open the menu
                return;
            }
        }

        menuKey.updateStateForNextTick();
        Arrays.stream(cursorMovingKeys).forEach(IntervalKeystroke::updateStateForNextTick);
    }

    private void updateConfigs() {
        AreaMapConfigMap map = AreaMapConfigMap.getInstance();
        this.enabled = map.isEnabled();

        // set key intervals
        Stream.of(Arrays.stream(cursorMovingKeys),
                        Stream.of(cursorResetKey, mapLockKey))
                .flatMap(i -> i)
                .forEach(k -> k.setInterval(Interval.inMilliseconds(map.getDelayInMilliseconds(), k.interval())));
    }

    private void openAreaMapMenu() {
        MinecraftClient.getInstance().setScreen(new AreaMapMenuGUI());
    }

    private void updateMapStates() {
        resetCursorToPlayerPosition();
    }

    private void handleInMenuActions() {
        // move cursor
        for (Pair<IntervalKeystroke, Orientation> p : CURSOR_MOVING_DIRECTIONS) {
            if (p.getLeft().isCooledDownAndTriggered()) {
                Orientation direction = p.getRight();
                moveCursorTowards(direction);
                return;
            }
        }

        if (cursorResetKey.isCooledDownAndTriggered()) {
            resetCursorToPlayerPosition();
        }
    }

    private void moveCursorTowards(Orientation direction) {
        this.cursor = this.cursor.add(direction.vector);
        MainClass.infoLog("Cursor moves " + direction + ": " + cursor);
        Pair<String, String> blockDescription = ReadCrosshair.getInstance().describeBlock(this.cursor, "");
        MainClass.speakWithNarrator(blockDescription.getLeft(), true);
        // TODO Alt + speak position key
//        MainClass.speakWithNarrator(blockDescription.getLeft() + I18n.translate("minecraft_access.other.words_connection") + PlayerPositionUtils.getI18NPosition(), true);
    }

    private void resetCursorToPlayerPosition() {
        cursor = PlayerPositionUtils.getPlayerBlockPosition().orElseThrow();
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.area_map.cursor_reset", PlayerPositionUtils.getI18NPosition()), true);
    }
}
