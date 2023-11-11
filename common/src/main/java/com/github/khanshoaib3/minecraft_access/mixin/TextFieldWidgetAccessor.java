package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.gui.widget.TextFieldWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(TextFieldWidget.class)
public interface TextFieldWidgetAccessor {
    @Accessor
    boolean getFocusUnlocked();

    @SuppressWarnings("BooleanMethodIsAlwaysInverted")
    @Invoker
    boolean callIsActive();

    @Invoker
    String callGetSelectedText();

    @Invoker
    int callGetWordSkipPosition(int wordOffset);

    @Invoker
    int callGetCursorPosWithOffset(int offset);
}
