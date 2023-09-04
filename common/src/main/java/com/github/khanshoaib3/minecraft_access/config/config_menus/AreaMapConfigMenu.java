package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.config_maps.AreaMapConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

public class AreaMapConfigMenu extends BaseScreen {
    public AreaMapConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        AreaMapConfigMap initMap = AreaMapConfigMap.getInstance();

        ButtonWidget featureToggleButton = this.buildButtonWidget(featureToggleButtonMessage(initMap.isEnabled()),
                (button) -> {
                    AreaMapConfigMap map = AreaMapConfigMap.getInstance();
                    map.setEnabled(!map.isEnabled());
                    button.setMessage(Text.of(featureToggleButtonMessage(map.isEnabled())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(featureToggleButton);
    }
}
