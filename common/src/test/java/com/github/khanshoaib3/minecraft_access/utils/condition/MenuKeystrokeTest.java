package com.github.khanshoaib3.minecraft_access.utils.condition;

import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeAction;
import com.github.khanshoaib3.minecraft_access.test_utils.MockMinecraftClientWrapper;
import com.github.khanshoaib3.minecraft_access.test_utils.annotations.MockMinecraftClient;
import com.github.khanshoaib3.minecraft_access.test_utils.extensions.MockMinecraftClientExtension;
import net.minecraft.client.gui.screen.Screen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;

@ExtendWith(MockMinecraftClientExtension.class)
class MenuKeystrokeTest {
    @MockMinecraftClient
    MockMinecraftClientWrapper mockClient;

    @Test
    void test() {
        MockKeystrokeAction m = MockKeystrokeAction.pressed();
        MenuKeystroke k = new MenuKeystroke(m.supplier);
        Screen mockScreen = mockClient.setScreen(Screen.class);

        k.updateStateForNextTick();
        m.release();
        assertTrue(k.canOpenMenu(), "from pressed to released, the menu should be opened");

        m.press();
        assertTrue(k.closeMenuIfMenuKeyPressing(), "is pressed, the menu should be closed");
        verify(mockScreen).close();
        k.updateStateForNextTick();

        m.release();
        assertFalse(k.canOpenMenu(), "is released, but since the menu is just closed, it should not be opened again");
        // will clean the inner flag
        k.updateStateForNextTick();

        m.press();
        k.updateStateForNextTick();
        m.release();
        assertTrue(k.canOpenMenu(), "now the menu can be opened again");
    }
}