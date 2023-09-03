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
