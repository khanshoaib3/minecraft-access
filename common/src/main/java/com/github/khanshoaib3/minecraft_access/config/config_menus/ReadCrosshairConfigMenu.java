package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.config_maps.RCPartialSpeakingConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.ReadCrosshairConfigMap;
import com.github.khanshoaib3.minecraft_access.utils.BaseScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.text.Text;

import java.util.function.Function;

@SuppressWarnings("DataFlowIssue")
public class ReadCrosshairConfigMenu extends BaseScreen {
    public ReadCrosshairConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        ReadCrosshairConfigMap map = ReadCrosshairConfigMap.getInstance();

        ButtonWidget featureToggleButton = this.buildButtonWidget(featureToggleButtonMessage(map.isEnabled()),
                (button) -> {
                    map.setEnabled(!map.isEnabled());
                    button.setMessage(Text.of(featureToggleButtonMessage(map.isEnabled())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(featureToggleButton);

        Function<Boolean, String> speakBlockSidesText = featureToggleButtonMessageWith("minecraft_access.gui.read_crosshair_config_menu.button.speak_block_sides_button");
        ButtonWidget speakBlockSidesButton = this.buildButtonWidget(
                speakBlockSidesText.apply(map.isSpeakSide()),
                (button) -> {
                    map.setSpeakSide(!map.isSpeakSide());
                    button.setMessage(Text.of(speakBlockSidesText.apply(map.isSpeakSide())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(speakBlockSidesButton);

        Function<Boolean, String> disableConsecutiveBlocksText = featureToggleButtonMessageWith("minecraft_access.gui.read_crosshair_config_menu.button.disable_speaking_consecutive_blocks_button");
        ButtonWidget disableConsecutiveBlocksButton = this.buildButtonWidget(
                disableConsecutiveBlocksText.apply(map.isDisableSpeakingConsecutiveBlocks()),
                (button) -> {
                    map.setDisableSpeakingConsecutiveBlocks(!map.isDisableSpeakingConsecutiveBlocks());
                    button.setMessage(Text.of(disableConsecutiveBlocksText.apply(map.isDisableSpeakingConsecutiveBlocks())));
                    Config.getInstance().writeJSON();
                },
                true);
        this.addDrawableChild(disableConsecutiveBlocksButton);

        ButtonWidget repeatSpeakingIntervalButton = this.buildButtonWidget(
                floatValueButtonMessageWith("minecraft_access.gui.read_crosshair_config_menu.button.repeat_speaking_interval_button",
                        map.getRepeatSpeakingInterval()),
                (button) -> this.client.setScreen(new ValueEntryMenu("value_entry_menu", ValueEntryMenu.CONFIG_TYPE.READ_CROSSHAIR_REPEAT_SPEAKING_INTERVAL, this)),
                true);
        this.addDrawableChild(repeatSpeakingIntervalButton);

        ButtonWidget enablePartialSpeakingButton = this.buildButtonWidget("minecraft_access.gui.read_crosshair_config_menu.button.partial_speaking_menu_button",
                (button) -> this.client.setScreen(new RCPartialSpeakingConfigMenu("rc_partial_speaking_menu", this)));
        this.addDrawableChild(enablePartialSpeakingButton);
    }
}

class RCPartialSpeakingConfigMenu extends BaseScreen {

    public RCPartialSpeakingConfigMenu(String title, BaseScreen previousScreen) {
        super(title, previousScreen);
    }

    @Override
    protected void init() {
        super.init();

        RCPartialSpeakingConfigMap initMap = RCPartialSpeakingConfigMap.getInstance();

        ButtonWidget featureToggleButton = this.buildButtonWidget(featureToggleButtonMessage(initMap.isEnabled()),
                (button) -> {
                    RCPartialSpeakingConfigMap map = RCPartialSpeakingConfigMap.getInstance();
                    map.setEnabled(!map.isEnabled());
                    button.setMessage(Text.of(featureToggleButtonMessage(map.isEnabled())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(featureToggleButton);

        Function<Boolean, String> partialSpeakingWhitelistModeText = featureToggleButtonMessageWith("minecraft_access.gui.rc_partial_speaking_menu.button.partial_speaking_whitelist_mode_button");
        ButtonWidget partialSpeakingWhitelistModeButton = this.buildButtonWidget(
                partialSpeakingWhitelistModeText.apply(initMap.isPartialSpeakingWhitelistMode()),
                (button) -> {
                    RCPartialSpeakingConfigMap map = RCPartialSpeakingConfigMap.getInstance();
                    map.setPartialSpeakingWhitelistMode(!map.isPartialSpeakingWhitelistMode());
                    button.setMessage(Text.of(partialSpeakingWhitelistModeText.apply(map.isPartialSpeakingWhitelistMode())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(partialSpeakingWhitelistModeButton);

        Function<Boolean, String> partialSpeakingFuzzyModeText = featureToggleButtonMessageWith("minecraft_access.gui.rc_partial_speaking_menu.button.partial_speaking_fuzzy_mode_button");
        ButtonWidget partialSpeakingFuzzyModeButton = this.buildButtonWidget(
                partialSpeakingFuzzyModeText.apply(initMap.isPartialSpeakingFuzzyMode()),
                (button) -> {
                    RCPartialSpeakingConfigMap map = RCPartialSpeakingConfigMap.getInstance();
                    map.setPartialSpeakingFuzzyMode(!map.isPartialSpeakingFuzzyMode());
                    button.setMessage(Text.of(partialSpeakingFuzzyModeText.apply(map.isPartialSpeakingFuzzyMode())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(partialSpeakingFuzzyModeButton);
    }
}