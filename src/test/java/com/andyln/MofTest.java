package com.andyln;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.andyln.Mof.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MofTest {

    private final Object mock1 = mock(Object.class);
    private final Object mock2 = mock(Object.class);
    private final Object mock3 = mock(Object.class);

    private final WhenLambda when1 = mock(WhenLambda.class);
    private final WhenLambda when2 = mock(WhenLambda.class);
    private final WhenLambda when3 = mock(WhenLambda.class);

    private final VerifyLambda verify1 = mock(VerifyLambda.class);
    private final VerifyLambda verify2 = mock(VerifyLambda.class);
    private final VerifyLambda verify3 = mock(VerifyLambda.class);

    private final Mof mofSingleMock = new Mof.Builder()
            .add(
                    mock1,
                    when1,
                    verify1
            )
            .build();
    private final Mof mofTwoMocks = new Mof.Builder()
            .add(
                    mock1,
                    when1,
                    verify1
            )
            .add(
                    mock2,
                    when2,
                    verify3
            )
            .build();
    private final Mof mofThreeMocks = new Mof.Builder()
            .add(
                    mock1,
                    when1,
                    verify1
            )
            .add(
                    mock2,
                    when2,
                    verify3
            )
            .add(
                    mock3,
                    when3,
                    verify3
            )
            .build();

    private final Mof mofTwoMocksInASimpleClosedCurve = new Mof.Builder()
            .add(
                    mock1,
                    when1,
                    verify1
            )
            .add(
                    mock1,
                    when2,
                    verify3
            )
            .build();
    private final Mof mofThreeMocksInASimpleClosedCurve = new Mof.Builder()
            .add(
                    mock1,
                    when1,
                    verify1
            )
            .add(
                    mock2,
                    when2,
                    verify3
            )
            .add(
                    mock1,
                    when3,
                    verify3
            )
            .build();

    @Nested
    class Builder {

        NoInteractionLambda verifyNoInteractionLambda = mock(NoInteractionLambda.class);

        @Test
        void staticMethod_success() {
            Mof.builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .build();
        }

        @Test
        void constructor_success() {
            new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .build();
        }

        @Test
        void copy_success() {
            Mof.Builder originalBuilder = new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    );

            Mof.Builder copiedBuilder = originalBuilder.copy();

            originalBuilder.copy();
            copiedBuilder.copy();
        }

        @Test
        void withVerifyNoInteractions_success() {
            new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .enableVerifyNoInteractions(verifyNoInteractionLambda)
                    .build();
        }

        @Test
        void withTwoMocks_success() {
            new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .add(
                            mock2,
                            when2,
                            verify2
                    )
                    .build();
        }

        @Test
        void withThreeMocks_success() {
            new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .add(
                            mock2,
                            when2,
                            verify2
                    )
                    .add(
                            mock3,
                            when3,
                            verify3
                    )
                    .build();
        }

        @Test
        void whenTwoMocksAreInASimpleClosedCurve_success() {
            new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .add(
                            mock1,
                            when2,
                            verify2
                    )
                    .build();
        }

        @Test
        void whenThreeMocksAreInASimpleClosedCurve_ThenSuccess() {
            new Mof.Builder()
                    .add(
                            mock1,
                            when1,
                            verify1
                    )
                    .add(
                            mock2,
                            when2,
                            verify2
                    )
                    .add(
                            mock1,
                            when3,
                            verify3
                    )
                    .build();
        }

        @Test
        void whenMocksIsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "Cannot add null Mock to Mof Builder!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Mof.Builder()
                            .add(
                                    null,
                                    when1,
                                    verify1
                            )
                            .build()
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenWhenLambdaIsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "Cannot add null WhenLambda to Mof Builder!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Mof.Builder()
                            .add(
                                    mock1,
                                    null,
                                    verify1
                            )
                            .build()
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenVerifyLambdaIsNull_ThenThrowIllegalArgumentException() {
            String expectedMessage = "Cannot add null VerifyLambda to Mof Builder!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Mof.Builder()
                            .add(
                                    mock1,
                                    when1,
                                    null
                            )
                            .build()
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }


        @Test
        void whenBuilding_WithNoMocksAdded_ThenThrowIllegalStateException() {
            String expectedMessage = "Must add at least one mock before calling build on Mof Builder!";

            IllegalStateException actualException = assertThrows(
                    IllegalStateException.class,
                    () -> new Mof.Builder().build()
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksInANonSimpleCurve_ThenThrowIllegalArgumentException() {
            String expectedMessage = "m3 cannot be the same as a previous mock in mocks!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Mof.Builder()
                            .add(
                                    mock1,
                                    when1,
                                    verify1
                            )
                            .add(
                                    mock2,
                                    when2,
                                    verify2
                            )
                            .add(
                                    mock2,
                                    when3,
                                    verify3
                            )
                            .build()
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }
    }

    @Nested
    class When {

        @Nested
        class All {
            @Test
            void success() throws Exception {
                mofSingleMock.when(ALL);

                verify(when1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "w1 throws an exception! Please check your whens.";

                doThrow(new Exception()).when(when1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.when(ALL)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }
    }

    @Nested
    class WhenBefore {

        @Nested
        class First {

            @Test
            void success() throws Exception {
                mofSingleMock.whenBefore(FIRST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                mofSingleMock.whenBefore(LAST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "w1 throws an exception! Please check your whens.";

                doThrow(new Exception()).when(when1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.whenBefore(LAST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                mofSingleMock.whenBefore(mock1);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                mofTwoMocks.whenBefore(mock1);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                mofTwoMocks.whenBefore(mock2);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                mofThreeMocks.whenBefore(mock1);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                mofThreeMocks.whenBefore(mock2);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                mofThreeMocks.whenBefore(mock3);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.whenBefore(mock2);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenBefore(FIRST) or whenBefore(LAST).";

                IllegalStateException actualException = assertThrows(
                        IllegalStateException.class,
                        () -> mofTwoMocksInASimpleClosedCurve.whenBefore(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenBefore(FIRST) or whenBefore(LAST).";

                IllegalStateException actualException = assertThrows(
                        IllegalStateException.class,
                        () -> mofThreeMocksInASimpleClosedCurve.whenBefore(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call whenBefore(Object mock) for mock not in mocks!";
                Object mockNotInMocks = mock(Object.class);

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocks.whenBefore(mockNotInMocks)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "w1 throws an exception! Please check your whens.";

                doThrow(new Exception()).when(when1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.whenBefore(mock3)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }
    }

    @Nested
    class WhenAfter {

        @Nested
        class First {

            @Test
            void success() throws Exception {
                mofSingleMock.whenAfter(FIRST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void calledWithWhenThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "w2 throws an exception! Please check your whens.";

                doThrow(new Exception()).when(when2).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.whenAfter(FIRST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }
        }
    }
}
