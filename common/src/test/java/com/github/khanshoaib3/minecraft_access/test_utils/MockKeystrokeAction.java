package com.github.khanshoaib3.minecraft_access.test_utils;

import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import com.github.khanshoaib3.minecraft_access.utils.condition.MenuKeyStroke;
import org.junit.platform.commons.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.function.BooleanSupplier;

/**
 * Combining a changeable boolean variable with supplier.
 */
public class MockKeystrokeAction {
    Boolean pressed;
    public BooleanSupplier supplier;
    public Keystroke mockTarget;

    public void revertKeystrokeResult() {
        this.pressed = !this.pressed;
    }

    public MockKeystrokeAction(boolean initPressed) {
        this.pressed = initPressed;
        this.supplier = () -> this.pressed;
    }

    public void press() {
        this.pressed = true;
    }

    public void release() {
        this.pressed = false;
    }

    public static MockKeystrokeAction pressed() {
        return new MockKeystrokeAction(true);
    }

    @SuppressWarnings("unused")
    public static MockKeystrokeAction released() {
        return new MockKeystrokeAction(false);
    }

    /**
     * Replace given KeyStroke field's condition with its supplier
     */
    public void mockKeystrokeOf(Class<?> clazz, String keyFieldName) {
        try {
            Field keyField = clazz.getDeclaredField(keyFieldName);
            this.mockTarget = (Keystroke) ReflectionUtils.tryToReadFieldValue(keyField).get();

            Field conditionField = Keystroke.class.getDeclaredField("condition");
            conditionField.setAccessible(true);
            conditionField.set(this.mockTarget, this.supplier);

        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Reset target field's inner state to avoid test cases from affecting each other.
     */
    public void resetTargetInnerState() {
        try {
            if (this.mockTarget instanceof MenuKeyStroke) {
                Field justClosed = MenuKeyStroke.class.getDeclaredField("isMenuJustClosed");
                justClosed.setAccessible(true);
                justClosed.set(this.mockTarget, false);
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
