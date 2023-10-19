package com.github.khanshoaib3.minecraft_access.test_utils.extensions;


import com.github.khanshoaib3.minecraft_access.test_utils.ExtensionUtils;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockClientPlayerEntityProxy;
import com.github.khanshoaib3.minecraft_access.utils.ClientPlayerEntityProxy;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.BeforeEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.mockito.MockedStatic;

import java.util.Objects;

/**
 * At {@link BeforeEach} phase,
 * assign new {@link MockedStatic} {@link ClientPlayerEntityProxy} instances to first field that tagged with {@link MockClientPlayerEntityProxy}.
 * Close the mocked static instance at {@link AfterEach} phase.
 */
public class MockClientPlayerEntityProxyExtension implements BeforeEachCallback, AfterEachCallback {
    private MockedStatic<ClientPlayerEntityProxy> ms;

    @Override
    public void beforeEach(ExtensionContext extensionContext) {
        this.ms = ExtensionUtils.mockStaticForAnnotatedField(extensionContext, ClientPlayerEntityProxy.class, MockClientPlayerEntityProxy.class);
    }

    @Override
    public void afterEach(ExtensionContext extensionContext) {
        if (Objects.nonNull(this.ms)) this.ms.close();
    }
}
