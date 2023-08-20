package com.github.khanshoaib3.minecraft_access.utils.condition;

import java.util.function.BooleanSupplier;

/**
 * Combining a changeable boolean variable with supplier.
 */
class MockKeystrokeAction {
    Boolean pressed;
    BooleanSupplier supplier;

    public void revertKeystrokeResult() {
        this.pressed = !this.pressed;
    }

    public MockKeystrokeAction(boolean initPressed) {
        this.pressed = initPressed;
        this.supplier = () -> this.pressed;
    }
}
