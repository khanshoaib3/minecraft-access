package com.github.khanshoaib3.minecraft_access.config.config_menus;

import com.github.khanshoaib3.minecraft_access.config.Config;
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

        Function<Boolean, String> enablePartialSpeakingText = featureToggleButtonMessageWith("minecraft_access.gui.read_crosshair_config_menu.button.partial_speaking_button");
        ButtonWidget enablePartialSpeakingButton = this.buildButtonWidget(
                  enablePartialSpeakingText.apply(map.isEnablePartialSpeaking()),
                (button) -> {
                    map.setEnablePartialSpeaking(!map.isEnablePartialSpeaking());
                    button.setMessage(Text.of(enablePartialSpeakingText.apply(map.isEnablePartialSpeaking())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(enablePartialSpeakingButton);

        Function<Boolean, String> partialSpeakingWhitelistModeText = featureToggleButtonMessageWith("minecraft_access.gui.read_crosshair_config_menu.button.partial_speaking_whitelist_mode_button");
        ButtonWidget partialSpeakingWhitelistModeButton = this.buildButtonWidget(
                  partialSpeakingWhitelistModeText.apply(map.isPartialSpeakingWhitelistMode()),
                (button) -> {
                    map.setPartialSpeakingWhitelistMode(!map.isPartialSpeakingWhitelistMode());
                    button.setMessage(Text.of(partialSpeakingWhitelistModeText.apply(map.isPartialSpeakingWhitelistMode())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(partialSpeakingWhitelistModeButton);

        Function<Boolean, String> partialSpeakingFuzzyModeText = featureToggleButtonMessageWith("minecraft_access.gui.read_crosshair_config_menu.button.partial_speaking_fuzzy_mode_button");
        ButtonWidget partialSpeakingFuzzyModeButton = this.buildButtonWidget(
                  partialSpeakingFuzzyModeText.apply(map.isPartialSpeakingFuzzyMode()),
                (button) -> {
                    map.setPartialSpeakingFuzzyMode(!map.isPartialSpeakingFuzzyMode());
                    button.setMessage(Text.of(partialSpeakingFuzzyModeText.apply(map.isPartialSpeakingFuzzyMode())));
                    Config.getInstance().writeJSON();
                });
        this.addDrawableChild(partialSpeakingFuzzyModeButton);
    }
}
