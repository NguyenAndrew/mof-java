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
}
