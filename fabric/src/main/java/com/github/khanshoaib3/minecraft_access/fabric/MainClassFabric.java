package com.github.khanshoaib3.minecraft_access.fabric;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.fabricmc.api.ModInitializer;

public class MainClassFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        MainClass.init();
    }
}
