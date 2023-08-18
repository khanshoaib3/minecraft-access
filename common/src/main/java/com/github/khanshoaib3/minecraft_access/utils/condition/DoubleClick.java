package com.github.khanshoaib3.minecraft_access.utils.condition;

import java.util.function.BooleanSupplier;

public class DoubleClick extends KeystrokeTiming{
    /**
     * @param condition Expression that checking if the key (combination) is pressed now.
     * @param interval  The maximum interval between first and second keystroke.
     */
    public DoubleClick(BooleanSupplier condition, Interval interval) {
        super(condition, interval);
    }

    @Override
    public void updateStateForNextTick() {
        super.updateStateForNextTick();
    }
}
