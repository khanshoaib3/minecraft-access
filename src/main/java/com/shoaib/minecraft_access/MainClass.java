package com.shoaib.minecraft_access;

import com.shoaib.minecraft_access.mixin.NarratorManagerAccessor;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.util.NarratorManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MainClass implements ModInitializer {
	public static final Logger LOGGER = LoggerFactory.getLogger("minecraft_access");

	@Override
	public void onInitialize() {
		LOGGER.info("Hello Fabric world!");
		((NarratorManagerAccessor)NarratorManager.INSTANCE).getNarrator().say("Test", true);
	}
}
