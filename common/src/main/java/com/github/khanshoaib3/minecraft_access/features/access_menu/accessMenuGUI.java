package com.github.khanshoaib3.minecraft_access.features.access_menu;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.ConfigMenu;
import com.github.khanshoaib3.minecraft_access.screen_reader.ScreenReaderController;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;

/**
 * GUI screen for the access menu.
 */
public class accessMenuGUI extends Screen {
    int centerX;
    int buttonHeight;
    int marginY;
    int calculatedYPosition;
    int calculatedXPosition;
    int leftColumnX;
    int rightColumnX;
    boolean shouldRenderInLeftColumn;

    public accessMenuGUI(String title) {
        super(Text.of(I18n.translate("minecraft_access.gui.screen." + title)));
    }

    /**
     * The order of buttons initialization should be same as NarratorMenu.menuFunctions
     */
    @Override
    protected void init() {
        this.centerX = this.width / 2;
        this.buttonHeight = 20;
        this.marginY = buttonHeight + buttonHeight / 4;
        this.calculatedYPosition = this.height / 6 - marginY; // Starting Y position (marginY will again be added in buildButtonWidget() so it is subtracted here to equate)
        this.leftColumnX = 10;
        this.rightColumnX = centerX + 10;
        shouldRenderInLeftColumn = true;


        ButtonWidget blockAndFluidTargetInformationButton = this.buildButtonWidget("1", "minecraft_access.access_menu.gui.button.block_and_fluid_target_info",
                (button) -> AccessMenu.getBlockAndFluidTargetInformation());
        this.addDrawableChild(blockAndFluidTargetInformationButton);

        ButtonWidget blockAndFluidTargetPositionButton = this.buildButtonWidget("2", "minecraft_access.access_menu.gui.button.block_and_fluid_target_position",
                (button) -> AccessMenu.getBlockAndFluidTargetPosition());
        this.addDrawableChild(blockAndFluidTargetPositionButton);

        ButtonWidget lightLevelButton = this.buildButtonWidget("3", "minecraft_access.access_menu.gui.button.light_level",
                (button) -> AccessMenu.getLightLevel());
        this.addDrawableChild(lightLevelButton);

        ButtonWidget findWaterButton = this.buildButtonWidget("4", "minecraft_access.access_menu.gui.button.find_water",
                (button) -> MainClass.fluidDetector.findClosestWaterSource(true));
        this.addDrawableChild(findWaterButton);

        ButtonWidget findLavaButton = this.buildButtonWidget("5", "minecraft_access.access_menu.gui.button.find_lava",
                (button) -> MainClass.fluidDetector.findClosestLavaSource(true));
        this.addDrawableChild(findLavaButton);

        ButtonWidget biomeButton = this.buildButtonWidget("6", "minecraft_access.access_menu.gui.button.biome",
                (button) -> AccessMenu.getBiome());
        this.addDrawableChild(biomeButton);

        ButtonWidget timeOfDayButton = this.buildButtonWidget("7", "minecraft_access.access_menu.gui.button.time_of_day",
                (button) -> AccessMenu.getTimeOfDay());
        this.addDrawableChild(timeOfDayButton);

            ButtonWidget xpButton = this.buildButtonWidget("8", "minecraft_access.access_menu.gui.button.xp",
                (button) -> AccessMenu.getXP());
        this.addDrawableChild(xpButton);

        ButtonWidget refreshScreenReaderButton = this.buildButtonWidget("9", "minecraft_access.access_menu.gui.button.refresh_screen_reader",
                (button) -> ScreenReaderController.refreshScreenReader(true));
        this.addDrawableChild(refreshScreenReaderButton);

        ButtonWidget openConfigMenuButton = this.buildButtonWidget("0", "minecraft_access.access_menu.gui.button.open_config_menu",
                (button) -> MinecraftClient.getInstance().setScreen(new ConfigMenu("config_menu")));
        this.addDrawableChild(openConfigMenuButton);
    }

    private ButtonWidget buildButtonWidget(String shortcut, String translationKey, ButtonWidget.PressAction pressAction) {
        Text label = Text.literal(shortcut)
                .append(". ")
                .append(I18n.translate(translationKey));
        int calculatedButtonWidth = this.textRenderer.getWidth(label) + 35;
        calculatedXPosition = shouldRenderInLeftColumn ? leftColumnX : rightColumnX;
        if (shouldRenderInLeftColumn) calculatedYPosition += marginY;
        shouldRenderInLeftColumn = !shouldRenderInLeftColumn;

        return ButtonWidget.builder(label, pressAction)
                .dimensions(calculatedXPosition, calculatedYPosition, calculatedButtonWidth, buttonHeight)
                .build();
    }

    @Override
    public void render(DrawContext context, int mouseX, int mouseY, float delta) {
        this.renderBackground(context, mouseX, mouseY, delta);
        context.drawCenteredTextWithShadow(this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(context, mouseX, mouseY, delta);
    }

    /* Pre 1.20.x
    @Override
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta) {
        this.renderBackground(matrices);
        DrawableHelper.drawCenteredText(matrices, this.textRenderer, this.title, this.width / 2, 15, 16777215);
        super.render(matrices, mouseX, mouseY, delta);
    }
    */
}