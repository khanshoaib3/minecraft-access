package com.github.khanshoaib3.minecraft_access.features;

import com.github.khanshoaib3.minecraft_access.features.area_map_menu.AreaMapMenu;
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

    @BeforeAll
    static void beforeAll() {
        TestFixtures.mockConfigAsDefaultValues();
        mockClient = MockMinecraftClient.buildMockMinecraftClient();
        mockedStaticClient = TestFixtures.mockMinecraftClientWith(mockClient);
        TestFixtures.mockKeyMappingRegistry();
    }

    @AfterAll
    static void afterAll() {
        mockedStaticClient.close();
    }

    @Test
    void testOpenMenuWithMenuKey() {
        AreaMapMenu.getInstance().execute(mockClient.mockito());
    }
}