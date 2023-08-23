package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.config_maps.PlayerWarningConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class PlayerWarningsConfigMenu extends BaseScreen {
    public PlayerWarningsConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        PlayerWarningConfigMap initMap = PlayerWarningConfigMap.getInstance();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (initMap.isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    PlayerWarningConfigMap map = PlayerWarningConfigMap.getInstance();
                    map.setEnabled(!map.isEnabled());
                    Config.getInstance().writeJSON();
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (map.isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget playSoundButton = this.buildButtonWidget("minecraft_access.gui.common.button.play_sound_toggle_button." + (initMap.isPlaySound() ? "enabled" : "disabled"),
                (button) -> {
                    PlayerWarningConfigMap map = PlayerWarningConfigMap.getInstance();
                    map.setPlaySound(!map.isPlaySound());
                    Config.getInstance().writeJSON();
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.play_sound_toggle_button." + (map.isPlaySound() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(playSoundButton);

        ButtonWidget firstHealthThresholdButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.player_warnings_config_menu.button.first_health_threshold_button"),
                        initMap.getFirstHealthThreshold()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.PLAYER_WARNINGS_FIRST_HEALTH_THRESHOLD, this)));
        this.addDrawableChild(firstHealthThresholdButton);

        ButtonWidget secondHealthThresholdButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.player_warnings_config_menu.button.second_health_threshold_button"),
                        initMap.getSecondHealthThreshold()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.PLAYER_WARNINGS_SECOND_HEALTH_THRESHOLD, this)));
        this.addDrawableChild(secondHealthThresholdButton);

        ButtonWidget hungerThresholdButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.player_warnings_config_menu.button.hunger_threshold_button"),
                        initMap.getHungerThreshold()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.PLAYER_WARNINGS_HUNGER_THRESHOLD, this)));
        this.addDrawableChild(hungerThresholdButton);

        ButtonWidget airThresholdButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.player_warnings_config_menu.button.air_threshold_button"),
                        initMap.getAirThreshold()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.PLAYER_WARNINGS_AIR_THRESHOLD, this)));
        this.addDrawableChild(airThresholdButton);
    }
}
