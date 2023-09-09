package com.github.khanshoaib3.minecraft_access.test_utils;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.RunArgs;
import org.mockito.Mockito;

/**
 * Reusable mock Minecraft Client for testing.
 * Based on Mockito, we can make use of Mockito's techniques,
 * but with this additional layer of wrap, it's easier to get parameters than using Mockito ArgumentCaptor.
 * And we can easily write and reuse our own custom logic.
 */
public class MockMinecraftClient extends MinecraftClient {

    private final MinecraftClient mockitoClient;

    private MockMinecraftClient() {
        super(new RunArgs(null, null, null, null, null));
        mockitoClient = Mockito.mock(MockMinecraftClient.class);
    }

    public static MockMinecraftClient buildMockMinecraftClient() {
        return new MockMinecraftClient();
    }

    @SuppressWarnings("unused")
    public MinecraftClient mockito() {
        return mockitoClient;
    }
}
