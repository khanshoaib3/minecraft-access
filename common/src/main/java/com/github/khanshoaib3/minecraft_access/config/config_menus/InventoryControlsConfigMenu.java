package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class InventoryControlsConfigMenu extends BaseScreen {
    public InventoryControlsConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getInventoryControlsConfigMap().isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getInventoryControlsConfigMap().setEnabled(!configMap.getInventoryControlsConfigMap().isEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getInventoryControlsConfigMap().isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget autoOpenRecipeBookButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getInventoryControlsConfigMap().isAutoOpenRecipeBook() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.inventory_controls_config_menu.button.auto_open_recipe_book")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getInventoryControlsConfigMap().setAutoOpenRecipeBook(!configMap.getInventoryControlsConfigMap().isAutoOpenRecipeBook());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getInventoryControlsConfigMap().isAutoOpenRecipeBook() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(autoOpenRecipeBookButton);

        ButtonWidget rowNColumnButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.button_with_string_value",
                        I18n.translate("minecraft_access.gui.inventory_controls_config_menu.button.row_and_column_format"),
                        MainClass.config.getConfigMap().getInventoryControlsConfigMap().getRowAndColumnFormat()
                ),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.INVENTORY_CONTROLS_ROW_N_COLUMN_FORMAT, this)));
        rowNColumnButton.active = false;
        this.addDrawableChild(rowNColumnButton);

        ButtonWidget delayButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.delay",
                        MainClass.config.getConfigMap().getInventoryControlsConfigMap().getDelayInMilliseconds()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.INVENTORY_CONTROLS_DELAY, this)));
        this.addDrawableChild(delayButton);
    }
}
