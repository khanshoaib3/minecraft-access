package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenu;
import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenuGUI;
import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeAction;
import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeActionArray;
import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClientWrapper;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.FeatureText;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockMinecraftClient;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockedStaticPlayerPositionUtils;
import com.github.khanshoaib3.minecraft_access.utils.position.Orientation;
import com.github.khanshoaib3.minecraft_access.utils.position.PlayerPositionUtils;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Vec3i;
import org.apache.logging.log4j.core.util.ReflectionUtil;
import org.jetbrains.annotations.NotNull;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.params.provider.Arguments.arguments;

@FeatureText
class AreaMapMenuTest {
    @MockMinecraftClient
    MockMinecraftClientWrapper mockClient;
    @MockedStaticPlayerPositionUtils
    MockedStatic<PlayerPositionUtils> mockPlayerPositionUtils;
    static MockKeystrokeAction menuKeyAction;
    static MockKeystrokeActionArray cursorMovingKeyActions;
    static MockKeystrokeAction cursorResetKeyAction;
    static MockKeystrokeAction mapLockKeyAction;

    @BeforeAll
    static void beforeAll() {
        menuKeyAction = MockKeystrokeAction.mock(AreaMapMenu.class, "menuKey");
        cursorMovingKeyActions = new MockKeystrokeActionArray(AreaMapMenu.class, "cursorMovingKeys");
        cursorResetKeyAction = MockKeystrokeAction.mock(AreaMapMenu.class, "cursorResetKey");
        mapLockKeyAction = MockKeystrokeAction.mock(AreaMapMenu.class, "mapLockKey");
    }

    @BeforeEach
    void setUp() {
        Set.of(menuKeyAction, cursorResetKeyAction, mapLockKeyAction).forEach(MockKeystrokeAction::resetTargetInnerState);
        cursorMovingKeyActions.resetTargetInnerState();
    }

    @Test
    void testOpenMenuWithMenuKey() {
        openOrCloseAreaMapMenu();
        mockClient.verifyOpeningMenuOf(AreaMapMenuGUI.class);
        assertThat(valueOfMapCursor())
                .as("Cursor should be reset to player position (zero)")
                .isEqualTo(new BlockPos(Vec3i.ZERO));
    }

    /**
     * By pressing and releasing the menu key
     */
    private void openOrCloseAreaMapMenu() {
        menuKeyAction.press();
        oneTickForward();
        menuKeyAction.release();
        oneTickForward();
    }

    private void oneTickForward() {
        AreaMapMenu.getInstance().execute(mockClient.mockito());
    }

    @Test
    void testCloseMenuWithMenuKey() {
        setAreaMapMenuAsOpened();
        openOrCloseAreaMapMenu();
        mockClient.verifyClosingMenu();
    }

    private void setAreaMapMenuAsOpened() {
        mockClient.setScreen(AreaMapMenuGUI.class);
    }

    @ParameterizedTest
    @MethodSource
    void testCursorMovingWithKeys(@NotNull Orientation directionUnderTest, int index) {
        setAreaMapMenuAsOpened();
        cursorMovingKeyActions.get(index).press();
        setMapCursorTo(new BlockPos(Vec3i.ZERO));

        oneTickForward();

        String msg = "Cursor should be same as vector of " + directionUnderTest + " since init position is zero";
        assertThat(valueOfMapCursor()).as(msg).isEqualTo(directionUnderTest.vector);
    }

    static Stream<Arguments> testCursorMovingWithKeys() {
        return Stream.of(
                arguments(Orientation.NORTH, 0),
                arguments(Orientation.SOUTH, 1),
                arguments(Orientation.WEST, 2),
                arguments(Orientation.EAST, 3),
                arguments(Orientation.UP, 4),
                arguments(Orientation.DOWN, 5)
        );
    }

    @Test
    void testCursorResetWithKey() {
        setAreaMapMenuAsOpened();
        BlockPos zeroPos = new BlockPos(Vec3i.ZERO);
        setMapCursorTo(zeroPos.offset(Direction.NORTH));
        cursorResetKeyAction.press();

        oneTickForward();

        assertThat(valueOfMapCursor())
                .as("Cursor should be reset to player position (zero)")
                .isEqualTo(zeroPos);
    }

    @Test
    @DisplayName("test cursor reset when player position changed when reopen the menu")
    void testCursorResetWhenReopenTheMenu() {
        // open the menu, set cursor from null to 0,0,0
        openOrCloseAreaMapMenu();
        assertThat(valueOfMapCursor())
                .as("Cursor should be reset to player position (zero)")
                .isEqualTo(new BlockPos(Vec3i.ZERO));

        // close the menu
        openOrCloseAreaMapMenu();
        BlockPos newPlayerPosition = new BlockPos(new Vec3i(1, 1, 1));
        mockPlayerPositionUtils.when(PlayerPositionUtils::getPlayerBlockPosition).thenReturn(Optional.of(newPlayerPosition));

        // open the menu again
        openOrCloseAreaMapMenu();

        assertThat(valueOfMapCursor())
                .as("Cursor should be reset to player position (1,1,1)")
                .isEqualTo(newPlayerPosition);
    }

    private static void setMapCursorTo(BlockPos pos) {
        try {
            Field cursorField = AreaMapMenu.class.getDeclaredField("cursor");
            cursorField.setAccessible(true);
            ReflectionUtil.setFieldValue(cursorField, AreaMapMenu.getInstance(), pos);
        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        }
    }

    private Vec3i valueOfMapCursor() {
        try {
            Field cursorField = AreaMapMenu.class.getDeclaredField("cursor");
            cursorField.setAccessible(true);
            return (Vec3i) ReflectionUtils.tryToReadFieldValue(cursorField, AreaMapMenu.getInstance()).get();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}