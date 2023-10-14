package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.config_maps.AreaMapConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

import java.util.Objects;

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

        ButtonWidget delayButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.delay", initMap.getDelayInMilliseconds()),
                (button) -> Objects.requireNonNull(this.client).setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.AREA_MAP_DELAY, this)));
        this.addDrawableChild(delayButton);

        ValueEntryMenu.CONFIG_TYPE verticalBound = ValueEntryMenu.CONFIG_TYPE.AREA_MAP_VERTICAL_BOUND;
        ButtonWidget verticalBoundButton = this.buildButtonWidget(
                verticalBound.buildButtonText("minecraft_access.gui.area_map_config_menu.button.vertical_bound"),
                (button) -> Objects.requireNonNull(this.client).setScreen(new ValueEntryMenu("value_entry_menu", verticalBound, this)));
        this.addDrawableChild(verticalBoundButton);

        ValueEntryMenu.CONFIG_TYPE horizontalBound = ValueEntryMenu.CONFIG_TYPE.AREA_MAP_HORIZONTAL_BOUND;
        ButtonWidget horizontalBoundButton = this.buildButtonWidget(
                horizontalBound.buildButtonText("minecraft_access.gui.area_map_config_menu.button.horizontal_bound"),
                (button) -> Objects.requireNonNull(this.client).setScreen(new ValueEntryMenu("value_entry_menu", horizontalBound, this)));
        this.addDrawableChild(horizontalBoundButton);
    }
}
