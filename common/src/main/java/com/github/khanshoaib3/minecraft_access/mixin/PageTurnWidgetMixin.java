package com.github.khanshoaib3.minecraft_access.mixin;

import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PageTurnWidget.class)
public class PageTurnWidgetMixin {
    @Shadow @Final private boolean isNextPageButton;

    @Inject(at = @At("HEAD"), method = "renderButton")
    public void renderButton(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo){
        if(this.isNextPageButton)
            ((ClickableWidgetAccessor)this).setMessage(Text.literal(I18n.translate("minecraft_access.menus.book_screen.next_page_button_name")));
        else
            ((ClickableWidgetAccessor)this).setMessage(Text.literal(I18n.translate("minecraft_access.menus.book_screen.previous_page_button_name")));
    }
}
