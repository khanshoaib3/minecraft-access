package com.github.khanshoaib3.minecraft_access.test_utils.extensions;

import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClientWrapper;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockMinecraftClient;
import net.minecraft.client.MinecraftClient;
import org.junit.jupiter.api.extension.AfterTestExecutionCallback;
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.MockedStatic;
import org.mockito.Mockito;

import java.util.Arrays;

/**
 * At {@link BeforeTestExecutionCallback} phase, assign new {@link MockMinecraftClientWrapper} instances to all fields that tagged with {@link MockMinecraftClient}.
 */
public class MockMinecraftClientExtension implements BeforeTestExecutionCallback, AfterTestExecutionCallback {
    private MockedStatic<MinecraftClient> ms;

    @Override public void beforeTestExecution(ExtensionContext extensionContext) {
        Object testInstance = extensionContext.getRequiredTestInstance();
        Arrays.stream(testInstance.getClass().getDeclaredFields())
                .filter(f -> f.isAnnotationPresent(MockMinecraftClient.class))
                .forEach(f -> {
                    this.ms = Mockito.mockStatic(MinecraftClient.class);
                    MockMinecraftClientWrapper wrapper = new MockMinecraftClientWrapper();

                    // Mock "MinecraftClient.getInstance()" that commonly used to get current MinecraftClient singleton instance.
                    this.ms.when(MinecraftClient::getInstance).thenReturn(wrapper.mockito());

                    try {
                        f.setAccessible(true);
                        f.set(testInstance, wrapper);
                    } catch (IllegalAccessException e) {
                        throw new RuntimeException(e);
                    }
                });
    }

    @Override public void afterTestExecution(ExtensionContext extensionContext) {
        this.ms.close();
    }
}
