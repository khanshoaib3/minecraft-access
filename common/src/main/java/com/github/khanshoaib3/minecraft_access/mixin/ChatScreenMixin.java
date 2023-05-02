package com.github.khanshoaib3.minecraft_access.mixin;

import com.github.khanshoaib3.minecraft_access.MainClass;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.ChatHudLine;
import net.minecraft.client.gui.screen.ChatScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.narration.NarrationMessageBuilder;
import net.minecraft.client.gui.screen.narration.NarrationPart;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;


@Mixin(ChatScreen.class)
public class ChatScreenMixin {
    private static final Text USAGE_TEXT = Text.translatable("chat_screen.usage");

    @Shadow
    protected TextFieldWidget chatField;

    /**
     * Removes `message to send` from the spoken text when entering a chat message.
     */
    @Inject(at = @At("HEAD"), method = "addScreenNarrations", cancellable = true)
    private void addScreenNarrations(NarrationMessageBuilder builder, CallbackInfo callbackInfo) {
        if (MinecraftClient.getInstance().currentScreen == null) return;
        builder.put(NarrationPart.TITLE, MinecraftClient.getInstance().currentScreen.getTitle());
        builder.put(NarrationPart.USAGE, USAGE_TEXT);
        String string = this.chatField.getText();
        if (!string.isEmpty()) {
            builder.nextMessage().put(NarrationPart.TITLE, string);
        }
        callbackInfo.cancel();
    }

    /**
     * Adds new keybinds that speak the previous chat messages.
     */
    @Inject(at = @At("HEAD"), method = "keyPressed", cancellable = true)
    private void keyPressed(int keyCode, int scanCode, int modifiers, CallbackInfoReturnable<Boolean> cir) {
        if (!Screen.hasAltDown()) return;
        if (!handleKeyInput(keyCode)) return;

        cir.setReturnValue(true);
        cir.cancel();
    }

    /**
     * Handles the input from a key event and returns a boolean value indicating whether
     * the input was handled or not.
     * This method checks if the key code corresponds to a numeric key or numeric keypad
     * key between 1 and 9. If it does, it calls the {@link #speakPreviousChatAtIndex(int)}
     * method with the corresponding index and returns true. If the key code does not correspond
     * to one of these keys, the method returns false.
     *
     * @param keyCode the key code of the input event.
     * @return true if the input was handled, false otherwise.
     */
    private static boolean handleKeyInput(int keyCode) {
        for (int i = 1; i <= 9; i++) {
            if (keyCode == InputUtil.GLFW_KEY_0 + i || keyCode == InputUtil.GLFW_KEY_KP_0 + i) {
                speakPreviousChatAtIndex(i - 1);
                return true;
            }
        }
        return false;
    }

    /**
     * Speaks the previous chat message at the specified index offset.
     *
     * @param indexOffset the index offset from the most recent chat message to speak.
     */
    private static void speakPreviousChatAtIndex(int indexOffset) {
        List<ChatHudLine> messages = ((ChatHudAccessor) MinecraftClient.getInstance().inGameHud.getChatHud()).getMessages();
        if ((messages.size() - indexOffset) <= 0) return;

        MainClass.speakWithNarrator(messages.get(indexOffset).content().getString(), true);
    }
}
