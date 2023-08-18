package com.github.khanshoaib3.minecraft_access.utils.condition;

import java.util.function.BooleanSupplier;

/**
 * A state machine that helps with complex keystroke condition checking,
 * such as "when-the-key-is-released", "only-trigger-once-before-release-key", "double-strike-within-interval".
 */
public class Keystroke {
    /**
     * Save the state of keystroke at the previous tick.
     */
    private boolean hasKeyPressed = false;
    /**
     * Expression that checking if the key (combination) is pressed now.
     */
    private final BooleanSupplier condition;

    /**
     * @param condition Expression that checking if the key (combination) is pressed now.
     */
    public Keystroke(BooleanSupplier condition) {
        this.condition = condition;
    }

    /**
     * Update state according to the condition result.
     * Invoke this method at the end of feature logic.
     */
    public void updateStateForNextTick() {
        hasKeyPressed = isPressing();
    }

    public boolean isPressing() {
        return condition.getAsBoolean();
    }

    public boolean isNotPressing() {
        return !isPressing();
    }

    public boolean hasPressedPreviousTick() {
        return hasKeyPressed;
    }

    public boolean isReleased() {
        return !isPressing() && hasPressedPreviousTick();
    }

    public boolean isPressed() {
        return isPressing() && !hasPressedPreviousTick();
    }
}
