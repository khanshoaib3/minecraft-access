package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class FallDetectorConfigMenu extends BaseScreen {
    public FallDetectorConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getFallDetectorConfigMap().isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getFallDetectorConfigMap().setEnabled(!configMap.getFallDetectorConfigMap().isEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getFallDetectorConfigMap().isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget rangeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.range",
                        MainClass.config.getConfigMap().getFallDetectorConfigMap().getRange()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_RANGE, this)));
        this.addDrawableChild(rangeButton);

        ButtonWidget depthButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.fall_detector_config_menu.button.depth_threshold_button"),
                        MainClass.config.getConfigMap().getFallDetectorConfigMap().getDepth()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_DEPTH_THRESHOLD, this)));
        this.addDrawableChild(depthButton);

        ButtonWidget playAlternateSoundButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getFallDetectorConfigMap().isPlayAlternateSound() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.fall_detector_config_menu.button.play_alternate_sound_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getFallDetectorConfigMap().setPlayAlternateSound(!configMap.getFallDetectorConfigMap().isPlayAlternateSound());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getFallDetectorConfigMap().isPlayAlternateSound() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.fall_detector_config_menu.button.play_alternate_sound_button")
                    )));
                });
        playAlternateSoundButton.active = false;
        this.addDrawableChild(playAlternateSoundButton);

        ButtonWidget volumeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.volume",
                        MainClass.config.getConfigMap().getFallDetectorConfigMap().getVolume()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_VOLUME, this)));
        this.addDrawableChild(volumeButton);

        ButtonWidget delayButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.delay",
                        MainClass.config.getConfigMap().getFallDetectorConfigMap().getDelay()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_DELAY, this)));
        this.addDrawableChild(delayButton);
    }
}
