package com.github.khanshoaib3.minecraft_access.gui;

import com.github.khanshoaib3.minecraft_access.MainClass;
import io.github.cottonmc.cotton.gui.GuiDescription;
import io.github.cottonmc.cotton.gui.client.CottonClientScreen;
import net.minecraft.client.resource.language.I18n;

public class ConfigScreen extends CottonClientScreen {
    public ConfigScreen(GuiDescription description, String title) {
        super(description);
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.gui.screen." + title), true);
    }
}