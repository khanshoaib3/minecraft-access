package com.github.khanshoaib3.minecraft_access.utils.condition;

import java.util.function.BooleanSupplier;

/**
 * For checking keystroke conditions that related to time,
 * like "interval/rate-limitation-on-feature-execution", "double/triple-click"
 */
public abstract class KeystrokeTiming extends Keystroke {
    protected Interval interval;

    /**
     * @param condition Expression that checking if the key (combination) is pressed now.
     * @param interval  The interval setting, the meaning is to be determined.
     */
    public KeystrokeTiming(BooleanSupplier condition, Interval interval) {
        super(condition);
        this.interval = interval;
    }

    /**
     * Getter, for operating inner Interval instance.
     */
    public Interval interval() {
        return this.interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }
}
