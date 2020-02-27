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
    private Object mock3 = mock(Object.class);
    private Object[] singleMock = {mock1};
    private Object[] twoMocks = {mock1, mock2};
    private Object[] threeMocks = {mock1, mock2, mock3};

    private MockCoachRunnable when1 = mock(MockCoachRunnable.class);
    private MockCoachRunnable when2 = mock(MockCoachRunnable.class);
    private MockCoachRunnable when3 = mock(MockCoachRunnable.class);
    private MockCoachRunnable[] singleWhen = {when1};
    private MockCoachRunnable[] twoWhens = {when1, when2};
    private MockCoachRunnable[] threeWhens = {when1, when2, when3};

    private MockCoachRunnable verify1 = mock(MockCoachRunnable.class);
    private MockCoachRunnable verify2 = mock(MockCoachRunnable.class);
    private MockCoachRunnable verify3 = mock(MockCoachRunnable.class);
    private MockCoachRunnable[] singleVerify = {verify1};
    private MockCoachRunnable[] twoVerifies = {verify1, verify2};
    private MockCoachRunnable[] threeVerifies = {verify1, verify2, verify3};

    enum MockEnum {
        SECOND
    }

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    class Constructor {
        @Test
        public void success() {
            new MockCoach(twoMocks, twoWhens, twoVerifies);
        }

        @Test
        public void whenMocksWhensVerifiesAreSingleElementArrays_ThenSuccess() {
            new MockCoach(singleMock, singleWhen, singleVerify);
        }

        @Test
        public void whenMocksAreInCircleChain_ThenSuccess() {
            Object[] mocksInCircleChain = {mock1, mock2, mock1};
            new MockCoach(mocksInCircleChain, threeWhens, threeVerifies);
        }

        @Test
        public void whenMocksIsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks/whens/verifies cannot be null!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(null, null, null)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksLengthDoesNotEqualWhensLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "whens length does not match mocks length!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(twoMocks, singleWhen, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksLengthDoesNotEqualVerifiesLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "verifies length does not match mocks length!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(twoMocks, twoWhens, singleVerify)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksIsEmpty_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks/whens/verifies cannot be empty!";
            Object[] emptyMocks = new Object[0];
            MockCoachRunnable[] emptyWhens = new MockCoachRunnable[0];
            MockCoachRunnable[] emptyVerifies = new MockCoachRunnable[0];

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(emptyMocks, emptyWhens, emptyVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksContainsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be null!";
            Object[] invalidMocks = { mock1, null};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksContainsInteger_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of Integer! Please use LegacyMockCoachBuilder and LegacyMockCoach for Integer support.";
            Object[] invalidMocks = { mock1, 2};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksContainsCharacter_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of Character! Please use LegacyMockCoachBuilder and LegacyMockCoach for Character support.";
            Object[] invalidMocks = { mock1, 'B'};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksContainsString_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of String! Please use LegacyMockCoachBuilder and LegacyMockCoach for String support.";
            Object[] invalidMocks = { mock1, "Second Mock"};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksContainsEnum_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of Enum! Please use LegacyMockCoachBuilder and LegacyMockCoach for Enum support.";
            Object[] invalidMocks = { mock1, MockEnum.SECOND};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenMocksInACyclicGraphThatIsNotACircleChain_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[2] cannot be the same as a previous mock in mocks!";
            Object[] invalidMocks = { mock1, mock2, mock2};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, threeWhens, threeVerifies)
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