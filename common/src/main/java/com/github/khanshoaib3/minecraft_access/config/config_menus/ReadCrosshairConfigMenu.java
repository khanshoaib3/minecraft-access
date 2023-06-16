package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class ReadCrosshairConfigMenu extends BaseScreen {
    public ReadCrosshairConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getReadCrosshairConfigMap().isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getReadCrosshairConfigMap().setEnabled(!configMap.getReadCrosshairConfigMap().isEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getReadCrosshairConfigMap().isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget speakBlockSidesButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getReadCrosshairConfigMap().isSpeakSide() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.read_crosshair_config_menu.button.speak_block_sides_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getReadCrosshairConfigMap().setSpeakSide(!configMap.getReadCrosshairConfigMap().isSpeakSide());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getReadCrosshairConfigMap().isSpeakSide() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.read_crosshair_config_menu.button.speak_block_sides_button")
                    )));
                });
        this.addDrawableChild(speakBlockSidesButton);

        ButtonWidget disableConsecutiveBlocksButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getReadCrosshairConfigMap().isSpeakSide() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.read_crosshair_config_menu.button.disable_speaking_consecutive_blocks_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getReadCrosshairConfigMap().setSpeakSide(!configMap.getReadCrosshairConfigMap().isSpeakSide());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getReadCrosshairConfigMap().isSpeakSide() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.read_crosshair_config_menu.button.disable_speaking_consecutive_blocks_button")
                    )));
                },
                true);
        this.addDrawableChild(disableConsecutiveBlocksButton);

        ButtonWidget repeatSpeakingIntervalButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.read_crosshair_config_menu.button.repeat_speaking_interval_button"),
                        MainClass.config.getConfigMap().getReadCrosshairConfigMap().getRepeatSpeakingInterval()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.READ_CROSSHAIR_REPEAT_SPEAKING_INTERVAL, this)),
                true);
        this.addDrawableChild(repeatSpeakingIntervalButton);
    }
}
