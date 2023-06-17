package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class NarratorMenuConfigMenu extends BaseScreen {
    public NarratorMenuConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getNarratorMenuConfigMap().isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getNarratorMenuConfigMap().setEnabled(!configMap.getNarratorMenuConfigMap().isEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getNarratorMenuConfigMap().isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget fluidDetectorButton = this.buildButtonWidget("minecraft_access.gui.narrator_menu_config_menu.button.fluid_detector_button",
                (button) -> this.client.setScreen(new FluidDetectorConfigMenu("fluid_detector_config_menu", this)));
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
                I18n.translate("minecraft_access.gui.common.button.volume",
                        MainClass.config.getConfigMap().getNarratorMenuConfigMap().getFluidDetectorConfigMap().getVolume()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.NARRATOR_MENU_VOLUME, this)));
        this.addDrawableChild(volumeButton);

        ButtonWidget rangeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.range",
                        MainClass.config.getConfigMap().getNarratorMenuConfigMap().getFluidDetectorConfigMap().getRange()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.NARRATOR_MENU_RANGE, this)));
        this.addDrawableChild(rangeButton);
    }
}