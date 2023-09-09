package com.github.khanshoaib3.minecraft_access.utils.condition;

import com.github.khanshoaib3.minecraft_access.test_utils.MockKeystrokeAction;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mockStatic;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class MenuKeyStrokeTest {
    @Mock
    MinecraftClient mockClient;
    @Mock
    Screen mockScreen;

    @Test
    void test() {
        MockKeystrokeAction m = MockKeystrokeAction.pressed();
        MenuKeyStroke k = new MenuKeyStroke(m.supplier);
        mockStatic(MinecraftClient.class).when(MinecraftClient::getInstance).thenReturn(mockClient);
        mockClient.currentScreen = mockScreen;

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