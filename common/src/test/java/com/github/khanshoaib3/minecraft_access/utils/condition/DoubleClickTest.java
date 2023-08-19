package com.github.khanshoaib3.minecraft_access.utils.condition;

import com.github.khanshoaib3.minecraft_access.MainClass;
import com.github.khanshoaib3.minecraft_access.config.Config;
import com.github.khanshoaib3.minecraft_access.config.ConfigMap;
import com.github.khanshoaib3.minecraft_access.config.config_maps.OtherConfigsMap;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class DoubleClickTest {
    @BeforeAll
    static void beforeAll() {
        ConfigMap mockConfigMap = mock(ConfigMap.class);
        when(mockConfigMap.getOtherConfigsMap()).thenReturn(OtherConfigsMap.getDefaultOtherConfigsMap());
        Config mockConfig = mock(Config.class);
        when(mockConfig.getConfigMap()).thenReturn(mockConfigMap);
        MainClass.config = mockConfig;
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
    void testCanCleanStaleCountIfTimeOut() throws InterruptedException {
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