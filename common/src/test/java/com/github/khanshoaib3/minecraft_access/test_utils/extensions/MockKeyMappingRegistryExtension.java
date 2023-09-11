package com.github.khanshoaib3.minecraft_access.test_utils.extensions;

import dev.architectury.registry.client.keymappings.KeyMappingRegistry;
import org.junit.jupiter.api.extension.AfterAllCallback;
import org.junit.jupiter.api.extension.BeforeAllCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

/**
 * Mock "KeyMappingRegistry.register" in KeyBindingsHandler,
 * which will throw an exception in test environment.
 */
public class MockKeyMappingRegistryExtension implements BeforeAllCallback, AfterAllCallback {
    private MockedStatic<KeyMappingRegistry> ms;

    @Override
    public void beforeAll(ExtensionContext extensionContext) {
        this.ms = Mockito.mockStatic(KeyMappingRegistry.class);
    }

    @Override public void afterAll(ExtensionContext extensionContext) {
        this.ms.close();
    }
}
