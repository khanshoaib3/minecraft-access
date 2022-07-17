package com.shoaib.minecraft_access.mixin;

import com.mojang.text2speech.NarratorLinux;
import com.shoaib.minecraft_access.MainClass;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(NarratorLinux.class)
public class NarratorLinuxMixin {
	@Inject(at = @At("TAIL"), method = " <init>()V")
	public void init(CallbackInfo info) {
		MainClass.LOGGER.info("This line is printed by an example mod mixin!");
	}

	@Inject(at = @At("HEAD"), method = "say")
	public void say(String msg, boolean interrupt, CallbackInfo info)
	{
		MainClass.LOGGER.info("Saying:("+interrupt+")\t"+msg);
	}
}
