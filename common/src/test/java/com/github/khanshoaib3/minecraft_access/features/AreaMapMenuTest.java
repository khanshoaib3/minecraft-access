package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenu;
import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenuGUI;
import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeAction;
import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClient;
import com.github.khanshoaib3.minecraft_access.test_utils.TestFixtures;
import net.minecraft.client.MinecraftClient;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.MockedStatic;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class AreaMapMenuTest {
    private static MockMinecraftClient mockClient;
    private static MockedStatic<MinecraftClient> mockedStaticClient;
    private static MockKeystrokeAction menuKeyAction;

    @BeforeAll
    static void beforeAll() {
        TestFixtures.mockConfigAsDefaultValues();
        mockClient = MockMinecraftClient.buildMockMinecraftClient();
        mockedStaticClient = TestFixtures.mockMinecraftClientWith(mockClient);
        TestFixtures.mockKeyMappingRegistry();

        mockKeyStrokes();
    }

    private static void mockKeyStrokes() {
        menuKeyAction = MockKeystrokeAction.pressed();
        menuKeyAction.mockKeystrokeOf(AreaMapMenu.class, "menuKey");
    }

    @AfterAll
    static void afterAll() {
        mockedStaticClient.close();
    }

    @Test
    void testOpenMenuWithMenuKey() {
        menuKeyAction.press();
        oneTickForward();
        menuKeyAction.release();
        oneTickForward();
        mockClient.verifyOpeningMenuOf(AreaMapMenuGUI.class);
    }

    private static void oneTickForward() {
        AreaMapMenu.getInstance().execute(mockClient.mockito());
    }
}