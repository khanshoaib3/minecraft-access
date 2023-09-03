package com.github.khanshoaib3.minecraft_access.utils.condition;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class MenuKeyStrokeTest {
    @Test
    void test() {
        MockKeystrokeAction m = new MockKeystrokeAction(true);
        MenuKeyStroke k = new MenuKeyStroke(m.supplier);

        k.updateStateForNextTick();
        m.revertKeystrokeResult();
        assertTrue(k.canOpenMenu(), "from pressed to released, the menu should be opened");

        // press down
        m.revertKeystrokeResult();
        assertTrue(k.canCloseMenu(), "is pressed, the menu should be closed");
        k.updateStateForNextTick();

        // release
        m.revertKeystrokeResult();
        assertFalse(k.canOpenMenu(), "is released, but since the menu is just closed, it should not be opened again");
        // will clean the inner flag
        k.updateStateForNextTick();

        // press down
        m.revertKeystrokeResult();
        k.updateStateForNextTick();
        // release
        m.revertKeystrokeResult();
        assertTrue(k.canOpenMenu(), "now the menu can be opened again");
    }
}