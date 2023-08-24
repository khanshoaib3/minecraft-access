package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.config_maps.FallDetectorConfigMap;
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

        FallDetectorConfigMap initMap = FallDetectorConfigMap.getInstance();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (initMap.isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    FallDetectorConfigMap map = FallDetectorConfigMap.getInstance();
                    map.setEnabled(!map.isEnabled());
                    Config.getInstance().writeJSON();
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (map.isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget rangeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.range",
                        initMap.getRange()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_RANGE, this)));
        this.addDrawableChild(rangeButton);

        ButtonWidget depthButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.fall_detector_config_menu.button.depth_threshold_button"),
                        initMap.getDepth()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_DEPTH_THRESHOLD, this)));
        this.addDrawableChild(depthButton);

        ButtonWidget playAlternateSoundButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (initMap.isPlayAlternateSound() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.fall_detector_config_menu.button.play_alternate_sound_button")
                ),
                (button) -> {
                    FallDetectorConfigMap map = FallDetectorConfigMap.getInstance();
                    map.setPlayAlternateSound(!map.isPlayAlternateSound());
                    Config.getInstance().writeJSON();
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (map.isPlayAlternateSound() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.fall_detector_config_menu.button.play_alternate_sound_button")
                    )));
                });
        playAlternateSoundButton.active = false;
        this.addDrawableChild(playAlternateSoundButton);

        ButtonWidget volumeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.volume", initMap.getVolume()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_VOLUME, this)));
        this.addDrawableChild(volumeButton);

        ButtonWidget delayButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.delay", initMap.getDelay()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.FALL_DETECTOR_DELAY, this)));
        this.addDrawableChild(delayButton);
    }
}
