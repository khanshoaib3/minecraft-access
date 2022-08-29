package com.github.khanshoaib3.minecraft_access;

import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderInterface;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.fabricmc.api.ModInitializer;

public class MainClass implements ModInitializer {
	private static final Logger LOGGER = LoggerFactory.getLogger("minecraft_access");
	private static ScreenReaderInterface screenReader = null;

	@Override
	public void onInitialize() {
		LOGGER.info("Initializing Minecraft Access");
		String msg = "Initializing Minecraft Access";

		setScreenReader(ScreenReaderController.getAvailable());
		if(getScreenReader().isInitialized())
			getScreenReader().say(msg, true);
	}

	public static void DebugLog(String msg)
	{
		//TODO add debug enabling/disabling logic here
		LOGGER.info(msg);
	}

	public static ScreenReaderInterface getScreenReader() {
		return MainClass.screenReader;
	}

	public static void setScreenReader(ScreenReaderInterface screenReader) {
		MainClass.screenReader = screenReader;
	}
}
