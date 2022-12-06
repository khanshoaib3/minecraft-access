package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import java.util.List;

@Mixin(BookScreen.class)
public interface BookScreenAccessor {
    @Accessor
    BookScreen.Contents getContents();

    @Accessor
    int getPageIndex();

    @Accessor
    Text getPageIndexText();
}
