package com.github.khanshoaib3.minecraft_access;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.utils.MockConfig;
import org.mockito.Mockito;

public class TestFixtures {

    /**
     * And do not access json file to reduce the test suite execution time.
     */
    @SuppressWarnings("ResultOfMethodCallIgnored")
    public static void mockConfigFileAsDefaultValues() {
        Mockito.mockStatic(Config.class).when(Config::getInstance).thenReturn(new MockConfig());
    }
}
