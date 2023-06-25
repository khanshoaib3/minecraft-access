package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.KeyUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BookEditScreen.class)
public class BookEditScreenMixin {
    @Shadow private int currentPage;
    @Final @Shadow private List<String> pages;

    @Shadow private Text pageIndicatorText;
    @Shadow private PageTurnWidget nextPageButton;
    @Shadow private PageTurnWidget previousPageButton;
    @Shadow private boolean signing;

    @Shadow @Final private static Text FINALIZE_WARNING_TEXT;
    @Shadow @Final private static Text EDIT_TITLE_TEXT;

    @Shadow private ButtonWidget cancelButton;
    @Shadow private ButtonWidget finalizeButton;
    @Shadow private ButtonWidget signButton;
    @Shadow private ButtonWidget doneButton;

    @Shadow private String title;
    boolean firstTimeInSignMenu = true;
    String previousContent = "";

    @Inject(at = @At("HEAD"), method = "render")
    public void render(MatrixStack matrices, int mouseX, int mouseY, float delta, CallbackInfo callbackInfo) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return;
        if (minecraftClient.currentScreen == null) return;

        boolean isSpaceOrEnterPressed = KeyUtils.isEnterPressed() || KeyUtils.isSpacePressed();

        if (this.cancelButton.isFocused() && isSpaceOrEnterPressed) {
            this.cancelButton.onPress();
            return;
        }

        if (this.finalizeButton.isFocused() && isSpaceOrEnterPressed) {
            this.finalizeButton.onPress();
            return;
        }

        if (this.doneButton.isFocused() && isSpaceOrEnterPressed) {
            this.doneButton.onPress();
            return;
        }

        if (this.signButton.isFocused() && isSpaceOrEnterPressed) {
            this.signButton.onPress();
            return;
        }

        if (this.signing) {
            String currentTitle = this.title.trim();

            if (!previousContent.equals(currentTitle)) {
                previousContent = currentTitle;
                if(firstTimeInSignMenu){
                    firstTimeInSignMenu = false;
                    currentTitle = FINALIZE_WARNING_TEXT.getString() + "\n" + EDIT_TITLE_TEXT.getString();
                }
                MainClass.speakWithNarrator(currentTitle, true);
            }
            return;
        }
        firstTimeInSignMenu = true;

        // Repeat current page content and un-focus next and previous page buttons
        if (Screen.hasAltDown() && Screen.hasControlDown()) {
            if (this.nextPageButton.isFocused()) this.nextPageButton.changeFocus(false);
            if (this.previousPageButton.isFocused()) this.previousPageButton.changeFocus(false);
            previousContent = "";
        }

        if (this.currentPage < 0 || this.currentPage > this.pages.size())
            return; // Return if the page index is out of bounds

        String currentPageContentString = this.pages.get(this.currentPage).trim();
        currentPageContentString = "%s \n\n %s".formatted(currentPageContentString, this.pageIndicatorText.getString());

        if (!previousContent.equals(currentPageContentString)) {
            previousContent = currentPageContentString;
            MainClass.speakWithNarrator(currentPageContentString, true);
        }
    }
}
