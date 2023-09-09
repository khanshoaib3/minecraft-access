package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenu;
import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenuGUI;
import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeAction;
import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClientWrapper;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.FeatureText;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockMinecraftClient;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

@FeatureText
class AreaMapMenuTest {
    @MockMinecraftClient
    MockMinecraftClientWrapper mockClient;
    static MockKeystrokeAction menuKeyAction;

    @BeforeAll
    static void beforeAll() {
        menuKeyAction = MockKeystrokeAction.pressed();
        menuKeyAction.mockKeystrokeOf(AreaMapMenu.class, "menuKey");
    }

    @BeforeEach
    void setUp() {
        menuKeyAction.resetTargetInnerState();
    }

    @Test
    void testOpenMenuWithMenuKey() {
        menuKeyAction.press();
        oneTickForward();
        menuKeyAction.release();
        oneTickForward();
        mockClient.verifyOpeningMenuOf(AreaMapMenuGUI.class);
    }

    @Test
    void testCloseMenuWithMenuKey() {
        menuKeyAction.press();
        mockClient.setScreen(AreaMapMenuGUI.class);
        oneTickForward();
        mockClient.verifyClosingMenu();
    }

    private void oneTickForward() {
        AreaMapMenu.getInstance().execute(mockClient.mockito());
    }
}