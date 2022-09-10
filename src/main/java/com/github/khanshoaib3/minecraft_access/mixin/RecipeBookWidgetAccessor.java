package com.github.khanshoaib3.minecraft_access.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.RecipeBookGhostSlots;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeGroupButtonWidget;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.ToggleButtonWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetAccessor {
    @Accessor
    RecipeBookGhostSlots getGhostSlots();

    @Accessor
    List<RecipeGroupButtonWidget> getTabButtons();

    @Accessor
    RecipeGroupButtonWidget getCurrentTab();

    @Accessor
    ToggleButtonWidget getToggleCraftableButton();

    @Accessor
    TextFieldWidget getSearchField();
}
