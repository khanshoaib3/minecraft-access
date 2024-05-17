package com.github.khanshoaib3.minecraft_access.forge;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraftforge.fml.common.Mod;

@Mod(MainClass.MOD_ID)
public class MainClassForge {

    public MainClassForge() {
        MainClass.isForge = true;
        MainClass.init();
    }
}
