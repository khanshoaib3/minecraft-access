package com.github.khanshoaib3.minecraft_access.utils;

import com.github.khanshoaib3.minecraft_access.utils.condition.Keystroke;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class KeystrokeTest {

    static class InitKeystrokeConditionSameAsExpected implements ArgumentsProvider {
        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            return Stream.of(
                    Arguments.of(new MockKeystroke(true), true),
                    Arguments.of(new MockKeystroke(false), false)
            );
        }
    }

    @Nested
    class KeystrokeConditionCheckingTest {
        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsPressing(MockKeystroke keystroke, boolean expected) {
            checkPressing(keystroke, expected, Keystroke::isPressing);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsNotPressing(MockKeystroke keystroke, boolean expected) {
            checkNotPressing(keystroke, expected, Keystroke::isNotPressing);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testHasPressedPreviousTick(MockKeystroke keyStroke, boolean expected) {
            Keystroke checker = new Keystroke(keyStroke.supplier, Keystroke.TriggeredAt.PRESSED);

            // tick 0
            checker.updateStateForNextTick();

            // tick 1
            assertThat(checker.hasPressedPreviousTick()).isEqualTo(expected);
            keyStroke.revertKeystrokeResult();
            checker.updateStateForNextTick();

            // tick 2
            assertThat(checker.hasPressedPreviousTick())
                    .as("keystroke condition should be reverted")
                    .isEqualTo(!expected);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsReleased(MockKeystroke keyStroke, boolean expected) {
            checkReleased(keyStroke, expected, Keystroke::isReleased);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsPressed(MockKeystroke keyStroke, boolean expected) {
            checkPressed(keyStroke, expected, Keystroke::isPressed);
        }
    }

    @Nested
    class KeystrokeTriggerTest {
        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsPressing(MockKeystroke keystroke, boolean expected) {
            checkPressing(keystroke, expected, Keystroke::isTriggered);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsNotPressing(MockKeystroke keystroke, boolean expected) {
            checkNotPressing(keystroke, expected, Keystroke::isTriggered);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsReleased(MockKeystroke keyStroke, boolean expected) {
            checkReleased(keyStroke, expected, Keystroke::isTriggered);
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsPressed(MockKeystroke keyStroke, boolean expected) {
            checkPressed(keyStroke, expected, Keystroke::isTriggered);
        }
    }

    @Nested
    class KeystrokeTriggerCountTest {
        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsPressing(MockKeystroke keystroke, boolean expected) {
            checkPressing(keystroke,
                    k -> {
                        assertThat(k.isTriggered()).isEqualTo(expected);
                        if (expected) {
                            assertThat(k.hasBeenTriggered()).isTrue();
                            assertThat(k.isTriggered())
                                    .as("can be triggered only once")
                                    .isFalse();
                        }
                    },
                    k -> assertThat(k.isTriggered()).isEqualTo(!expected));
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsNotPressing(MockKeystroke keystroke, boolean expected) {
            checkNotPressing(keystroke,
                    k -> {
                        assertThat(k.isTriggered()).isEqualTo(expected);
                        if (expected) {
                            assertThat(k.hasBeenTriggered()).isTrue();
                            assertThat(k.isTriggered())
                                    .as("can be triggered only once")
                                    .isFalse();
                        }
                    },
                    k -> assertThat(k.isTriggered()).isEqualTo(!expected));
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsReleased(MockKeystroke keystroke, boolean expected) {
            checkReleased(keystroke,
                    k -> {
                        assertThat(k.isTriggered()).isEqualTo(expected);
                        if (expected) {
                            assertThat(k.hasBeenTriggered()).isTrue();
                            assertThat(k.isTriggered())
                                    .as("can be triggered only once")
                                    .isFalse();
                        }
                    },
                    k -> assertThat(k.isTriggered()).isEqualTo(!expected));
        }

        @ParameterizedTest
        @ArgumentsSource(InitKeystrokeConditionSameAsExpected.class)
        void testIsPressed(MockKeystroke keystroke, boolean expected) {
            checkPressed(keystroke,
                    k -> {
                        boolean exp = !expected;
                        assertThat(k.isTriggered())
                                .as("originally released key should be pressed now (expected should be true), vice versa")
                                .isEqualTo(exp);
                        if (exp) {
                            assertThat(k.hasBeenTriggered()).isTrue();
                            assertThat(k.isTriggered())
                                    .as("can be triggered only once")
                                    .isFalse();
                        }
                    },
                    k -> assertThat(k.isTriggered()).isEqualTo(expected));
        }
    }

    /**
     * Combining a changeable boolean variable with supplier.
     */
    static class MockKeystroke {
        Boolean pressed;
        BooleanSupplier supplier;

        public void revertKeystrokeResult() {
            this.pressed = !this.pressed;
        }

        public MockKeystroke(boolean initPressed) {
            this.pressed = initPressed;
            this.supplier = () -> this.pressed;
        }
    }

    @Test
    void testMockKeystrokeWorks() {
        var m = new MockKeystroke(true);
        var k = new Keystroke(m.supplier);
        assertThat(k.isPressing()).isTrue();
        m.revertKeystrokeResult();
        assertThat(k.isPressing()).isFalse();
    }

    private static void checkPressing(MockKeystroke keystroke, boolean expected, Function<Keystroke, Boolean> actual) {
        checkPressing(keystroke,
                k -> assertThat(actual.apply(k)).isEqualTo(expected),
                k -> assertThat(actual.apply(k))
                        .as("keystroke condition should be reverted")
                        .isEqualTo(!expected));
    }

    private static void checkPressing(MockKeystroke keystroke, Consumer<Keystroke> trueAssertion, Consumer<Keystroke> falseAssertion) {
        Keystroke k = new Keystroke(keystroke.supplier, Keystroke.TriggeredAt.PRESSING);

        trueAssertion.accept(k);
        k.updateStateForNextTick();

        keystroke.revertKeystrokeResult();
        falseAssertion.accept(k);
        k.updateStateForNextTick();

        keystroke.revertKeystrokeResult();
        trueAssertion.accept(k);
    }

    private static void checkNotPressing(MockKeystroke keystroke, boolean expected, Function<Keystroke, Boolean> actual) {
        checkNotPressing(keystroke,
                k -> assertThat(actual.apply(k))
                        .as("keystroke condition should be reverted")
                        .isEqualTo(expected),
                k -> assertThat(actual.apply(k)).isEqualTo(!expected));
    }

    private static void checkNotPressing(MockKeystroke keystroke, Consumer<Keystroke> trueAssertion, Consumer<Keystroke> falseAssertion) {
        Keystroke k = new Keystroke(keystroke.supplier, Keystroke.TriggeredAt.NOT_PRESSING);

        falseAssertion.accept(k);
        k.updateStateForNextTick();

        keystroke.revertKeystrokeResult();
        trueAssertion.accept(k);
        k.updateStateForNextTick();

        keystroke.revertKeystrokeResult();
        falseAssertion.accept(k);
    }

    private static void checkReleased(MockKeystroke keystroke, boolean expected, Function<Keystroke, Boolean> actual) {
        checkReleased(keystroke,
                k -> assertThat(actual.apply(k))
                        .as("originally pressed key should be released now, vice versa")
                        .isEqualTo(expected),
                k -> assertThat(actual.apply(k)).isEqualTo(!expected));
    }

    private static void checkReleased(MockKeystroke keystroke, Consumer<Keystroke> trueAssertion, Consumer<Keystroke> falseAssertion) {
        Keystroke k = new Keystroke(keystroke.supplier, Keystroke.TriggeredAt.RELEASED);

        // tick 0
        k.updateStateForNextTick();

        // tick 1
        keystroke.revertKeystrokeResult();
        trueAssertion.accept(k);
        k.updateStateForNextTick();

        // tick 2
        keystroke.revertKeystrokeResult();
        falseAssertion.accept(k);
    }

    private static void checkPressed(MockKeystroke keystroke, boolean expected, Function<Keystroke, Boolean> actual) {
        checkPressed(keystroke,
                k -> assertThat(actual.apply(k))
                        .as("originally released key should be pressed now, vice versa")
                        .isEqualTo(!expected),
                k -> assertThat(actual.apply(k)).isEqualTo(expected));
    }

    private static void checkPressed(MockKeystroke keystroke, Consumer<Keystroke> trueAssertion, Consumer<Keystroke> falseAssertion) {
        Keystroke k = new Keystroke(keystroke.supplier);

        // tick 0
        k.updateStateForNextTick();

        // tick 1
        keystroke.revertKeystrokeResult();
        trueAssertion.accept(k);
        k.updateStateForNextTick();

        // tick 2
        keystroke.revertKeystrokeResult();
        falseAssertion.accept(k);
    }
}
