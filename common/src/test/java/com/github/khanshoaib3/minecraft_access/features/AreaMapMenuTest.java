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
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.platform.commons.util.ReflectionUtils;
import org.mockito.MockedStatic;

import java.lang.reflect.Field;
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

    @BeforeAll
    static void beforeAll() {
        menuKeyAction = MockKeystrokeAction.mock(AreaMapMenu.class, "menuKey");
        cursorMovingKeyActions = new MockKeystrokeActionArray(AreaMapMenu.class, "cursorMovingKeys");
        cursorResetKeyAction = MockKeystrokeAction.mock(AreaMapMenu.class, "cursorResetKey");
    }

    @BeforeEach
    void setUp() {
        menuKeyAction.resetTargetInnerState();
        cursorMovingKeyActions.resetTargetInnerState();
        cursorResetKeyAction.resetTargetInnerState();
    }

    @Test
    void testOpenMenuWithMenuKey() {
        openAreaMapMenu();
        mockClient.verifyOpeningMenuOf(AreaMapMenuGUI.class);
    }

    private void openAreaMapMenu() {
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
        menuKeyAction.press();
        mockClient.setScreen(AreaMapMenuGUI.class);
        oneTickForward();
        mockClient.verifyClosingMenu();
    }

    @ParameterizedTest
    @MethodSource
    void testCursorMoving(@NotNull Orientation directionUnderTest, int index) {
        mockClient.setScreen(AreaMapMenuGUI.class);
        cursorMovingKeyActions.get(index).press();
        setMapCursorTo(new BlockPos(Vec3i.ZERO));

        oneTickForward();

        String msg = "Cursor should be same as vector of " + directionUnderTest + " since init position is zero";
        assertThat(valueOfMapCursor()).as(msg).isEqualTo(directionUnderTest.vector);
    }

    static Stream<Arguments> testCursorMoving() {
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
    void testCursorReset() {
        mockClient.setScreen(AreaMapMenuGUI.class);
        BlockPos zeroPos = new BlockPos(Vec3i.ZERO);
        setMapCursorTo(zeroPos.offset(Direction.NORTH));
        cursorResetKeyAction.press();

        oneTickForward();

        assertThat(valueOfMapCursor())
                .as("Cursor should be reset to player position (zero)")
                .isEqualTo(zeroPos);
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