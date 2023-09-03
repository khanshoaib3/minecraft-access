package com.github.khanshoaib3.minecraft_access.utils.condition;

import com.github.khanshoaib3.minecraft_access.utils.TestFixtures;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class DoubleClickTest {

    @BeforeAll
    static void beforeAll() {
        TestFixtures.mockConfigFileAsDefaultValues();
    }

    @Test
    void testCanCountTrigger() {
        MockKeystrokeAction m = new MockKeystrokeAction(true);
        DoubleClick k = new DoubleClick(m.supplier, Keystroke.TriggeredAt.PRESSING);

        k.updateStateForNextTick();
        assertThat(k.canBeTriggered()).as("there should be one valid count").isTrue();

        k.updateStateForNextTick();
        assertThat(k.canBeTriggered()).as("once reaches triggered condition, the counter will be cleaned after update").isFalse();
    }

    @Test
    void testCanCleanStaleCountIfTimeOut() {
        MockKeystrokeAction m = new MockKeystrokeAction(true);
        MockInterval i = new MockInterval(0, 0);
        DoubleClick k = new DoubleClick(m.supplier, Keystroke.TriggeredAt.PRESSING, i);

        // record first keystroke
        k.updateStateForNextTick();
        // simulate time passing through the interval
        i.setReady(true);
        k.updateStateForNextTick();

        assertThat(k.canBeTriggered()).as("first count should be cleaned").isFalse();
    }

    @Test
    void testCanTriggerIfProperlyTriggerAgain() {
        MockKeystrokeAction m = new MockKeystrokeAction(true);
        MockInterval i = new MockInterval(0, 0);
        DoubleClick k = new DoubleClick(m.supplier, Keystroke.TriggeredAt.PRESSING, i);

        // record first keystroke
        k.updateStateForNextTick();
        // simulate time passing through the interval
        i.setReady(true);
        k.updateStateForNextTick();
        assertThat(k.canBeTriggered()).as("first count should be cleaned").isFalse();

        // third keystroke
        i.setReady(false);
        k.updateStateForNextTick();
        assertThat(k.canBeTriggered()).as("third keystroke should be regarded as first valid keystroke").isTrue();
    }
}