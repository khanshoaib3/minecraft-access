package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

@SuppressWarnings("DataFlowIssue")
public class POIConfigMenu extends BaseScreen {
    public POIConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget poiBlocksButton = this.buildButtonWidget("minecraft_access.gui.poi_config_menu.button.poi_blocks_button",
                (button) -> this.client.setScreen(new POIBlocksConfigMenu("poi_blocks_config_menu", this)));
        this.addDrawableChild(poiBlocksButton);

        ButtonWidget poiEntitiesButton = this.buildButtonWidget("minecraft_access.gui.poi_config_menu.button.poi_entities_button",
                (button) -> this.client.setScreen(new POIEntitiesConfigMenu("poi_entities_config_menu", this)));
        this.addDrawableChild(poiEntitiesButton);

        ButtonWidget poiLockingButton = this.buildButtonWidget("minecraft_access.gui.poi_config_menu.button.poi_locking_button",
                (button) -> this.client.setScreen(new POILockingConfigMenu("poi_locking_config_menu", this)));
        this.addDrawableChild(poiLockingButton);
    }
}

class POIBlocksConfigMenu extends BaseScreen {
    public POIBlocksConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();
    }
}

class POIEntitiesConfigMenu extends BaseScreen {
    public POIEntitiesConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();
    }
}

@SuppressWarnings("DataFlowIssue")
class POILockingConfigMenu extends BaseScreen {
    public POILockingConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ButtonWidget featureToggleButton = this.buildButtonWidget("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isEnabled() ? "enabled" : "disabled"),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getPoiConfigMap().getLockingConfigMap().setEnabled(!configMap.getPoiConfigMap().getLockingConfigMap().isEnabled());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.feature_toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isEnabled() ? "enabled" : "disabled"))));
                });
        this.addDrawableChild(featureToggleButton);

        ButtonWidget lockOnBlocksButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isLockOnBlocks() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.lock_on_blocks_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getPoiConfigMap().getLockingConfigMap().setLockOnBlocks(!configMap.getPoiConfigMap().getLockingConfigMap().isLockOnBlocks());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isLockOnBlocks() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.lock_on_blocks_button")
                    )));
                });
        this.addDrawableChild(lockOnBlocksButton);

        ButtonWidget speakDistanceButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isSpeakDistance() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.speak_distance_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getPoiConfigMap().getLockingConfigMap().setSpeakDistance(!configMap.getPoiConfigMap().getLockingConfigMap().isSpeakDistance());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isSpeakDistance() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.speak_distance_button")
                    )));
                },
                true);
        this.addDrawableChild(speakDistanceButton);

        ButtonWidget autoLockEyeOfEnderButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isAutoLockEyeOfEnderEntity() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.auto_lock_eye_of_ender_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getPoiConfigMap().getLockingConfigMap().setAutoLockEyeOfEnderEntity(!configMap.getPoiConfigMap().getLockingConfigMap().isAutoLockEyeOfEnderEntity());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isAutoLockEyeOfEnderEntity() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.auto_lock_eye_of_ender_button")
                    )));
                },
                true);
        this.addDrawableChild(autoLockEyeOfEnderButton);

        ButtonWidget unlockingSoundButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isUnlockingSound() ? "enabled" : "disabled"),
                        I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.unlocking_sound_button")
                ),
                (button) -> {
                    ConfigMap configMap = MainClass.config.getConfigMap();
                    configMap.getPoiConfigMap().getLockingConfigMap().setUnlockingSound(!configMap.getPoiConfigMap().getLockingConfigMap().isUnlockingSound());
                    MainClass.config.setConfigMap(configMap);
                    button.setMessage(Text.of(I18n.translate("minecraft_access.gui.common.button.toggle_button." + (MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().isUnlockingSound() ? "enabled" : "disabled"),
                            I18n.translate("minecraft_access.gui.poi_locking_config_menu.button.unlocking_sound_button")
                    )));
                });
        this.addDrawableChild(unlockingSoundButton);

        ButtonWidget delayButton = this.buildButtonWidget(
                I18n.translate("minecraft_access.gui.common.button.delay",
                        MainClass.config.getConfigMap().getPoiConfigMap().getLockingConfigMap().getDelay()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.POI_LOCKING_DELAY, this)));
        this.addDrawableChild(delayButton);
    }
}