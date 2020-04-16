package com.andyln;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class WhensTest {

    private final Object mock1 = mock(Object.class);
    private final Object mock2 = mock(Object.class);

    private final MockCoachRunnable when1 = mock(MockCoachRunnable.class);

    @Nested
    class Constructor {

        @Test
        void success() {
            // Example of Whens API
            Whens whens = new Whens(
                mock1, (MockCoachRunnable) () -> {
                    // when(mock1.someMethod).thenReturn(someValue);
                    // when(mock1.someOtherMethod).thenReturn(someOtherValue);
                },
                mock2, (MockCoachRunnable) () -> {
                    // when(mock2.anotherMethod).thenReturn(anotherValue);
                    // when(mock2.anotherMethod2).thenReturn(anotherValue2);
                    // when(mock2.anotherMethod3).thenReturn(anotherValue3);
                }
            );

            assertNotNull(whens);
        }

        @Test
        void whenNothingPassedIntoConstructor_thenThrowIllegalArgumentException() {
            String expectedMessage = "Passed in Mocks and Runnables array must not be empty!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    Whens::new
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenOddSizedArrayPassedIntoConstructor_thenThrowIllegalArgumentException() {
            String expectedMessage = "Passed in Mocks and Runnables array must come in pairs! Please check this array, as it is an odd length.";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Whens(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenSecondValuePassedIntoConstructorIsNotAMockCoachRunnable_thenThrowIllegalArgumentException() {
            String expectedMessage = "alternatingMocksAndRunnables[1] must be an instance of MockCoachRunnable!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Whens(mock1, null)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }
    }

    @Nested
    class getMocks {

        @Test
        void success() {
            List<Object> expected = Collections.singletonList(mock1);

            Whens whens = new Whens(mock1, when1);

            List<Object> actual = whens.getMocks();

            assertEquals(expected, actual);
        }
    }

    @Nested
    class getRunnables {

        @Test
        void success() {
            List<MockCoachRunnable> expected = Collections.singletonList(when1);

            Whens whens = new Whens(mock1, when1);

            List<MockCoachRunnable> actual = whens.getRunnables();

            assertEquals(expected, actual);
        }
    }
}