package com.andyln;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MockCoachTest {

    private Object mock1 = mock(Object.class);
    private Object mock2 = mock(Object.class);
    private Object[] singleMock = {mock1};
    private Object[] twoMocks = {mock1, mock2};
    private Object[] threeMocksInCircleChain = {mock1, mock2, mock1};

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

    private MockCoach mockCoachSingleMock = new MockCoach(singleMock, singleWhen, singleVerify);
    private MockCoach mockCoachTwoMocks = new MockCoach(twoMocks, twoWhens, twoVerifies);
    private MockCoach mockCoachThreeMocksInCircleChain = new MockCoach(threeMocksInCircleChain, threeWhens, threeVerifies);

    @Nested
    class Constructor {

        @Test
        void success() {
            new MockCoach(twoMocks, twoWhens, twoVerifies);
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() {
            new MockCoach(singleMock, singleWhen, singleVerify);
        }

        @Test
        void whenMocksAreInCircleChain_ThenSuccess() {
            new MockCoach(threeMocksInCircleChain, threeWhens, threeVerifies);
        }

        @Test
        void whenMocksIsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks/whens/verifies cannot be null!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(null, null, null)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksLengthDoesNotEqualWhensLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "whens length does not match mocks length!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(twoMocks, singleWhen, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksLengthDoesNotEqualVerifiesLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "verifies length does not match mocks length!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(twoMocks, twoWhens, singleVerify)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksIsEmpty_ThenThrowIllegalArgumentException() {
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
        void whenMocksContainsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be null!";
            Object[] invalidMocks = {mock1, null};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksContainsInteger_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of Integer! Please use LegacyMockCoachBuilder and LegacyMockCoach for Integer support.";
            Object[] invalidMocks = {mock1, 2};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksContainsCharacter_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of Character! Please use LegacyMockCoachBuilder and LegacyMockCoach for Character support.";
            Object[] invalidMocks = {mock1, 'B'};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksContainsString_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of String! Please use LegacyMockCoachBuilder and LegacyMockCoach for String support.";
            Object[] invalidMocks = {mock1, "Second Mock"};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksContainsEnum_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[1] cannot be instance of Enum! Please use LegacyMockCoachBuilder and LegacyMockCoach for Enum support.";
            Object[] invalidMocks = {mock1, MockEnum.SECOND};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksInACyclicGraphThatIsNotACircleChain_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks[2] cannot be the same as a previous mock in mocks!";
            Object[] invalidMocks = {mock1, mock2, mock2};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoach(invalidMocks, threeWhens, threeVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

    }

    @Nested
    class WhenBefore {

        @Test
        void success() throws Exception {
            mockCoachTwoMocks.whenBefore(mock2);

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.whenBefore(mock1);

            verify(when1, times(0)).run();
        }

        @Test
        void whenWhenBeforeMiddleMockInCircleChainMocks_ThenSuccess() throws Exception {
            mockCoachThreeMocksInCircleChain.whenBefore(mock2);

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenWhenBefore_CalledWithFirstMockInCircleChainMocks_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call whenBefore(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use whenBeforeFirst() or whenBeforeLast()";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.whenBefore(mock1);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenWhenBefore_CalledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
            String expectedMessage = "Cannot call whenBefore(Object mock) for mock not in mocks!";
            Object mockNotInMocks = mock(Object.class);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.whenBefore(mockNotInMocks);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenWhenBefore_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "whens[0] throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.whenBefore(mock2);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

    }

    @Nested
    class WhenBeforeFirst {

        @Test
        void success() throws Exception {
            mockCoachThreeMocksInCircleChain.whenBeforeFirst();

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.whenBeforeFirst();

            verify(when1, times(0)).run();
        }

        @Test
        void whenWhenBeforeFirst_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call whenBeforeFirst() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_FIRST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachTwoMocks.whenBeforeFirst();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
        }

    }

    @Nested
    class WhenBeforeLast {

        @Test
        void success() throws Exception {
            mockCoachThreeMocksInCircleChain.whenBeforeLast();

            verify(when1, times(1)).run();
            verify(when2, times(1)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.whenBeforeLast();

            verify(when1, times(0)).run();
        }

        @Test
        void whenWhenBeforeLast_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call whenBeforeLast() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_LAST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachTwoMocks.whenBeforeLast();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
        }

        @Test
        void whenWhenBeforeLast_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "whens[0] throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.whenBeforeLast();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

    }

    @Nested
    class WhenEverything {

        @Test
        void success() throws Exception {
            mockCoachTwoMocks.whenEverything();

            verify(when1, times(1)).run();
            verify(when2, times(1)).run();
        }

        @Test
        void whenThreeMocksInCircleChain_Thensuccess() throws Exception {
            mockCoachThreeMocksInCircleChain.whenEverything();

            verify(when1, times(1)).run();
            verify(when2, times(1)).run();
            verify(when3, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.whenEverything();

            verify(when1, times(1)).run();
        }

        @Test
        void whenWhenEverything_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "whens[0] throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.whenEverything();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

    }

    @Nested
    class VerifyBefore {

        @Test
        void success() throws Exception {
            mockCoachTwoMocks.verifyBefore(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verifyBefore(mock1);

            verify(verify1, times(0)).run();
        }

        @Test
        void whenVerifyBeforeMiddleMockInCircleChainMocks_ThenSuccess() throws Exception {
            mockCoachThreeMocksInCircleChain.verifyBefore(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerifyBefore_CalledWithFirstMockInCircleChainMocks_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyBefore(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyBeforeFirst() or verifyBeforeLast()";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyBefore(mock1);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerifyBefore_CalledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
            String expectedMessage = "Cannot call verifyBefore(Object mock) for mock not in mocks!";
            Object mockNotInMocks = mock(Object.class);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyBefore(mockNotInMocks);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerifyBefore_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "verifies[0] throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyBefore(mock2);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class Verify {

        @Test
        void success() throws Exception {
            mockCoachTwoMocks.verify(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verify(mock1);

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyMiddleMockInCircleChainMocks_ThenSuccess() throws Exception {
            mockCoachThreeMocksInCircleChain.verify(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerify_CalledWithFirstMockInCircleChainMocks_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verify(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyFirst() or verifyLast()";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verify(mock1);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerify_CalledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
            String expectedMessage = "Cannot call verify(Object mock) for mock not in mocks!";
            Object mockNotInMocks = mock(Object.class);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verify(mockNotInMocks);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerify_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "verifies[0] throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verify(mock2);
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyBeforeFirst {

        @Test
        void success() throws Exception {
            mockCoachThreeMocksInCircleChain.verifyBeforeFirst();

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verifyBeforeFirst();

            verify(verify1, times(0)).run();
        }

        @Test
        void whenVerifyBeforeFirst_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyBeforeFirst() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_FIRST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachTwoMocks.verifyBeforeFirst();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

    }

    @Nested
    class VerifyBeforeLast {

        @Test
        void success() throws Exception {
            mockCoachThreeMocksInCircleChain.verifyBeforeLast();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verifyBeforeLast();

            verify(verify1, times(0)).run();
        }

        @Test
        void whenVerifyBeforeLast_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyBeforeLast() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_LAST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachTwoMocks.verifyBeforeLast();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenVerifyBeforeLast_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "verifies[0] throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyBeforeLast();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyFirst {

        @Test
        void success() throws Exception {
            mockCoachThreeMocksInCircleChain.verifyFirst();

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verifyFirst();

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyFirst_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyFirst() for mocks in a path graph! For mocks in a path graph, use verify(INSERT_FIRST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachTwoMocks.verifyFirst();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenVerifyFirst_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "verifies[0] throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyFirst();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyLast {

        @Test
        void success() throws Exception {
            mockCoachThreeMocksInCircleChain.verifyLast();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verifyLast();

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyLast_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyLast() for mocks in a path graph! For mocks in a path graph, use verify(INSERT_LAST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> {
                        mockCoachTwoMocks.verifyLast();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenVerifyLast_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "verifies[0] throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyLast();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyEverything {

        @Test
        void success() throws Exception {
            mockCoachTwoMocks.verifyEverything();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
        }

        @Test
        void whenThreeMocksInCircleChain_Thensuccess() throws Exception {
            mockCoachThreeMocksInCircleChain.verifyEverything();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachSingleMock.verifyEverything();

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyEverything_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "verifies[0] throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> {
                        mockCoachThreeMocksInCircleChain.verifyEverything();
                    }
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

}