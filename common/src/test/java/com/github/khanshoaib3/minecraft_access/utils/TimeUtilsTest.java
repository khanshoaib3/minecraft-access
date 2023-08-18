package com.github.khanshoaib3.minecraft_access.utils;

import org.jetbrains.annotations.TestOnly;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.function.BooleanSupplier;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class TimeUtilsTest {

    @Nested
    class KeystrokeCheckerTest {
        /**
         * Combining a changeable boolean variable with supplier.
         */
        @TestOnly
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
            var k = new TimeUtils.KeystrokeChecker(m.supplier);
            assertThat(k.isPressing()).isEqualTo(true);
            m.revertKeystrokeResult();
            assertThat(k.isPressing()).isEqualTo(false);
        }

        @ParameterizedTest
        @MethodSource("provideKeystrokeCheckerTestCases")
        void testIsKeyPressing(MockKeystroke keystroke, boolean expected) {
            TimeUtils.KeystrokeChecker checker = new TimeUtils.KeystrokeChecker(keystroke.supplier);
            assertThat(checker.isPressing()).isEqualTo(expected);
            keystroke.revertKeystrokeResult();
            assertThat(checker.isPressing())
                    .as("keystroke condition should be reverted")
                    .isEqualTo(!expected);
        }

        static Stream<Arguments> provideKeystrokeCheckerTestCases() {
            return Stream.of(
                    Arguments.of(new MockKeystroke(true), true),
                    Arguments.of(new MockKeystroke(false), false)
            );
        }

        @ParameterizedTest
        @MethodSource("provideKeystrokeCheckerTestCases")
        void testIsKeyNotPressing(MockKeystroke keystroke, boolean expected) {
            TimeUtils.KeystrokeChecker checker = new TimeUtils.KeystrokeChecker(keystroke.supplier);
            assertThat(checker.isNotPressing()).isEqualTo(!expected);
            keystroke.revertKeystrokeResult();
            assertThat(checker.isNotPressing())
                    .as("keystroke condition should be reverted")
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @MethodSource("provideKeystrokeCheckerTestCases")
        void testHasKeyPressed(MockKeystroke keyStroke, boolean expected) {
            TimeUtils.KeystrokeChecker checker = new TimeUtils.KeystrokeChecker(keyStroke.supplier);

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
        @MethodSource("provideKeystrokeCheckerTestCases")
        void testIsKeyReleased(MockKeystroke keyStroke, boolean expected) {
            TimeUtils.KeystrokeChecker checker = new TimeUtils.KeystrokeChecker(keyStroke.supplier);

            // tick 0
            checker.updateStateForNextTick();

            // tick 1
            keyStroke.revertKeystrokeResult();
            assertThat(checker.isReleased())
                    .as("pressed key should be released now, vice versa")
                    .isEqualTo(expected);
        }

        @ParameterizedTest
        @MethodSource("provideKeystrokeCheckerTestCases")
        void testIsKeyPressed(MockKeystroke keyStroke, boolean expected) {
            TimeUtils.KeystrokeChecker checker = new TimeUtils.KeystrokeChecker(keyStroke.supplier);

            // tick 0
            checker.updateStateForNextTick();

            // tick 1
            keyStroke.revertKeystrokeResult();
            assertThat(checker.isPressed())
                    .as("released key should be pressed now, vice versa")
                    .isEqualTo(!expected);
        }
    }
}
