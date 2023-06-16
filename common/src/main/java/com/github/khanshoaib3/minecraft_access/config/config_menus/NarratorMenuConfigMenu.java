package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;

@SuppressWarnings("DataFlowIssue")
public class NarratorMenuConfigMenu extends BaseScreen {
    public NarratorMenuConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget fluidDetectorButton = this.buildButtonWidget("minecraft_access.gui.narrator_menu_config_menu.button.fluid_detector_button",
                (button) -> this.client.setScreen(new FluidDetectorConfigMenu("fluid_detector_config_menu", this)),
                true);
        this.addDrawableChild(fluidDetectorButton);
    }
}

@SuppressWarnings("DataFlowIssue")
class FluidDetectorConfigMenu extends BaseScreen {
    public FluidDetectorConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget volumeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.fluid_detector_config_menu.button.volume_button"),
                        MainClass.config.getConfigMap().getNarratorMenuConfigMap().getFluidDetectorConfigMap().getVolume()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.NARRATOR_MENU_VOLUME, this)));
        this.addDrawableChild(volumeButton);

        ButtonWidget rangeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.fluid_detector_config_menu.button.range_button"),
                        MainClass.config.getConfigMap().getNarratorMenuConfigMap().getFluidDetectorConfigMap().getRange()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.NARRATOR_MENU_RANGE, this)));
        this.addDrawableChild(rangeButton);
    }
}