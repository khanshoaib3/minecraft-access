package com.github.khanshoaib3.minecraft_access.utils.condition;

import java.util.function.BooleanSupplier;

/**
 * After experimentation, there is only one valid combination for opening and closing a menu with single key:
 * isReleased -> open the menu
 * isPressing -> close the menu
 * <p>
 * So here is an encapsulation of this knowledge.
 */
public class MenuKeyStroke extends Keystroke {
    /**
     * Prevent the f4 menu open again after f4 menu is just closed by pressing f4.
     * The menu is closed by pressing the f4, and is opened by releasing the f4,
     * so if you slowly press down the f4 while menu is opening, the menu will be opened again when you release the f4.
     */
    boolean isMenuJustClosed = false;

    public MenuKeyStroke(BooleanSupplier condition) {
        super(condition);
    }

    public boolean canOpenMenu() {
        return isReleased() && !isMenuJustClosed;
    }

    public boolean canCloseMenu() {
        if (isPressing()) {
            isMenuJustClosed = true;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateStateForNextTick() {
        // clean the flag
        if (isReleased()) isMenuJustClosed = false;
        super.updateStateForNextTick();
    }
}
