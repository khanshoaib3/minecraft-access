package com.github.khanshoaib3.minecraft_access.utils.condition;

import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeAction;
import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClientWrapper;
import com.github.khanshoaib3.minecraft_access.test_utils.extensions.MockMinecraftClientExtension;
import net.minecraft.client.gui.screen.Screen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@ExtendWith(MockMinecraftClientExtension.class)
class MenuKeystrokeTest {
    @com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockMinecraftClient
    MockMinecraftClientWrapper mockClient;

    /**
     * isReleased -> open the menu
     * isPressing -> close the menu
     */
    @Test
    void test() {
        MockKeystrokeAction key = MockKeystrokeAction.pressed();
        MenuKeystroke condition = new MenuKeystroke(key.supplier);
        condition.updateStateForNextTick();
        key.release();
        assertTrue(condition.canOpenMenu(), "from pressed to released, the menu should be opened");
        condition.updateStateForNextTick();

        mockClient.setScreen(Screen.class);
        key.press();
        assertTrue(condition.closeMenuIfMenuKeyPressing(), "key is pressed, the menu should be closed");
        mockClient.verifyClosingMenu();
        condition.updateStateForNextTick();

        key.release();
        assertFalse(condition.canOpenMenu(), "key is released, but since the menu is just closed, it should not be opened again");
        // will clean the inner isMenuJustClosed flag
        condition.updateStateForNextTick();

        key.press();
        condition.updateStateForNextTick();
        key.release();
        assertTrue(condition.canOpenMenu(), "now the menu can be opened again, AFTER releasing the key");
    }
}