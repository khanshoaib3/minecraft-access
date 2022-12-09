package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BookScreen.class)
public class BookScreenMixin {
    @Shadow
    private int pageIndex;
    @Shadow
    private BookScreen.Contents contents;

    @Shadow
    private Text pageIndexText;
    @Shadow private PageTurnWidget nextPageButton;
    @Shadow private PageTurnWidget previousPageButton;
    String previousContent = "";

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return;
        if (minecraftClient.currentScreen == null) {
            previousContent = "";
            return;
        }

        if (!(minecraftClient.currentScreen instanceof BookScreen)) {
            previousContent = "";
            return;
        }

        boolean isLeftAltPressed = InputUtil.isKeyPressed(MinecraftClient.getInstance().getWindow().getHandle(),
                InputUtil.fromTranslationKey("key.keyboard.left.alt").getCode());
        boolean isRPressed = (InputUtil.isKeyPressed(minecraftClient.getWindow().getHandle(),
                InputUtil.fromTranslationKey("key.keyboard.r").getCode()));

        // Repeat current page content and un-focus next and previous page buttons
        if (isLeftAltPressed && isRPressed) {
            if(this.nextPageButton.isFocused()) this.nextPageButton.changeFocus(false);
            if(this.previousPageButton.isFocused()) this.previousPageButton.changeFocus(false);
            previousContent = "";
        }

        int pageIndex = this.pageIndex;
        if (pageIndex < 0 || pageIndex > this.contents.getPageCount())
            return; //Return if the page index is out of bounds

        String currentPageContentString = this.contents.getPage(pageIndex).getString();
        currentPageContentString = "%s \n\n %s".formatted(this.pageIndexText.getString(), currentPageContentString);

        if (!previousContent.equals(currentPageContentString)) {
            previousContent = currentPageContentString;
            MainClass.speakWithNarrator(currentPageContentString, true);
        }
    }
}
