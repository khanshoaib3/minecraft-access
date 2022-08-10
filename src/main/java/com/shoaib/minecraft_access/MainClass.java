package com.shoaib.minecraft_access;

import com.shoaib.minecraft_access.mixin.NarratorManagerAccessor;
import com.sun.jna.Library;
import com.sun.jna.Native;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.util.NarratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("minecraft_access");
	public class GoString
	{
		public String msg;
		public long len;
		public GoString(String msg, long len)
		{
			this.msg = msg;
			this.len = len;
		}
	}

	public interface IDLL extends Library {
		int Initialize();
		int Speak(GoString text, boolean interrupt);
		int Close();
	}

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		String msg = "Hello from C#!";
		GoString str = new GoString(msg, msg.length());
		IDLL INSTANCE = (IDLL) Native.load(FabricLoader.getInstance().getGameDir().toString()+"/libspeechdwrapper.so", IDLL.class);
		INSTANCE.Initialize();
		INSTANCE.Speak(str, true);
//		((NarratorManagerAccessor)NarratorManager.INSTANCE).getNarrator().say("Test", true);
	}
}
