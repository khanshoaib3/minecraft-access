package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.gui.screen.recipebook.AnimatedResultButton;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.recipe.Recipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

import java.util.List;

@Mixin(AnimatedResultButton.class)
public interface AnimatedResultButtonAccessor {
    @Invoker
    List<Recipe<?>> callGetResults();

    @Accessor
    RecipeResultCollection getResultCollection();

    @Accessor
    int getCurrentResultIndex();
}
