package com.github.khanshoaib3.minecraft_access.gui;

import com.github.khanshoaib3.minecraft_access.features.NarratorMenu;
import io.github.cottonmc.cotton.gui.client.BackgroundPainter;
import io.github.cottonmc.cotton.gui.client.LightweightGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WButton;
import io.github.cottonmc.cotton.gui.widget.WGridPanel;
import io.github.cottonmc.cotton.gui.widget.WLabel;
import io.github.cottonmc.cotton.gui.widget.data.Color;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

public class NarratorMenuGUI extends LightweightGuiDescription {
    public NarratorMenuGUI() {
        WGridPanel rootGridPanel = new WGridPanel();

        setRootPanel(rootGridPanel);

        WButton blockAndFluidTargetInformationButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info")));
        blockAndFluidTargetInformationButton.setOnClick(NarratorMenu::getBlockAndFluidTargetInformation);
        rootGridPanel.add(blockAndFluidTargetInformationButton, 1, 1, 9, 1);

        WButton blockAndFluidTargetPositionButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position")));
        blockAndFluidTargetPositionButton.setOnClick(NarratorMenu::getBlockAndFluidTargetPosition);
        rootGridPanel.add(blockAndFluidTargetPositionButton, 11, 1, 9, 1);

        WButton entityTargetInformationButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.entity_target_info")));
        entityTargetInformationButton.setOnClick(NarratorMenu::getEntityTargetInformation);
        rootGridPanel.add(entityTargetInformationButton, 1, 3, 9, 1);

        WButton entityTargetPositionButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.entity_target_position")));
        entityTargetPositionButton.setOnClick(NarratorMenu::getEntityTargetPosition);
        rootGridPanel.add(entityTargetPositionButton, 11, 3, 9, 1);

        WButton lightLevelButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.light_level")));
        lightLevelButton.setOnClick(NarratorMenu::getLightLevel);
        rootGridPanel.add(lightLevelButton, 1, 5, 9, 1);

        WButton biomeButton = new WButton(Text.of(I18n.translate("minecraft_access.narrator_menu.gui.button.biome")));
        biomeButton.setOnClick(NarratorMenu::getBiome);
        rootGridPanel.add(biomeButton, 11, 5, 9, 1);

        WLabel labelForPadding = new WLabel(Text.of(""), Color.RED.toRgb());
        rootGridPanel.add(labelForPadding, 0, 6, 21, 1);

        rootGridPanel.validate(this);

        // TODO add time of day and map number buttons to trigger above buttons
    }

    @Override
    public void addPainters() {
        this.rootPanel.setBackgroundPainter(BackgroundPainter.createColorful(Color.LIGHT_GRAY_DYE.toRgb()));
    }
}