package com.shoaib.minecraft_access.mixin;

import com.shoaib.minecraft_access.MainClass;
import com.shoaib.minecraft_access.narrator.NarratorLinux;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(com.mojang.text2speech.NarratorLinux.class)
public class NarratorLinuxMixin {


	@Inject(at = @At("TAIL"), method = " <init>()V")
	public void init(CallbackInfo info) {
		MainClass.LOGGER.info("This line is printed by an example mod mixin!");
	}

	@Inject(at = @At("HEAD"), method = "say", remap = false, cancellable = true)
	public void say(String msg, boolean interrupt, CallbackInfo info)
	{
		MainClass.LOGGER.info("Saying:("+interrupt+")\t"+msg);
		NarratorLinux.IDLL.GoString.ByValue str = new NarratorLinux.IDLL.GoString.ByValue();
		str.p = msg;
		str.n = msg.length();
		MainClass.INSTANCE.Speak(str, interrupt);
		info.cancel();
	}

	// Always set as active even if `flite` library is not installed in system
	@Inject(at = @At("TAIL"), method="active", cancellable = true, remap = false)
	public void active(CallbackInfoReturnable<Boolean> cir)
	{
		cir.setReturnValue(true);
	}
}
