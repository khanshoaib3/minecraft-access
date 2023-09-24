package com.github.khanshoaib3.minecraft_access.features.area_map_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.config_maps.AreaMapConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.KeyBindingsHandler;
import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;
import com.github.khanshoaib3.minecraft_access.utils.condition.IntervalKeystroke;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import com.github.khanshoaib3.minecraft_access.utils.condition.MenuKeystroke;
import com.github.khanshoaib3.minecraft_access.utils.position.Orientation;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.system.KeyUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.util.Pair;
import net.minecraft.util.math.BlockPos;

import java.util.Arrays;
import java.util.Set;
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
    public static final Set<Pair<IntervalKeystroke, Orientation>> CURSOR_MOVING_DIRECTIONS;

    private boolean enabled;
    private BlockPos cachedPlayerPos;
    private BlockPos cursor;

    static {
        instance = new AreaMapMenu();
        // config keystroke conditions
        menuKey = new MenuKeystroke(() -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapMenuKey));

        int keyInterval = AreaMapConfigMap.getInstance().getDelayInMilliseconds();
        cursorMovingKeys[0] = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapNorthKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));
        cursorMovingKeys[1] = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapSouthKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));
        cursorMovingKeys[2] = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapWestKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));
        cursorMovingKeys[3] = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapEastKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));
        cursorMovingKeys[4] = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapUpKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));
        cursorMovingKeys[5] = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapDownKey),
                Keystroke.TriggeredAt.PRESSING,
                Interval.inMilliseconds(keyInterval));

        CURSOR_MOVING_DIRECTIONS = Set.of(
                new Pair<>(cursorMovingKeys[0], Orientation.NORTH),
                new Pair<>(cursorMovingKeys[1], Orientation.SOUTH),
                new Pair<>(cursorMovingKeys[2], Orientation.WEST),
                new Pair<>(cursorMovingKeys[3], Orientation.EAST),
                new Pair<>(cursorMovingKeys[4], Orientation.UP),
                new Pair<>(cursorMovingKeys[5], Orientation.DOWN)
        );

        cursorResetKey = new IntervalKeystroke(
                () -> KeyUtils.isAnyPressed(KeyBindingsHandler.getInstance().areaMapCursorResetKey),
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
                        Stream.of(cursorResetKey))
                .flatMap(i -> i)
                .forEach(k -> k.setInterval(Interval.inMilliseconds(map.getDelayInMilliseconds(), k.interval())));
    }

    private void openAreaMapMenu() {
        MinecraftClient.getInstance().setScreen(new AreaMapMenuGUI());
    }

    private void updateMapStates() {
        BlockPos currentPlayerPos = PlayerPositionUtils.getPlayerBlockPosition().orElseThrow();
        // player haven't moved since last menu opening, no need to update
        if (currentPlayerPos.equals(this.cachedPlayerPos)) return;
        this.cachedPlayerPos = currentPlayerPos;

        this.cursor = currentPlayerPos;
    }

    private void handleInMenuActions() {
        // move cursor
        for (Pair<IntervalKeystroke, Orientation> p : CURSOR_MOVING_DIRECTIONS) {
            if (p.getLeft().isCooledDownAndTriggered()) {
                Orientation direction = p.getRight();
                moveCursorTowards(direction);
                MainClass.infoLog("Cursor moving " + direction + ": " + cursor);
                return;
            }
        }

        // reset cursor
        if (cursorResetKey.isCooledDownAndTriggered()) {
            cursor = PlayerPositionUtils.getPlayerBlockPosition().orElseThrow();
            MainClass.infoLog("Cursor reset to:" + cursor);
        }
    }

    private void moveCursorTowards(Orientation direction) {
        this.cursor = this.cursor.add(direction.vector);
    }
}
