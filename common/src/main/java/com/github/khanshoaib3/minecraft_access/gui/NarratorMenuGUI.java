package com.github.khanshoaib3.minecraft_access.gui;

import com.github.khanshoaib3.minecraft_access.features.NarratorMenu;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;

public class NarratorMenuGUI extends Screen {
    public NarratorMenuGUI(String title) {
        super(Text.of(I18n.translate("minecraft_access.gui.screen." + title)));
    }

    @Override
    protected void init() {
        int centerX = this.width / 2;
        int buttonHeight = 20;
        int startY = this.height / 6;

        ButtonWidget blockAndFluidTargetInformationButton = ButtonWidget.builder(Text.translatable("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info"),
                (button) -> NarratorMenu.getBlockAndFluidTargetInformation()).dimensions(centerX - (this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info"))) + 35) / 2, startY, this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_info"))) + 35, buttonHeight).build();
        startY += buttonHeight + buttonHeight / 2;

        ButtonWidget blockAndFluidTargetPositionButton = ButtonWidget.builder(Text.translatable("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position"),
                (button) -> NarratorMenu.getBlockAndFluidTargetPosition()).dimensions(centerX - (this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position"))) + 35) / 2, startY, this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.block_and_fluid_target_position"))) + 35, buttonHeight).build();
        startY += buttonHeight + buttonHeight / 2;

        ButtonWidget entityTargetInformationButton = ButtonWidget.builder(Text.translatable("minecraft_access.narrator_menu.gui.button.entity_target_info"),
                (button) -> NarratorMenu.getEntityTargetInformation()).dimensions(centerX - (this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.entity_target_info"))) + 35) / 2, startY, this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.entity_target_info"))) + 35, buttonHeight).build();
        startY += buttonHeight + buttonHeight / 2;

        ButtonWidget entityTargetPositionButton = ButtonWidget.builder(Text.translatable("minecraft_access.narrator_menu.gui.button.entity_target_position"),
                (button) -> NarratorMenu.getEntityTargetPosition()).dimensions(centerX - (this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.entity_target_position"))) + 35) / 2, startY, this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.entity_target_position"))) + 35, buttonHeight).build();
        startY += buttonHeight + buttonHeight / 2;

        ButtonWidget lightLevelButton = ButtonWidget.builder(Text.translatable("minecraft_access.narrator_menu.gui.button.light_level"),
                (button) -> NarratorMenu.getLightLevel()).dimensions(centerX - (this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.light_level"))) + 35) / 2, startY, this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.light_level"))) + 35, buttonHeight).build();
        startY += buttonHeight + buttonHeight / 2;

        ButtonWidget biomeButton = ButtonWidget.builder(Text.translatable("minecraft_access.narrator_menu.gui.button.biome"),
                (button) -> NarratorMenu.getBiome()).dimensions(centerX - (this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.biome"))) + 35) / 2, startY, this.textRenderer.getWidth(I18n.translate(("minecraft_access.narrator_menu.gui.button.biome"))) + 35, buttonHeight).build();

        // TODO add time of day and map number buttons to trigger above buttons
        this.addDrawableChild(blockAndFluidTargetInformationButton);
        this.addDrawableChild(blockAndFluidTargetPositionButton);
        this.addDrawableChild(entityTargetInformationButton);
        this.addDrawableChild(entityTargetPositionButton);
        this.addDrawableChild(lightLevelButton);
        this.addDrawableChild(biomeButton);
    }

    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
}