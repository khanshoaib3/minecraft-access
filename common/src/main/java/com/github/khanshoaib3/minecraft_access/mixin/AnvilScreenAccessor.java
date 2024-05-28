package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.gui.screen.ingame.AnvilScreen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(AnvilScreen.class)
public interface AnvilScreenAccessor {
    @Accessor
    TextFieldWidget getNameField();

    @Accessor("TOO_EXPENSIVE_TEXT")
    Text getTooExpensiveText();
}
