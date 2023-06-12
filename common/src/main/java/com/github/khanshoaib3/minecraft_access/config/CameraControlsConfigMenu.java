package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.MainClass;
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

        ButtonWidget normalRotatingAngleButton = this.buildButtonWidget(I18n.translate("minecraft_access.gui.common.button.button_with_float_value", "Normal Rotating Angle", MainClass.config.getConfigMap().getCameraControlsConfigMap().getNormalRotatingAngle()),
                (button) -> this.client.setScreen(new ValueEntryMenu("TEST", ValueEntryMenu.TYPE.CameraControls_NormalRotatingAngle, this)));
        this.addDrawableChild(normalRotatingAngleButton);
    }
}
