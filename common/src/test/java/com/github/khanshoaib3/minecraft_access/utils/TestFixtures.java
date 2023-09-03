package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.config.Config;
import org.mockito.Mockito;

public class TestFixtures {

    /**
     * And do not access json file to reduce the test suite execution time.
     */
    public static void mockConfigFileAsDefaultValues() {
        Mockito.mockStatic(Config.class).when(Config::getInstance).thenReturn(new MockConfig());
    }
}
