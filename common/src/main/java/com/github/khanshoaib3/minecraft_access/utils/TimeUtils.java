package com.github.khanshoaib3.minecraft_access.utils;

import java.util.function.BooleanSupplier;

public class TimeUtils {
    /**
     * An auto-refresh countdown timer for controlling interval execution of features.
     */
    public static class Interval {
        private long lastRunTime;
        private final long delay;
        private boolean isRunning;
        private final boolean disabled;

        private Interval(long lastRunTime, long delayInNanoTime) {
            this.lastRunTime = lastRunTime;
            this.delay = delayInNanoTime;
            this.disabled = delayInNanoTime == 0;
            this.isRunning = false;
        }

        /**
         * Build or update instance according to delay config.
         *
         * @param delay    config value
         * @param previous the interval class variable
         */
        public static Interval inMilliseconds(long delay, Interval... previous) {
            if (previous == null || previous.length == 0 || previous[0] == null) {
                // 1 milliseconds = 1*10^6 nanoseconds
                return new Interval(System.nanoTime(), delay * 1000_000);
            } else {
                Interval interval = previous[0];
                boolean configChanged = delay * 1000_000 != interval.delay;
                return configChanged ? Interval.inMilliseconds(delay) : interval;
            }
        }

        public void reset() {
            lastRunTime = System.nanoTime();
        }

        /**
         * Check if the delay has cooled down. (Will auto-reset the timer if true)
         */
        public boolean isReady() {
            if (disabled) return false;

            if (System.nanoTime() - lastRunTime > delay) {
                reset();
                return true;
            } else {
                return false;
            }
        }

        /**
         * Check if the delay has cooled down. (This will not auto-reset the timer, that will have to be done by calling the start() method)
         *
         * @return true if the delay timer has stopped or cooled down.
         */
        public boolean hasEnded() {
            if (!this.isRunning) return true;
            if (!this.isReady()) return false;

            this.isRunning = false;
            return true;
        }

        /**
         * Starts or resets the delay timer. This is recommended to be used when there is a key input.
         */
        public void start() {
            this.isRunning = true;
            reset();
        }
    }

    /**
     * A state machine that helps with complex keystroke condition checking,
     * such as "when-the-key-is-released", "only-trigger-once-before-release-key", "double-strike-within-interval".
     */
    public static class KeystrokeChecker {
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
        public KeystrokeChecker(BooleanSupplier condition) {
            this.condition = condition;
        }

        /**
         * Update state according to the condition result.
         * Invoke this method at the end of feature logic.
         */
        public void updateStateForNextTick() {
            hasKeyPressed = isKeyPressing();
        }

        public boolean isKeyPressing() {
            return condition.getAsBoolean();
        }

        public boolean hasKeyPressedPreviousTick() {
            return hasKeyPressed;
        }

        public boolean isKeyReleased() {
            return !isKeyPressing() && hasKeyPressedPreviousTick();
        }

        public boolean isKeyPressed() {
            return isKeyPressing() && !hasKeyPressedPreviousTick();
        }
    }
}
