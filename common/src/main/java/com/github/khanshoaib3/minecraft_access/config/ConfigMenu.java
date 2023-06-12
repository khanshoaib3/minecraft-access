package com.github.khanshoaib3.minecraft_access.config;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.gui.widget.ButtonWidget;

public class ConfigMenu extends BaseScreen {
    public ConfigMenu(String title) {
        super(title);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget cameraControlsButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.camera_controls_button",
                (button) -> MainClass.infoLog("SAVING..."));
        this.addDrawableChild(cameraControlsButton);

        ButtonWidget inventoryControlsButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.inventory_controls_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(inventoryControlsButton);

        ButtonWidget poiButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.poi_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(poiButton);

        ButtonWidget playerWarningsButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.player_warnings_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(playerWarningsButton);

        ButtonWidget fallDetectorButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.fall_detector_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(fallDetectorButton);

        ButtonWidget readCrosshairButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.read_crosshair_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(readCrosshairButton);

        ButtonWidget narratorMenuButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.narrator_menu_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(narratorMenuButton);

        ButtonWidget otherButton = this.buildButtonWidget("minecraft_access.gui.config_menu.button.other_button",
                (button) -> MainClass.infoLog("CANCEL..."));
        this.addDrawableChild(otherButton);
    }
}
