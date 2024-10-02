package com.github.khanshoaib3.minecraft_access.neoforge;

import com.github.khanshoaib3.minecraft_access.Config;
import com.github.khanshoaib3.minecraft_access.MainClass;
import me.shedaniel.autoconfig.AutoConfig;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.gui.IConfigScreenFactory;
import net.neoforged.neoforge.common.NeoForge;

@Mod(MainClass.MOD_ID)
public class MainClassNeoForge {
    public MainClassNeoForge(IEventBus modBus, ModContainer container) {
        MainClass.init();
        MainClass.isNeoForge = true;
        NeoForge.EVENT_BUS.register(GameEventHandler.class);
        modBus.register(ModEventHandler.class);
        container.registerExtensionPoint(IConfigScreenFactory.class, (modContainer, parent) -> AutoConfig.getConfigScreen(Config.class, parent).get());
    }
}
