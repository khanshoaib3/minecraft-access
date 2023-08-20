package com.github.khanshoaib3.minecraft_access.utils.condition;

import com.github.khanshoaib3.minecraft_access.utils.condition.Interval;

import static org.junit.jupiter.api.Assertions.*;

class MockInterval extends Interval {
    boolean ready = false;

    public MockInterval(long lastRunTime, long delayInNanoTime) {
        super(lastRunTime, delayInNanoTime);
    }

    public void setReady(boolean ready) {
        this.ready = ready;
    }

    @Override
    public boolean isReady() {
        return ready;
    }
}