package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class OtherConfigMenu extends BaseScreen {
    public OtherConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget biomeIndicatorButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isBiomeIndicatorEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.biome_indicator_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setBiomeIndicatorEnabled(!configMap.getOtherConfigsMap().isBiomeIndicatorEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isBiomeIndicatorEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.biome_indicator_button")
                    )));
                });
        this.addDrawableChild(biomeIndicatorButton);

        ButtonWidget xpIndicatorButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isXpIndicatorEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.xp_indicator_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setXpIndicatorEnabled(!configMap.getOtherConfigsMap().isXpIndicatorEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isXpIndicatorEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.xp_indicator_button")
                    )));
                });
        this.addDrawableChild(xpIndicatorButton);

        ButtonWidget speakFacingDirectionButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isFacingDirectionEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.speak_facing_direction_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setFacingDirectionEnabled(!configMap.getOtherConfigsMap().isFacingDirectionEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isFacingDirectionEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.speak_facing_direction_button")
                    )));
                },
                true);
        this.addDrawableChild(speakFacingDirectionButton);

        ButtonWidget healthNHungerButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isHealthNHungerEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.health_n_hunger_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setHealthNHungerEnabled(!configMap.getOtherConfigsMap().isHealthNHungerEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isHealthNHungerEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.health_n_hunger_button")
                    )));
                },
                true);
        this.addDrawableChild(healthNHungerButton);

        ButtonWidget positionNarratorButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isPositionNarratorEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.position_narrator_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setPositionNarratorEnabled(!configMap.getOtherConfigsMap().isPositionNarratorEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isPositionNarratorEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.position_narrator_button")
                    )));
                });
        this.addDrawableChild(positionNarratorButton);

        ButtonWidget positionNarratorFormatButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_string_value",
                        I18n.translate("minecraft_access.gui.other_config_menu.button.position_narrator_format_button"),
                        MainClass.config.getConfigMap().getInventoryControlsConfigMap().getRowAndColumnFormat()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.OTHER_POSITION_NARRATOR_FORMAT, this)));
        positionNarratorFormatButton.active = false;
        this.addDrawableChild(positionNarratorFormatButton);

        ButtonWidget use12HourFormatButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isUse12HourTimeFormat() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.use_12_hour_format_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setUse12HourTimeFormat(!configMap.getOtherConfigsMap().isUse12HourTimeFormat());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isUse12HourTimeFormat() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.use_12_hour_format_button")
                    )));
                });
        this.addDrawableChild(use12HourFormatButton);

        ButtonWidget speakActionBarButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isActionBarEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.speak_action_bar_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setActionBarEnabled(!configMap.getOtherConfigsMap().isActionBarEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isActionBarEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.speak_action_bar_button")
                    )));
                },
                true);
        this.addDrawableChild(speakActionBarButton);

        ButtonWidget speakFishingHarvestButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isFishingHarvestEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.speak_fishing_harvest_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setFishingHarvestEnabled(!configMap.getOtherConfigsMap().isFishingHarvestEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isFishingHarvestEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.speak_fishing_harvest_button")
                    )));
                },
                true);
        this.addDrawableChild(speakFishingHarvestButton);

        ButtonWidget menuFixButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isMenuFixEnabled() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.menu_fix_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setMenuFixEnabled(!configMap.getOtherConfigsMap().isMenuFixEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isMenuFixEnabled() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.menu_fix_button")
                    )));
                });
        this.addDrawableChild(menuFixButton);

        ButtonWidget debugModeButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isDebugMode() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.other_config_menu.button.debug_mode_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getOtherConfigsMap().setDebugMode(!configMap.getOtherConfigsMap().isDebugMode());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getOtherConfigsMap().isDebugMode() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.other_config_menu.button.debug_mode_button")
                    )));
                });
        this.addDrawableChild(debugModeButton);
    }
}
