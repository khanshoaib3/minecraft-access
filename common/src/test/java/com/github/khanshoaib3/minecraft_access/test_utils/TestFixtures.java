package com.github.khanshoaib3.minecraft_access.test_utils;

import com.github.khanshoaib3.minecraft_access.config.Config;
import net.minecraft.client.MinecraftClient;
import org.mockito.Mockito;

public class TestFixtures {

    /**
     * Aiming to mock logic in "getInstance" method in every "*ConfigMap" class: "if (instance == null) Config.getInstance().loadConfig();"
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void mockConfigAsDefaultValues() {
        Mockito.mockStatic(Config.class).when(Config::getInstance).thenReturn(new MockConfig());
    }
}
