package com.github.khanshoaib3.minecraft_access.test_utils;

import com.github.khanshoaib3.minecraft_access.config.Config;
import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import net.minecraft.client.MinecraftClient;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

public class TestFixtures {

    /**
     * Aiming to mock logic in "getInstance" method in every "*ConfigMap" class: "if (instance == null) Config.getInstance().loadConfig();"
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void mockConfigAsDefaultValues() {
        Mockito.mockStatic(Config.class).when(Config::getInstance).thenReturn(new MockConfig());
    }

    /**
     * Mock "MinecraftClient.getInstance()" that commonly used to get current MinecraftClient singleton instance.
     *
     * @return newly created MockMinecraftClient instance, save it for later verification
     */
    public static MockedStatic<MinecraftClient> mockMinecraftClientWith(MockMinecraftClient mock) {
        MockedStatic<MinecraftClient> mockedStatic = Mockito.mockStatic(MinecraftClient.class);
        // TODO I suspect that this solution can't work when running multiple feature tests at the same time (test thread number >1),
        //  since original MinecraftClient is singleton pattern while we create new MockMinecraftClient instances for each feature test.
        mockedStatic.when(MinecraftClient::getInstance).thenReturn(mock.mockito());
        return mockedStatic;
    }

    /**
     * Mock "KeyMappingRegistry.register" in KeyBindingsHandler,
     * which will throw an exception in test environment.
     */
    public static void mockKeyMappingRegistry() {
        Mockito.mockStatic(KeyMappingRegistry.class);
    }
}
