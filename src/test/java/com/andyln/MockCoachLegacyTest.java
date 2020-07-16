package com.andyln;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class MockCoachLegacyTest {

    private final Object mock1 = mock(Object.class);
    private final Object mock2 = mock(Object.class);
    private final Object[] singleMock = {mock1};
    private final Object[] twoMocks = {mock1, mock2};
    private final Object[] threeMocksInCircleChain = {mock1, mock2, mock1};

    private final WhenLambda when1 = mock(WhenLambda.class);
    private final WhenLambda when2 = mock(WhenLambda.class);
    private final WhenLambda when3 = mock(WhenLambda.class);
    private final WhenLambda[] singleWhen = {when1};
    private final WhenLambda[] twoWhens = {when1, when2};
    private final WhenLambda[] threeWhens = {when1, when2, when3};

    private final VerifyLambda verify1 = mock(VerifyLambda.class);
    private final VerifyLambda verify2 = mock(VerifyLambda.class);
    private final VerifyLambda verify3 = mock(VerifyLambda.class);
    private final VerifyLambda[] singleVerify = {verify1};
    private final VerifyLambda[] twoVerifies = {verify1, verify2};
    private final VerifyLambda[] threeVerifies = {verify1, verify2, verify3};

    enum MockEnum {
        SECOND
    }

    private final MockCoach mockCoachLegacySingleMock = new MockCoachLegacy(singleMock, singleWhen, singleVerify);
    private final MockCoach mockCoachLegacyTwoMocks = new MockCoachLegacy(twoMocks, twoWhens, twoVerifies);
    private final MockCoach mockCoachLegacyThreeMocksInCircleChain = new MockCoachLegacy(threeMocksInCircleChain, threeWhens, threeVerifies);

    @BeforeEach
    void initMocks() {
        MockitoAnnotations.initMocks(this);
    }

    @Nested
    class ConstructorWithObjectsAndMockCoachRunnables {

        @Test
        void success() {
            new MockCoachLegacy(twoMocks, twoWhens, twoVerifies);
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() {
            new MockCoachLegacy(singleMock, singleWhen, singleVerify);
        }

        @Test
        void whenMocksAreInCircleChain_ThenSuccess() {
            new MockCoachLegacy(threeMocksInCircleChain, threeWhens, threeVerifies);
        }

        @Test
        void whenMocksContainsInteger_ThenSuccess() {
            Object[] validMocks = {mock1, 2};
            new MockCoachLegacy(validMocks, twoWhens, twoVerifies);
        }

        @Test
        void whenMocksContainsCharacter_ThenSuccess() {
            Object[] validMocks = {mock1, 'B'};
            new MockCoachLegacy(validMocks, twoWhens, twoVerifies);
        }

        @Test
        void whenMocksContainsString_ThenSuccess() {
            Object[] validMocks = {mock1, "Second Mock"};
            new MockCoachLegacy(validMocks, twoWhens, twoVerifies);
        }

        @Test
        void whenMocksContainsEnum_ThenSuccess() {
            Object[] validMocks = {mock1, MockEnum.SECOND};
            new MockCoachLegacy(validMocks, twoWhens, twoVerifies);
        }

        @Test
        void whenMocksIsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks/whens/verifies cannot be null!";

            Object[] mocks = null;
            WhenLambda[] whens = null;
            VerifyLambda[] verifies = null;

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoachLegacy(mocks, whens, verifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksLengthDoesNotEqualWhensLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "whens length does not match mocks length!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoachLegacy(twoMocks, singleWhen, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksLengthDoesNotEqualVerifiesLength_ThenThrowIllegalArgumentException() {
            String expectedMessage = "verifies length does not match mocks length!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoachLegacy(twoMocks, twoWhens, singleVerify)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksIsEmpty_ThenThrowIllegalArgumentException() {
            String expectedMessage = "mocks/whens/verifies cannot be empty!";
            Object[] emptyMocks = new Object[0];
            WhenLambda[] emptyWhens = new WhenLambda[0];
            VerifyLambda[] emptyVerifies = new VerifyLambda[0];

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoachLegacy(emptyMocks, emptyWhens, emptyVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksContainsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "m2 cannot be null!";
            Object[] invalidMocks = {mock1, null};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoachLegacy(invalidMocks, twoWhens, twoVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksInACyclicGraphThatIsNotACircleChain_ThenThrowIllegalArgumentException() {
            String expectedMessage = "m3 cannot be the same as a previous mock in mocks!";
            Object[] invalidMocks = {mock1, mock2, mock2};

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new MockCoachLegacy(invalidMocks, threeWhens, threeVerifies)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

    }

    @Nested
    class WhenBefore {

        @Test
        void success() throws Exception {
            mockCoachLegacyTwoMocks.whenBefore(mock2);

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.whenBefore(mock1);

            verify(when1, times(0)).run();
        }

        @Test
        void whenWhenBeforeMiddleMockInCircleChainMocks_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.whenBefore(mock2);

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenWhenBefore_CalledWithFirstMockInCircleChainMocks_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call whenBefore(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use whenBeforeFirst() or whenBeforeLast()";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.whenBefore(mock1)
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
                    () -> mockCoachLegacyThreeMocksInCircleChain.whenBefore(mockNotInMocks)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenWhenBefore_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "w1 throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.whenBefore(mock2)
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
            mockCoachLegacyThreeMocksInCircleChain.whenBeforeFirst();

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.whenBeforeFirst();

            verify(when1, times(0)).run();
        }

        @Test
        void whenWhenBeforeFirst_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call whenBeforeFirst() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_FIRST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::whenBeforeFirst
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
            mockCoachLegacyThreeMocksInCircleChain.whenBeforeLast();

            verify(when1, times(1)).run();
            verify(when2, times(1)).run();
            verify(when3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.whenBeforeLast();

            verify(when1, times(0)).run();
        }

        @Test
        void whenWhenBeforeLast_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call whenBeforeLast() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_LAST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::whenBeforeLast
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(0)).run();
            verify(when2, times(0)).run();
        }

        @Test
        void whenWhenBeforeLast_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "w1 throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyThreeMocksInCircleChain::whenBeforeLast
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

    }

    @Nested
    class WhenAll {

        @Test
        void success() throws Exception {
            mockCoachLegacyTwoMocks.whenAll();

            verify(when1, times(1)).run();
            verify(when2, times(1)).run();
        }

        @Test
        void whenThreeMocksInCircleChain_Thensuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.whenAll();

            verify(when1, times(1)).run();
            verify(when2, times(1)).run();
            verify(when3, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.whenAll();

            verify(when1, times(1)).run();
        }

        @Test
        void whenWhenAll_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "w1 throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyThreeMocksInCircleChain::whenAll
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(when1, times(1)).run();
            verify(when2, times(0)).run();
            verify(when3, times(0)).run();
        }

    }

    @Nested
    class WhenTheRest {

        @Test
        public void whenWhenBefore_ThenSuccess() throws Exception {
            mockCoachLegacyTwoMocks.whenBefore(mock1);

            mockCoachLegacyTwoMocks.whenTheRest();

            verifyNoInteractions(when1);
            verify(when2, times(1)).run();
        }

        @Test
        public void whenWhenBeforeFirst_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.whenBeforeFirst();

            mockCoachLegacyThreeMocksInCircleChain.whenTheRest();

            verifyNoInteractions(when1);
            verify(when2, times(1)).run();
            verify(when3, times(1)).run();
        }

        @Test
        public void whenWhenAll_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call whenTheRest()! Must be called only after whenBefore(mock) or whenThroughFirst()";

            mockCoachLegacyTwoMocks.whenAll();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::whenTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenWhenBeforeLast_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call whenTheRest()! Must be called only after whenBefore(mock) or whenThroughFirst()";

            mockCoachLegacyThreeMocksInCircleChain.whenBeforeLast();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::whenTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "w2 throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when2).run();

            mockCoachLegacyTwoMocks.whenBefore(mock1);

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyTwoMocks::whenTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verifyNoInteractions(when1);
            verify(when2, times(1)).run();
        }

    }

    @Nested
    class WhenTheRestAfter {

        private final Object mock3 = mock(Object.class);
        private final Object[] threeMocks = {mock1, mock2, mock3};

        private final MockCoach mockCoachLegacyThreeMocks = new MockCoachLegacy(threeMocks, threeWhens, threeVerifies);

        @Test
        public void whenWhenBefore_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocks.whenBefore(mock1);

            mockCoachLegacyThreeMocks.whenTheRestAfter(mock2);

            verifyNoInteractions(when1);
            verifyNoInteractions(when2);
            verify(when3, times(1)).run();
        }

        @Test
        public void whenWhenBeforeFirst_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.whenBeforeFirst();

            mockCoachLegacyThreeMocksInCircleChain.whenTheRestAfter(mock2);

            verifyNoInteractions(when1);
            verifyNoInteractions(when2);
            verify(when3, times(1)).run();
        }

        @Test
        public void whenWhenAll_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call whenTheRestAfter(Object mock)! Must be called only after whenBefore(mock) or whenThroughFirst()";

            mockCoachLegacyThreeMocks.whenAll();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyThreeMocks.whenTheRestAfter(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenWhenBeforeLast_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call whenTheRestAfter(Object mock)! Must be called only after whenBefore(mock) or whenThroughFirst()";

            mockCoachLegacyThreeMocksInCircleChain.whenBeforeLast();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.whenTheRestAfter(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithFirstMockOrLastMockInCircleChain_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call whenTheRestAfter(Object mock) for first or last mock in circle chain. If specifying first mock, use whenTheRest(). If specifying the last mock, then this method does not have to be called (will have identical functionality)";

            mockCoachLegacyThreeMocksInCircleChain.whenBeforeFirst();

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.whenTheRestAfter(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithLastMockInMocks_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call whenTheRestAfter(Object mock) for the last mock! Not calling this method will have identical functionality";

            mockCoachLegacyThreeMocks.whenBefore(mock1);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocks.whenTheRestAfter(mock3)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call whenTheRestAfter(Object mock) for mock not in mocks!";

            Object mockNotInMocks = mock(Object.class);

            mockCoachLegacyThreeMocks.whenBefore(mock1);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocks.whenTheRestAfter(mockNotInMocks)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockBeforePreviouslyUsedMock_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call whenTheRestAfter(Object mock) for a mock located before previously used mock! Make sure correct mock is being passed into this method";

            mockCoachLegacyThreeMocks.whenBefore(mock2);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocks.whenTheRestAfter(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "w3 throws an exception! Please check your whens.";

            doThrow(new Exception()).when(when3).run();

            mockCoachLegacyThreeMocks.whenBefore(mock1);

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> mockCoachLegacyThreeMocks.whenTheRestAfter(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verifyNoInteractions(when1);
            verifyNoInteractions(when2);
            verify(when3, times(1)).run();
        }
    }

    @Nested
    class VerifyBefore {

        @Test
        void success() throws Exception {
            mockCoachLegacyTwoMocks.verifyBefore(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyBefore(mock1);

            verify(verify1, times(0)).run();
        }

        @Test
        void whenVerifyBeforeMiddleMockInCircleChainMocks_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyBefore(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerifyBefore_CalledWithFirstMockInCircleChainMocks_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyBefore(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyBeforeFirst() or verifyBeforeLast()";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyBefore(mock1)
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
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyBefore(mockNotInMocks)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerifyBefore_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v1 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyBefore(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyThrough {

        @Test
        void success() throws Exception {
            mockCoachLegacyTwoMocks.verifyThrough(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyThrough(mock1);

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyMiddleMockInCircleChainMocks_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyThrough(mock2);

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerify_CalledWithFirstMockInCircleChainMocks_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyThrough(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyThroughFirst() or verifyThroughLast()";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyThrough(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerify_CalledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
            String expectedMessage = "Cannot call verifyThrough(Object mock) for mock not in mocks!";
            Object mockNotInMocks = mock(Object.class);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyThrough(mockNotInMocks)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenVerify_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v1 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyThrough(mock2)
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
            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeFirst();

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyBeforeFirst();

            verify(verify1, times(0)).run();
        }

        @Test
        void whenVerifyBeforeFirst_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyBeforeFirst() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_FIRST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyBeforeFirst
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
            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeLast();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyBeforeLast();

            verify(verify1, times(0)).run();
        }

        @Test
        void whenVerifyBeforeLast_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyBeforeLast() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_LAST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyBeforeLast
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenVerifyBeforeLast_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v1 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyThreeMocksInCircleChain::verifyBeforeLast
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyThroughFirst {

        @Test
        void success() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyThroughFirst();

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyThroughFirst();

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyFirst_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyThroughFirst() for mocks in a path graph! For mocks in a path graph, use verifyThrough(INSERT_FIRST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyThroughFirst
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenVerifyFirst_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v1 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyThreeMocksInCircleChain::verifyThroughFirst
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyThroughLast {

        @Test
        void success() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyThroughLast();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyThroughLast();

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyThroughLast_CalledOnDirectedPathGraph_ThenThrowIllegalStateException() throws Exception {
            String expectedMessage = "Cannot call verifyThroughLast() for mocks in a path graph! For mocks in a path graph, use verifyThrough(INSERT_LAST_MOCK_HERE)";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyThroughLast
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(0)).run();
            verify(verify2, times(0)).run();
        }

        @Test
        void whenVerifyThroughLast_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v1 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyThreeMocksInCircleChain::verifyThroughLast
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }

    }

    @Nested
    class VerifyAll {

        @Test
        void success() throws Exception {
            mockCoachLegacyTwoMocks.verifyAll();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
        }

        @Test
        void whenThreeMocksInCircleChain_Thensuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyAll();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(1)).run();
        }

        @Test
        void whenSingleMockInMocks_ThenSuccess() throws Exception {
            mockCoachLegacySingleMock.verifyAll();

            verify(verify1, times(1)).run();
        }

        @Test
        void whenVerifyAll_CalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v1 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify1).run();

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyThreeMocksInCircleChain::verifyAll
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verify(verify1, times(1)).run();
            verify(verify2, times(0)).run();
            verify(verify3, times(0)).run();
        }
    }

    @Nested
    class VerifyTheRest {

        @Test
        public void whenVerifyBefore_ThenSuccess() throws Exception {
            mockCoachLegacyTwoMocks.verifyBefore(mock1);

            mockCoachLegacyTwoMocks.verifyTheRest();

            verifyNoInteractions(verify1);
            verify(verify2, times(1)).run();
        }

        @Test
        public void whenVerifyThrough_ThenSuccess() throws Exception {
            mockCoachLegacyTwoMocks.verifyThrough(mock1);

            mockCoachLegacyTwoMocks.verifyTheRest();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
        }

        @Test
        public void whenVerifyBeforeFirst_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeFirst();

            mockCoachLegacyThreeMocksInCircleChain.verifyTheRest();

            verifyNoInteractions(verify1);
            verify(verify2, times(1)).run();
            verify(verify3, times(1)).run();
        }

        @Test
        public void whenVerifyFirst_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyThroughFirst();

            mockCoachLegacyThreeMocksInCircleChain.verifyTheRest();

            verify(verify1, times(1)).run();
            verify(verify2, times(1)).run();
            verify(verify3, times(1)).run();
        }

        @Test
        public void whenVerifyAll_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call verifyTheRest()! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()";

            mockCoachLegacyTwoMocks.verifyAll();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenVerifyBeforeLast_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call verifyTheRest()! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()";

            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeLast();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenVerifyThroughLast_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call verifyTheRest()! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()";

            mockCoachLegacyThreeMocksInCircleChain.verifyThroughLast();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    mockCoachLegacyTwoMocks::verifyTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v2 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify2).run();

            mockCoachLegacyTwoMocks.verifyBefore(mock1);

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    mockCoachLegacyTwoMocks::verifyTheRest
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verifyNoInteractions(verify1);
            verify(verify2, times(1)).run();
        }

    }

    @Nested
    class VerifyTheRestAfter {

        private final Object mock3 = mock(Object.class);
        private final Object[] threeMocks = {mock1, mock2, mock3};

        private final MockCoach mockCoachLegacyThreeMocks = new MockCoachLegacy(threeMocks, threeWhens, threeVerifies);

        @Test
        public void whenVerifyBefore_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocks.verifyBefore(mock1);

            mockCoachLegacyThreeMocks.verifyTheRestAfter(mock2);

            verifyNoInteractions(verify1);
            verifyNoInteractions(verify2);
            verify(verify3, times(1)).run();
        }

        @Test
        public void whenVerifyThrough_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocks.verifyThrough(mock1);

            mockCoachLegacyThreeMocks.verifyTheRestAfter(mock2);

            verify(verify1, times(1)).run();
            verifyNoInteractions(verify2);
            verify(verify3, times(1)).run();
        }

        @Test
        public void whenVerifyBeforeFirst_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeFirst();

            mockCoachLegacyThreeMocksInCircleChain.verifyTheRestAfter(mock2);

            verifyNoInteractions(verify1);
            verifyNoInteractions(verify2);
            verify(verify3, times(1)).run();
        }

        @Test
        public void whenVerifyFirst_ThenSuccess() throws Exception {
            mockCoachLegacyThreeMocksInCircleChain.verifyThroughFirst();

            mockCoachLegacyThreeMocksInCircleChain.verifyTheRestAfter(mock2);

            verify(verify1, times(1)).run();
            verifyNoInteractions(verify2);
            verify(verify3, times(1)).run();
        }

        @Test
        public void whenVerifyAll_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock)! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()";

            mockCoachLegacyTwoMocks.verifyAll();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyTwoMocks.verifyTheRestAfter(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenVerifyBeforeLast_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock)! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()";

            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeLast();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyTheRestAfter(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        public void whenVerifyThroughLast_ThenThrowIllegalStateException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock)! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()";

            mockCoachLegacyThreeMocksInCircleChain.verifyThroughLast();

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> mockCoachLegacyTwoMocks.verifyTheRestAfter(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithFirstMockOrLastMockInCircleChain_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock) for first or last mock in circle chain. If specifying first mock, use verifyTheRest(). If specifying the last mock, then this method does not have to be called (will have identical functionality)";

            mockCoachLegacyThreeMocksInCircleChain.verifyBeforeFirst();

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocksInCircleChain.verifyTheRestAfter(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithLastMockInMocks_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock) for the last mock! Not calling this method will have identical functionality";

            mockCoachLegacyThreeMocks.verifyBefore(mock1);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocks.verifyTheRestAfter(mock3)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock) for mock not in mocks!";

            Object mockNotInMocks = mock(Object.class);

            mockCoachLegacyThreeMocks.verifyBefore(mock1);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocks.verifyTheRestAfter(mockNotInMocks)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockBeforePreviouslyUsedMock_ThenThrowIllegalIllegalArgumentException() {
            String expectedMessage = "Cannot call verifyTheRestAfter(Object mock) for a mock located before previously used mock! Make sure correct mock is being passed into this method";

            mockCoachLegacyThreeMocks.verifyBefore(mock2);

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> mockCoachLegacyThreeMocks.verifyTheRestAfter(mock1)
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenCalledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
            String expectedMessage = "v3 throws an exception! Please check your verifies.";

            doThrow(new Exception()).when(verify3).run();

            mockCoachLegacyThreeMocks.verifyBefore(mock1);

            RuntimeException actualException = assertThrows(
                    RuntimeException.class,
                    () -> mockCoachLegacyThreeMocks.verifyTheRestAfter(mock2)
            );

            assertEquals(expectedMessage, actualException.getMessage());

            verifyNoInteractions(verify1);
            verifyNoInteractions(verify2);
            verify(verify3, times(1)).run();
        }

    }

    @Nested
    public class OverloadedConstructors {
        // List of Mocks
        Object m1 = mock(Object.class);
        Object m2 = mock(Object.class);
        Object m3 = mock(Object.class);
        Object m4 = mock(Object.class);
        Object m5 = mock(Object.class);
        Object m6 = mock(Object.class);
        Object m7 = mock(Object.class);
        Object m8 = mock(Object.class);

        @Test
        void OneMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void TwoMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void ThreeMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    },
                    m3,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void FourMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    },
                    m3,
                    () -> {
                    },
                    () -> {
                    },
                    m4,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void FiveMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    },
                    m3,
                    () -> {
                    },
                    () -> {
                    },
                    m4,
                    () -> {
                    },
                    () -> {
                    },
                    m5,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void SixMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    },
                    m3,
                    () -> {
                    },
                    () -> {
                    },
                    m4,
                    () -> {
                    },
                    () -> {
                    },
                    m5,
                    () -> {
                    },
                    () -> {
                    },
                    m6,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void SevenMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    },
                    m3,
                    () -> {
                    },
                    () -> {
                    },
                    m4,
                    () -> {
                    },
                    () -> {
                    },
                    m5,
                    () -> {
                    },
                    () -> {
                    },
                    m6,
                    () -> {
                    },
                    () -> {
                    },
                    m7,
                    () -> {
                    },
                    () -> {
                    }
            );
        }

        @Test
        void EightMock_Success() {
            new MockCoachLegacy(
                    m1,
                    () -> {
                    },
                    () -> {
                    },
                    m2,
                    () -> {
                    },
                    () -> {
                    },
                    m3,
                    () -> {
                    },
                    () -> {
                    },
                    m4,
                    () -> {
                    },
                    () -> {
                    },
                    m5,
                    () -> {
                    },
                    () -> {
                    },
                    m6,
                    () -> {
                    },
                    () -> {
                    },
                    m7,
                    () -> {
                    },
                    () -> {
                    },
                    m8,
                    () -> {
                    },
                    () -> {
                    }
            );
        }
    }

    @Nested
    class Builder {

        @Test
        void staticMethod_success() {
            MockCoachLegacy.builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .build();
        }

        @Test
        void constructor_success() {
            new MockCoachLegacy.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .build();
        }
    }

}