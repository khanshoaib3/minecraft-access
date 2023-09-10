package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import com.github.khanshoaib3.minecraft_access.utils.system.KeyUtils;
import com.github.khanshoaib3.minecraft_access.utils.system.MouseUtils;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.BookEditScreen;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.PageTurnWidget;
import net.minecraft.client.resource.language.I18n;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.List;

@Mixin(BookEditScreen.class)
public abstract class BookEditScreenMixin {
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

    @Unique boolean minecraft_access$firstTimeInSignMenu = true;
    @Unique String minecraft_access$previousContent = "";
    @Unique private static final Keystroke minecraft_access$tabKey = new Keystroke(() -> KeyUtils.isAnyPressed(GLFW.GLFW_KEY_TAB));
    @Unique private static final Keystroke minecraft_access$spaceKey = new Keystroke(KeyUtils::isSpacePressed);

    @Unique private int minecraft_access$currentFocusedButtonStateCode = 0;
    @Unique private static final int BUTTON_OFFSET = 3;

    @Inject(at = @At("HEAD"), method = "render")
    public void render(DrawContext context, int mouseX, int mouseY, float delta, CallbackInfo ci) {
        MinecraftClient minecraftClient = MinecraftClient.getInstance();
        if (minecraftClient == null) return;
        if (minecraftClient.currentScreen == null) return;

        // Switch between buttons with Tab key
        // Only switch once until release and press Tab again
        if (minecraft_access$tabKey.isPressed()) {
            minecraft_access$switchMouseHoveredButton();
        }
        // update the state
        minecraft_access$tabKey.updateStateForNextTick();

        if (minecraft_access$spaceKey.isPressed()) {
            minecraft_access$spaceKey.updateStateForNextTick();
            if (this.signing) {
                if (this.cancelButton.isHovered()) {
                    this.cancelButton.onPress();
                    return;
                }
                if (this.finalizeButton.isHovered()) {
                    this.finalizeButton.onPress();
                    return;
                }
            } else {
                if (this.doneButton.isHovered()) {
                    this.doneButton.onPress();
                    return;
                }
                if (this.signButton.isHovered()) {
                    this.signButton.onPress();
                    return;
                }
            }
        }
        // update the state
        minecraft_access$spaceKey.updateStateForNextTick();

        if (this.signing) {
            String currentTitle = this.title.trim();

            if (!minecraft_access$previousContent.equals(currentTitle)) {
                minecraft_access$previousContent = currentTitle;
                if (minecraft_access$firstTimeInSignMenu) {
                    minecraft_access$firstTimeInSignMenu = false;
                    currentTitle = FINALIZE_WARNING_TEXT.getString() + "\n" + EDIT_TITLE_TEXT.getString();
                }
                MainClass.speakWithNarrator(currentTitle, true);
            }
            return;
        }
        minecraft_access$firstTimeInSignMenu = true;

        // Repeat current page content and un-focus next and previous page buttons
        if (Screen.hasAltDown() && Screen.hasControlDown()) {
            if (this.nextPageButton.isFocused()) this.nextPageButton.setFocused(false);
            if (this.previousPageButton.isFocused()) this.previousPageButton.setFocused(false);
            minecraft_access$previousContent = "";
        }

        if (this.currentPage < 0 || this.currentPage > this.pages.size())
            return; // Return if the page index is out of bounds

        String currentPageContentString = this.pages.get(this.currentPage).trim();
        currentPageContentString = "%s \n\n %s".formatted(currentPageContentString, this.pageIndicatorText.getString());

        if (!minecraft_access$previousContent.equals(currentPageContentString)) {
            minecraft_access$previousContent = currentPageContentString;
            MainClass.speakWithNarrator(currentPageContentString, true);
        }
    }

    /**
     * Switch buttons focus with Tab key,
     * put the cursor on the focused button instead of invoking "setFocused()",
     * since somewhere we don't know will modify buttons focus states, which affects our logic.
     */
    @Unique
    private void minecraft_access$switchMouseHoveredButton() {
        if (this.signing) {
            // finalizeButton & cancelButton under the screen
            switch (minecraft_access$currentFocusedButtonStateCode) {
                case 0 -> minecraft_access$hoverMouseOnTo(this.cancelButton);
                case 1 -> minecraft_access$moveMouseAway();
                case 2 -> {
                    // the finalizeButton only be activated while title is not empty
                    if (this.finalizeButton.active) {
                        minecraft_access$hoverMouseOnTo(this.finalizeButton);
                    } else {
                        minecraft_access$currentFocusedButtonStateCode = 0;
                        minecraft_access$hoverMouseOnTo(this.cancelButton);
                    }
                }
            }
        } else {
            // signButton & doneButton under the screen
            switch (minecraft_access$currentFocusedButtonStateCode) {
                case 0 -> minecraft_access$hoverMouseOnTo(this.signButton);
                case 1 -> minecraft_access$hoverMouseOnTo(this.doneButton);
                case 2 -> minecraft_access$moveMouseAway();
            }
        }

        // loop between three status
        minecraft_access$currentFocusedButtonStateCode = (minecraft_access$currentFocusedButtonStateCode + 1) % 3;
    }

    @Unique
    private void minecraft_access$hoverMouseOnTo(ButtonWidget button) {
        MouseUtils.performAt(((ClickableWidgetAccessor) button).callGetX() + BUTTON_OFFSET,
                ((ClickableWidgetAccessor) button).callGetY() + BUTTON_OFFSET,
                MouseUtils::move);
    }

    @Unique
    private void minecraft_access$moveMouseAway() {
        MouseUtils.performAt(((ClickableWidgetAccessor) this.signButton).callGetX() - 10,
                ((ClickableWidgetAccessor) this.signButton).callGetY() - 10,
                MouseUtils::move);
        MainClass.speakWithNarrator(I18n.translate("minecraft_access.book_edit.focus_moved_away"), true);
    }
}
