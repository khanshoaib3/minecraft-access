package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class CameraControlsConfigMenu extends BaseScreen {
    public CameraControlsConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getCameraControlsConfigMap().isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getCameraControlsConfigMap().setEnabled(!configMap.getCameraControlsConfigMap().isEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getCameraControlsConfigMap().isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget normalRotatingAngleButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.camera_controls_config_menu.button.normal_rotating_angle"),
                        MainClass.config.getConfigMap().getCameraControlsConfigMap().getNormalRotatingAngle()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.CAMERA_CONTROLS_NORMAL_ROTATING_ANGLE, this)));
        this.addDrawableChild(normalRotatingAngleButton);

        ButtonWidget modifiedRotatingAngleButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_float_value",
                        I18n.translate("minecraft_access.gui.camera_controls_config_menu.button.modified_rotating_angle"),
                                MainClass.config.getConfigMap().getCameraControlsConfigMap().getModifiedRotatingAngle()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.CAMERA_CONTROLS_MODIFIED_ROTATING_ANGLE, this)));
        this.addDrawableChild(modifiedRotatingAngleButton);

        ButtonWidget delayButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.delay",
                        MainClass.config.getConfigMap().getCameraControlsConfigMap().getDelayInMilliseconds()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.CAMERA_CONTROLS_DELAY, this)));
        this.addDrawableChild(delayButton);
    }
}
