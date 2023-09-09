package com.github.khanshoaib3.minecraft_access.test_utils;

import net.minecraft.client.MinecraftClient;
import org.mockito.Mockito;

/**
 * Reusable mock Minecraft Client for testing.
 * Based on Mockito, we can make use of Mockito's techniques,
 * but with this additional layer of wrap, we can easily write and reuse our own custom logic.
 */
public class MockMinecraftClient {

    private final MinecraftClient mockitoClient;

    private MockMinecraftClient() {
        mockitoClient = Mockito.mock(MinecraftClient.class);
    }

    public static MockMinecraftClient buildMockMinecraftClient() {
        return new MockMinecraftClient();
    }

    public MinecraftClient mockito() {
        return mockitoClient;
    }
}
