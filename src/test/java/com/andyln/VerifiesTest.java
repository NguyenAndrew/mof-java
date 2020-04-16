package com.andyln;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

class VerifiesTest {

    private final Object mock1 = mock(Object.class);
    private final Object mock2 = mock(Object.class);

    private final MockCoachRunnable verify1 = mock(MockCoachRunnable.class);

    @Nested
    class Constructor {

        @Test
        void success() {
            // Example of Verifies API
            Verifies defaultVerifies = new Verifies(
                mock1, (MockCoachRunnable) () -> {
                    // verify(mock1, times(1)).someMethod();
                    // verify(mock2, times(1)).someOtherMethod();
                },
                mock2, (MockCoachRunnable) () -> {
                    // verify(mock2, times(1)).anotherMethod();
                    // verify(mock2, times(1)).anotherMethod2();
                    // verify(mock2, times(1)).anotherMethod3();
                }
            );

            assertNotNull(defaultVerifies);
        }

        @Test
        void whenNothingPassedIntoConstructor_thenThrowIllegalArgumentException() {
            String expectedMessage = "Passed in Mocks and Runnables array must not be empty!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    Verifies::new
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenOddSizedArrayPassedIntoConstructor_thenThrowIllegalArgumentException() {
            String expectedMessage = "Passed in Mocks and Runnables array must come in pairs! Please check this array, as it is an odd length.";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Verifies(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenSecondValuePassedIntoConstructorIsNotAMockCoachRunnable_thenThrowIllegalArgumentException() {
            String expectedMessage = "alternatingMocksAndRunnables[1] must be an instance of MockCoachRunnable!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Verifies(mock1, null)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }
    }

    @Nested
    class getMocks {

        @Test
        void success() {
            List<Object> expected = Collections.singletonList(mock1);

            Verifies verifies = new Verifies(mock1, verify1);

            List<Object> actual = verifies.getMocks();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class getRunnables {

        @Test
        void success() {
            List<MockCoachRunnable> expected = Collections.singletonList(verify1);

            Verifies verifies = new Verifies(mock1, verify1);

            List<MockCoachRunnable> actual = verifies.getRunnables();

            assertEquals(expected, actual);
        }
    }
}