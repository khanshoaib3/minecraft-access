package com.github.khanshoaib3.minecraft_access.fabric;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

public class MainClassFabric implements ModInitializer {

    @Override
    public void onInitialize() {
        MainClass.init();
        ClientTickEvents.END_CLIENT_TICK.register(MainClass::clientTickEventsMethod);
    }
}
