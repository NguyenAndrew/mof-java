package com.andyln;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;

class MockCoachTest {

    private Object mock1 = mock(Object.class);
    private Object mock2 = mock(Object.class);
    private Object[] mocks = {mock1, mock2};

    private MockCoachRunnable when1 = mock(MockCoachRunnable.class);
    private MockCoachRunnable when2 = mock(MockCoachRunnable.class);
    private MockCoachRunnable[] whens = {when1, when2};

    private MockCoachRunnable verify1 = mock(MockCoachRunnable.class);
    private MockCoachRunnable verify2 = mock(MockCoachRunnable.class);
    private MockCoachRunnable[] verifies = {verify1, verify2};

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    class Constructor {
        @Test
        public void success() {
            new MockCoach(mocks,whens,verifies);
        }

        @Test
        public void whenMocksLengthDoesNotEqualWhensLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "whens length does not match mocks length!";
            MockCoachRunnable[] singleWhen = {when1};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                        new MockCoach(mocks, singleWhen, verifies);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksLengthDoesNotEqualVerifiesLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "verifies length does not match whens length!";
            MockCoachRunnable[] singleVerify = {verify1};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                        new MockCoach(mocks, whens, singleVerify);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

    }

    @Nested
    class WhenBefore {

    }

    @Nested
    class WhenEverything {

    }

    @Nested
    class VerifyBefore {

    }

    @Nested
    class Verify {

    }

    @Nested
    class VerifyEverything {

    }

}