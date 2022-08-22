package com.shoaib.minecraft_access;

import com.shoaib.minecraft_access.narrator.NarratorLinux;
import net.minecraft.client.MinecraftClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.sun.jna.Native;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;

public class MainClass implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("minecraft_access");
	public static NarratorLinux.IDLL INSTANCE = (NarratorLinux.IDLL) Native.load(FabricLoader.getInstance().getGameDir().toString() + "/libspeechdwrapper.so",
			NarratorLinux.IDLL.class);

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		String msg = "Hello from C#!";
		NarratorLinux.IDLL.GoString.ByValue str = new NarratorLinux.IDLL.GoString.ByValue();
		str.p = msg;
		str.n = msg.length();
		INSTANCE.Initialize();
		INSTANCE.Speak(str, true);
	}

	public static void DebugLog(String msg)
	{
		//TODO add debug enabling/disabling logic here
		LOGGER.debug(msg);
	}
}
