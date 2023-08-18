package com.github.khanshoaib3.minecraft_access.utils.condition;

import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.function.Function;

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
     * For checking feature triggering condition,
     * like "only-trigger-once-before-release".
     */
    private final TriggeredAt timing;

    /**
     * Use this class as a condition checker.
     * Suitable for complex cases that there are other conditions that determine the logic is triggered or not.
     *
     * @param condition Expression that checking if the key (combination) is pressed now.
     */
    public Keystroke(BooleanSupplier condition) {
        this(condition, TriggeredAt.PRESSING);
    }

    /**
     * Let this class handles feature triggering controls.
     * Suitable for simple cases that the keystroke is the only condition that triggers the logic.
     *
     * @param condition Expression that checking if the key (combination) is pressed now.
     * @param timing    When the corresponding logic is triggered.
     */
    public Keystroke(BooleanSupplier condition, TriggeredAt timing) {
        this.condition = condition;
        this.timing = Optional.ofNullable(timing).orElse(TriggeredAt.PRESSING);
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

    public boolean isTriggered() {
        return this.timing.check(this);
    }

    public enum TriggeredAt {
        PRESSING(Keystroke::isPressing),
        NOT_PRESSING(Keystroke::isNotPressing),
        PRESSED(Keystroke::isPressed),
        RELEASED(Keystroke::isReleased),
        ;

        private final Function<Keystroke, Boolean> triggerCondition;

        TriggeredAt(Function<Keystroke, Boolean> condition) {
            this.triggerCondition = condition;
        }

        public boolean check(Keystroke keystroke) {
            return this.triggerCondition.apply(keystroke);
        }
    }
}
