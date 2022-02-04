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
                    verify2
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
                    verify2
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
                    verify2
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
                    verify2
            )
            .add(
                    mock1,
                    when3,
                    verify3
            )
            .build();

    NoInteractionLambda verifyNoInteractionLambda = mock(NoInteractionLambda.class);

    private final Mof mofSingleMockWithVerifyNoInteractions = new Mof.Builder()
            .add(
                    mock1,
                    when1,
                    verify1
            )
            .enableVerifyNoInteractions(verifyNoInteractionLambda)
            .build();
    private final Mof mofTwoMocksWithVerifyNoInteractions = new Mof.Builder()
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
            .enableVerifyNoInteractions(verifyNoInteractionLambda)
            .build();
    private final Mof mofThreeMocksWithVerifyNoInteractions = new Mof.Builder()
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
            .enableVerifyNoInteractions(verifyNoInteractionLambda)
            .build();

    private final Mof mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions = new Mof.Builder()
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
            .enableVerifyNoInteractions(verifyNoInteractionLambda)
            .build();
    private final Mof mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions = new Mof.Builder()
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
            .enableVerifyNoInteractions(verifyNoInteractionLambda)
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

        @Nested
        class Remaining {

            @Nested
            class WhenAll {

                @Test
                void success() throws Exception {
                    mofSingleMock.when(ALL);
                    mofSingleMock.when(REMAINING);

                    verify(when1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    mofTwoMocks.when(ALL);
                    mofTwoMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    mofThreeMocks.when(ALL);
                    mofThreeMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                    verify(when3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofTwoMocksInASimpleClosedCurve.when(ALL);
                    mofTwoMocksInASimpleClosedCurve.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofThreeMocksInASimpleClosedCurve.when(ALL);
                    mofThreeMocksInASimpleClosedCurve.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                    verify(when3, times(1)).run();
                }
            }

            @Nested
            class WhenRemaining {

                @Test
                void success() throws Exception {
                    mofSingleMock.when(REMAINING);

                    verify(when1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    mofTwoMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    mofThreeMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                    verify(when3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofTwoMocksInASimpleClosedCurve.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofThreeMocksInASimpleClosedCurve.when(REMAINING);

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
                            () -> mofThreeMocks.when(REMAINING)
                    );

                    assertEquals(expectedMessage, actualException.getMessage());

                    verify(when1, times(1)).run();
                    verify(when2, times(0)).run();
                    verify(when3, times(0)).run();
                }
            }

            @Nested
            class WhenBefore {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.whenBefore(FIRST);
                        mofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.whenBefore(FIRST);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.whenBefore(FIRST);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.whenBefore(FIRST);
                        mofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
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
                        mofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.whenBefore(LAST);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.whenBefore(LAST);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.whenBefore(LAST);
                        mofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.whenBefore(LAST);
                        mofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.whenBefore(mock1);
                        mofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocks.whenBefore(mock1);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocks.whenBefore(mock2);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocks.whenBefore(mock1);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocks.whenBefore(mock2);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocks.whenBefore(mock3);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.whenBefore(mock2);
                        mofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(1)).run();
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
                        mofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.whenAfter(FIRST);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.whenAfter(FIRST);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.whenAfter(FIRST);
                        mofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.whenAfter(FIRST);
                        mofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.whenAfter(LAST);
                        mofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.whenAfter(LAST);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.whenAfter(LAST);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.whenAfter(LAST);
                        mofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.whenAfter(LAST);
                        mofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.whenAfter(mock1);
                        mofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocks.whenAfter(mock1);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocks.whenAfter(mock2);
                        mofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocks.whenAfter(mock1);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocks.whenAfter(mock2);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocks.whenAfter(mock3);
                        mofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.whenAfter(mock2);
                        mofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(1)).run();
                    }
                }

            }
        }

        @Nested
        class Error {

            @Test
            void calledWithNotAllOrRemaining_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "aor must be ALL or REMAINING.";

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.when(null)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
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

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofTwoMocksInASimpleClosedCurve.whenBefore(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenBefore(FIRST) or whenBefore(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
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

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                mofSingleMock.whenAfter(LAST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                mofSingleMock.whenAfter(mock1);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                mofTwoMocks.whenAfter(mock1);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                mofTwoMocks.whenAfter(mock2);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                mofThreeMocks.whenAfter(mock1);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                mofThreeMocks.whenAfter(mock2);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                mofThreeMocks.whenAfter(mock3);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.whenAfter(mock2);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenAfter(FIRST) or whenAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofTwoMocksInASimpleClosedCurve.whenAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenAfter(FIRST) or whenAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocksInASimpleClosedCurve.whenAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call whenAfter(Object mock) for mock not in mocks!";
                Object mockNotInMocks = mock(Object.class);

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocks.whenAfter(mockNotInMocks)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "w2 throws an exception! Please check your whens.";

                doThrow(new Exception()).when(when2).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.whenAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }
        }
    }

    @Nested
    class Verify {

        @Nested
        class All {

            @Test
            void success() throws Exception {
                mofSingleMock.verify(ALL);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v1 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verify(ALL)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Remaining {

            @Nested
            class VerifyAll {

                @Test
                void success() throws Exception {
                    mofSingleMock.verify(ALL);
                    mofSingleMock.verify(REMAINING);

                    verify(verify1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    mofTwoMocks.verify(ALL);
                    mofTwoMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    mofThreeMocks.verify(ALL);
                    mofThreeMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofTwoMocksInASimpleClosedCurve.verify(ALL);
                    mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofThreeMocksInASimpleClosedCurve.verify(ALL);
                    mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }
            }

            @Nested
            class VerifyRemaining {

                @Test
                void success() throws Exception {
                    mofSingleMock.verify(REMAINING);

                    verify(verify1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    mofTwoMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    mofThreeMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }

                @Test
                void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                    String expectedMessage = "v1 throws an exception! Please check your verifies.";

                    doThrow(new Exception()).when(verify1).run();

                    RuntimeException actualException = assertThrows(
                            RuntimeException.class,
                            () -> mofThreeMocks.verify(REMAINING)
                    );

                    assertEquals(expectedMessage, actualException.getMessage());

                    verify(verify1, times(1)).run();
                    verify(verify2, times(0)).run();
                    verify(verify3, times(0)).run();
                }
            }

            @Nested
            class VerifyThrough {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyThrough(FIRST);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(1)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.verifyThrough(FIRST);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.verifyThrough(FIRST);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.verifyThrough(FIRST);
                        mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyThrough(FIRST);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyThrough(LAST);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(1)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.verifyThrough(LAST);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.verifyThrough(LAST);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.verifyThrough(LAST);
                        mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyThrough(LAST);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyThrough(mock1);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(1)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocks.verifyThrough(mock1);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocks.verifyThrough(mock2);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocks.verifyThrough(mock1);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocks.verifyThrough(mock2);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocks.verifyThrough(mock3);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyThrough(mock2);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }
            }

            @Nested
            class VerifyBefore {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyBefore(FIRST);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.verifyBefore(FIRST);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.verifyBefore(FIRST);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.verifyBefore(FIRST);
                        mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyBefore(FIRST);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyBefore(LAST);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.verifyBefore(LAST);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.verifyBefore(LAST);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.verifyBefore(LAST);
                        mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyBefore(LAST);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyBefore(mock1);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocks.verifyBefore(mock1);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocks.verifyBefore(mock2);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocks.verifyBefore(mock1);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocks.verifyBefore(mock2);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocks.verifyBefore(mock3);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyBefore(mock2);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                    }
                }
            }

            @Nested
            class VerifyAfter {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyAfter(FIRST);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.verifyAfter(FIRST);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.verifyAfter(FIRST);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.verifyAfter(FIRST);
                        mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyAfter(FIRST);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyAfter(LAST);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocks.verifyAfter(LAST);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocks.verifyAfter(LAST);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurve.verifyAfter(LAST);
                        mofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyAfter(LAST);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMock.verifyAfter(mock1);
                        mofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocks.verifyAfter(mock1);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocks.verifyAfter(mock2);
                        mofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocks.verifyAfter(mock1);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocks.verifyAfter(mock2);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocks.verifyAfter(mock3);
                        mofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurve.verifyAfter(mock2);
                        mofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                    }
                }
            }
        }

        @Nested
        class Error {

            @Test
            void calledWithNotAllOrRemaining_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "aor must be ALL or REMAINING.";

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verify(null)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }
    }

    @Nested
    class VerifyThrough {

        @Nested
        class First {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithWhenThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v1 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyThrough(FIRST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyThrough(LAST);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v1 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyThrough(LAST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyThrough(mock1);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                mofTwoMocks.verifyThrough(mock1);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                mofTwoMocks.verifyThrough(mock2);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                mofThreeMocks.verifyThrough(mock1);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                mofThreeMocks.verifyThrough(mock2);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                mofThreeMocks.verifyThrough(mock3);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyThrough(mock2);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyThrough(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyThrough(FIRST) or verifyThrough(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofTwoMocksInASimpleClosedCurve.verifyThrough(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyThrough(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyThrough(FIRST) or verifyThrough(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocksInASimpleClosedCurve.verifyThrough(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyThrough(Object mock) for mock not in mocks!";
                Object mockNotInMocks = mock(Object.class);

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocks.verifyThrough(mockNotInMocks)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v1 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyThrough(mock3)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }
    }

    @Nested
    class VerifyBefore {

        @Nested
        class First {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyBefore(LAST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v1 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyBefore(LAST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyBefore(mock1);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                mofTwoMocks.verifyBefore(mock1);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                mofTwoMocks.verifyBefore(mock2);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                mofThreeMocks.verifyBefore(mock1);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                mofThreeMocks.verifyBefore(mock2);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                mofThreeMocks.verifyBefore(mock3);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyBefore(mock2);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyBefore(FIRST) or verifyBefore(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofTwoMocksInASimpleClosedCurve.verifyBefore(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyBefore(FIRST) or verifyBefore(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocksInASimpleClosedCurve.verifyBefore(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyBefore(Object mock) for mock not in mocks!";
                Object mockNotInMocks = mock(Object.class);

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocks.verifyBefore(mockNotInMocks)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v1 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify1).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyBefore(mock3)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }
    }

    @Nested
    class VerifyAfter {

        @Nested
        class First {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void calledWithWhenThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v2 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify2).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyAfter(FIRST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyAfter(LAST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocks.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocks.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurve.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                mofSingleMock.verifyAfter(mock1);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                mofTwoMocks.verifyAfter(mock1);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                mofTwoMocks.verifyAfter(mock2);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                mofThreeMocks.verifyAfter(mock1);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                mofThreeMocks.verifyAfter(mock2);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                mofThreeMocks.verifyAfter(mock3);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurve.verifyAfter(mock2);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyAfter(FIRST) or verifyAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofTwoMocksInASimpleClosedCurve.verifyAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyAfter(FIRST) or verifyAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocksInASimpleClosedCurve.verifyAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyAfter(Object mock) for mock not in mocks!";
                Object mockNotInMocks = mock(Object.class);

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocks.verifyAfter(mockNotInMocks)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "v2 throws an exception! Please check your verifies.";

                doThrow(new Exception()).when(verify2).run();

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }
        }
    }

    @Nested
    class VerifyNoInteractions {

        @Nested
        class All {

            @Test
            void success() throws Exception {
                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(ALL);

                verify(verifyNoInteractionLambda, times(1)).run(mock1);
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(ALL);

                verify(verifyNoInteractionLambda, times(1)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(ALL);

                verify(verifyNoInteractionLambda, times(1)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
                verify(verifyNoInteractionLambda, times(1)).run(mock3);
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(ALL);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(ALL);

                verify(verifyNoInteractionLambda, times(1)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "verifyNoInteractionLambda called with m1 throws an exception! Please check your verifyNoInteractionLambda and mocks.";

                doThrow(new Exception()).when(verifyNoInteractionLambda).run(mock1);

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(ALL)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(1)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }
        }

        @Nested
        class Remaining {

            @Nested
            class Verify {

                @Nested
                class All {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verify(ALL);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verify(ALL);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verify(ALL);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(ALL);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(ALL);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }
                }

                @Nested
                class RemainingNested {

                    @Nested
                    class VerifyAll {

                        @Test
                        void success() throws Exception {
                            mofSingleMockWithVerifyNoInteractions.verify(ALL);
                            mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                            mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        }

                        @Test
                        void twoMocks_success() throws Exception {
                            mofTwoMocksWithVerifyNoInteractions.verify(ALL);
                            mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                            mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        }

                        @Test
                        void threeMocks_success() throws Exception {
                            mofThreeMocksWithVerifyNoInteractions.verify(ALL);
                            mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                            mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verify3, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            verify(verifyNoInteractionLambda, times(0)).run(mock3);
                        }

                        @Test
                        void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                            mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(ALL);
                            mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                            mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        }

                        @Test
                        void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                            mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(ALL);
                            mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verify3, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        }
                    }

                    @Nested
                    class VerifyRemaining {

                        @Test
                        void success() throws Exception {
                            mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                            mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        }

                        @Test
                        void twoMocks_success() throws Exception {
                            mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                            mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        }

                        @Test
                        void threeMocks_success() throws Exception {
                            mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                            mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verify3, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            verify(verifyNoInteractionLambda, times(0)).run(mock3);
                        }

                        @Test
                        void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                            mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                            mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        }

                        @Test
                        void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                            mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                            mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);

                            verify(verify1, times(1)).run();
                            verify(verify2, times(1)).run();
                            verify(verify3, times(1)).run();
                            verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        }
                    }

                    @Nested
                    class VerifyThrough {

                        @Nested
                        class First {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyThrough(FIRST);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyThrough(FIRST);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyThrough(FIRST);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(FIRST);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(FIRST);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }
                        }

                        @Nested
                        class Last {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyThrough(LAST);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyThrough(LAST);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyThrough(LAST);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(LAST);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(LAST);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }
                        }

                        @Nested
                        class Mock {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyThrough(mock1);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_onFirstMock_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyThrough(mock1);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void twoMocks_onSecondMock_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyThrough(mock2);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_onFirstMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyThrough(mock1);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocks_onSecondMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyThrough(mock2);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocks_onThirdMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyThrough(mock3);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(mock2);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }
                        }
                    }

                    @Nested
                    class VerifyBefore {

                        @Nested
                        class First {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyBefore(FIRST);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyBefore(FIRST);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyBefore(FIRST);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(FIRST);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(FIRST);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }
                        }

                        @Nested
                        class Last {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyBefore(LAST);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyBefore(LAST);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyBefore(LAST);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(LAST);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(LAST);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }
                        }

                        @Nested
                        class Mock {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyBefore(mock1);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_onFirstMock_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyBefore(mock1);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void twoMocks_onSecondMock_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyBefore(mock2);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_onFirstMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyBefore(mock1);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocks_onSecondMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyBefore(mock2);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocks_onThirdMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyBefore(mock3);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(mock2);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(1)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }
                        }
                    }

                    @Nested
                    class VerifyAfter {

                        @Nested
                        class First {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyAfter(FIRST);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyAfter(FIRST);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyAfter(FIRST);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(FIRST);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(FIRST);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }
                        }

                        @Nested
                        class Last {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyAfter(LAST);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyAfter(LAST);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyAfter(LAST);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(LAST);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(LAST);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }
                        }

                        @Nested
                        class Mock {

                            @Test
                            void success() throws Exception {
                                mofSingleMockWithVerifyNoInteractions.verifyAfter(mock1);
                                mofSingleMockWithVerifyNoInteractions.verify(REMAINING);
                                mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                            }

                            @Test
                            void twoMocks_onFirstMock_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyAfter(mock1);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void twoMocks_onSecondMock_success() throws Exception {
                                mofTwoMocksWithVerifyNoInteractions.verifyAfter(mock2);
                                mofTwoMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                            }

                            @Test
                            void threeMocks_onFirstMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyAfter(mock1);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(1)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocks_onSecondMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyAfter(mock2);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocks_onThirdMock_success() throws Exception {
                                mofThreeMocksWithVerifyNoInteractions.verifyAfter(mock3);
                                mofThreeMocksWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(0)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }

                            @Test
                            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(mock2);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verify(REMAINING);
                                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                                verify(verify1, times(0)).run();
                                verify(verify2, times(0)).run();
                                verify(verify3, times(1)).run();
                                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                                verify(verifyNoInteractionLambda, times(0)).run(mock3);
                            }
                        }
                    }
                }
            }

            @Nested
            class VerifyThrough {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyThrough(FIRST);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyThrough(FIRST);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyThrough(FIRST);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                        verify(verifyNoInteractionLambda, times(1)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(FIRST);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(FIRST);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyThrough(LAST);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyThrough(LAST);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyThrough(LAST);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(LAST);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(LAST);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyThrough(mock1);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyThrough(mock1);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyThrough(mock2);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyThrough(mock1);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                        verify(verifyNoInteractionLambda, times(1)).run(mock3);
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyThrough(mock2);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(1)).run(mock3);
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyThrough(mock3);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyThrough(mock2);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }
                }
            }

            @Nested
            class VerifyBefore {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyBefore(FIRST);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyBefore(FIRST);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyBefore(FIRST);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                        verify(verifyNoInteractionLambda, times(1)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(FIRST);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(FIRST);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyBefore(LAST);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyBefore(LAST);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyBefore(LAST);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(LAST);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(LAST);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyBefore(mock1);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyBefore(mock1);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyBefore(mock2);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyBefore(mock1);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(1)).run(mock2);
                        verify(verifyNoInteractionLambda, times(1)).run(mock3);
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyBefore(mock2);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(1)).run(mock3);
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyBefore(mock3);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyBefore(mock2);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }
                }
            }

            @Nested
            class VerifyAfter {

                @Nested
                class First {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyAfter(FIRST);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyAfter(FIRST);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyAfter(FIRST);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(FIRST);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(FIRST);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyAfter(LAST);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyAfter(LAST);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyAfter(LAST);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(LAST);
                        mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(LAST);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        mofSingleMockWithVerifyNoInteractions.verifyAfter(mock1);
                        mofSingleMockWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyAfter(mock1);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        mofTwoMocksWithVerifyNoInteractions.verifyAfter(mock2);
                        mofTwoMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyAfter(mock1);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyAfter(mock2);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        mofThreeMocksWithVerifyNoInteractions.verifyAfter(mock3);
                        mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyAfter(mock2);
                        mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractions(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                        verify(verifyNoInteractionLambda, times(0)).run(mock1);
                        verify(verifyNoInteractionLambda, times(0)).run(mock2);
                        verify(verifyNoInteractionLambda, times(0)).run(mock3);
                    }
                }
            }

            @Nested
            class Error {

                @Test
                void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                    String expectedMessage = "verifyNoInteractionLambda called with m1 throws an exception! Please check your verifyNoInteractionLambda and mocks.";

                    doThrow(new Exception()).when(verifyNoInteractionLambda).run(mock1);

                    RuntimeException actualException = assertThrows(
                            RuntimeException.class,
                            () -> mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(REMAINING)
                    );

                    assertEquals(expectedMessage, actualException.getMessage());

                    verify(verifyNoInteractionLambda, times(1)).run(mock1);
                    verify(verifyNoInteractionLambda, times(0)).run(mock2);
                    verify(verifyNoInteractionLambda, times(0)).run(mock3);
                }
            }
        }

        @Nested
        class Error {

            @Test
            void calledWhenNoInteractionsIsNotEnabled_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "Must enableVerifyNoInteractions before calling verifyNoInteractions.";

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyNoInteractions(ALL)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }

            @Test
            void calledWithNotAllOrRemaining_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "aor must be ALL or REMAINING.";

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocksWithVerifyNoInteractions.verifyNoInteractions(null)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }
        }
    }

    @Nested
    class VerifyNoInteractionsAfter {

        @Nested
        class First {

            @Test
            void success() throws Exception {
                mofSingleMockWithVerifyNoInteractions.verifyNoInteractionsAfter(FIRST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(FIRST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(FIRST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
                verify(verifyNoInteractionLambda, times(1)).run(mock3);
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(FIRST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(FIRST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
            }

            @Test
            void calledWithWhenThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "verifyNoInteractionLambda called with m2 throws an exception! Please check your verifyNoInteractionLambda and mocks.";

                doThrow(new Exception()).when(verifyNoInteractionLambda).run(mock2);

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(FIRST)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                mofSingleMockWithVerifyNoInteractions.verifyNoInteractionsAfter(LAST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void twoMocks_success() throws Exception {
                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(LAST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
            }

            @Test
            void threeMocks_success() throws Exception {
                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(LAST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(LAST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(LAST);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                mofSingleMockWithVerifyNoInteractions.verifyNoInteractionsAfter(mock1);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mock1);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                mofTwoMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mock2);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mock1);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
                verify(verifyNoInteractionLambda, times(1)).run(mock3);
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mock2);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(1)).run(mock3);
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mock3);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(mock2);

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyNoInteractionsAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyNoInteractionsAfter(FIRST) or verifyNoInteractionsAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofTwoMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyNoInteractionsAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyNoInteractionsAfter(FIRST) or verifyNoInteractionsAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocksInASimpleClosedCurveWithVerifyNoInteractions.verifyNoInteractionsAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyNoInteractionsAfter(Object mock) for mock not in mocks!";
                Object mockNotInMocks = mock(Object.class);

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mockNotInMocks)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }

            @Test
            void calledWithMockThatThrowsException_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "verifyNoInteractionLambda called with m2 throws an exception! Please check your verifyNoInteractionLambda and mocks.";

                doThrow(new Exception()).when(verifyNoInteractionLambda).run(mock2);

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocksWithVerifyNoInteractions.verifyNoInteractionsAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(1)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }
        }

        @Nested
        class Error {

            @Test
            void calledWhenNoInteractionsIsNotEnabled_ThenThrowRuntimeException() throws Exception {
                String expectedMessage = "Must enableVerifyNoInteractions before calling verifyNoInteractionsAfter.";

                RuntimeException actualException = assertThrows(
                        RuntimeException.class,
                        () -> mofThreeMocks.verifyNoInteractionsAfter(mock1)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verifyNoInteractionLambda, times(0)).run(mock1);
                verify(verifyNoInteractionLambda, times(0)).run(mock2);
                verify(verifyNoInteractionLambda, times(0)).run(mock3);
            }
        }
    }
}
