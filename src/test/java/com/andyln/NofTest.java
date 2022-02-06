package com.andyln;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.andyln.Nof.*;
import static com.andyln.Nof.LAST;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

public class NofTest {
    
    //region Unit Test Variables {...}
    enum MockEnums {
        FIRST_MOCK_ENUM,
        SECOND_MOCK_ENUM,
        THIRD_MOCK_ENUM,
    }

    enum NotInMockEnums {
        MOCK_ENUM
    }
    
    private final WhenLambda when1 = mock(WhenLambda.class);
    private final WhenLambda when2 = mock(WhenLambda.class);
    private final WhenLambda when3 = mock(WhenLambda.class);

    private final VerifyLambda verify1 = mock(VerifyLambda.class);
    private final VerifyLambda verify2 = mock(VerifyLambda.class);
    private final VerifyLambda verify3 = mock(VerifyLambda.class);

    private final Nof nofSingleMock = new Nof.Builder()
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when1,
                    verify1
            )
            .build();
    private final Nof nofTwoMocks = new Nof.Builder()
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when1,
                    verify1
            )
            .add(
                    MockEnums.SECOND_MOCK_ENUM,
                    when2,
                    verify2
            )
            .build();
    private final Nof nofThreeMocks = new Nof.Builder()
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when1,
                    verify1
            )
            .add(
                    MockEnums.SECOND_MOCK_ENUM,
                    when2,
                    verify2
            )
            .add(
                    MockEnums.THIRD_MOCK_ENUM,
                    when3,
                    verify3
            )
            .build();

    private final Nof nofTwoMocksInASimpleClosedCurve = new Nof.Builder()
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when1,
                    verify1
            )
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when2,
                    verify2
            )
            .build();
    private final Nof nofThreeMocksInASimpleClosedCurve = new Nof.Builder()
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when1,
                    verify1
            )
            .add(
                    MockEnums.SECOND_MOCK_ENUM,
                    when2,
                    verify2
            )
            .add(
                    MockEnums.FIRST_MOCK_ENUM,
                    when3,
                    verify3
            )
            .build();
    //endregion

    @Nested
    class Builder {

        @Test
        void staticMethod_success() {
            Nof.builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    )
                    .build();
        }

        @Test
        void constructor_success() {
            new Nof.Builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    )
                    .build();
        }

        @Test
        void copy_success() {
            Nof.Builder originalBuilder = new Nof.Builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    );

            Nof.Builder copiedBuilder = originalBuilder.copy();

            originalBuilder.copy();
            copiedBuilder.copy();
        }
        
        @Test
        void withTwoMocks_success() {
            new Nof.Builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    )
                    .add(
                            MockEnums.SECOND_MOCK_ENUM,
                            when2,
                            verify2
                    )
                    .build();
        }

        @Test
        void withThreeMocks_success() {
            new Nof.Builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    )
                    .add(
                            MockEnums.SECOND_MOCK_ENUM,
                            when2,
                            verify2
                    )
                    .add(
                            MockEnums.THIRD_MOCK_ENUM,
                            when3,
                            verify3
                    )
                    .build();
        }

        @Test
        void whenTwoMocksAreInASimpleClosedCurve_success() {
            new Nof.Builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    )
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when2,
                            verify2
                    )
                    .build();
        }

        @Test
        void whenThreeMocksAreInASimpleClosedCurve_ThenSuccess() {
            new Nof.Builder()
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
                            when1,
                            verify1
                    )
                    .add(
                            MockEnums.SECOND_MOCK_ENUM,
                            when2,
                            verify2
                    )
                    .add(
                            MockEnums.FIRST_MOCK_ENUM,
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
                    () -> new Nof.Builder()
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
                    () -> new Nof.Builder()
                            .add(
                                    MockEnums.FIRST_MOCK_ENUM,
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
                    () -> new Nof.Builder()
                            .add(
                                    MockEnums.FIRST_MOCK_ENUM,
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
                    () -> new Nof.Builder().build()
            );

            assertEquals(expectedMessage, actualException.getMessage());
        }

        @Test
        void whenMocksInANonSimpleCurve_ThenThrowIllegalArgumentException() {
            String expectedMessage = "m3 cannot be the same as a previous mock in mocks!";

            IllegalArgumentException actualException = assertThrows(
                    IllegalArgumentException.class,
                    () -> new Nof.Builder()
                            .add(
                                    MockEnums.FIRST_MOCK_ENUM,
                                    when1,
                                    verify1
                            )
                            .add(
                                    MockEnums.SECOND_MOCK_ENUM,
                                    when2,
                                    verify2
                            )
                            .add(
                                    MockEnums.SECOND_MOCK_ENUM,
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
                nofSingleMock.when(ALL);

                verify(when1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.when(ALL);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.when(ALL);

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
                        () -> nofThreeMocks.when(ALL)
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
                    nofSingleMock.when(ALL);
                    nofSingleMock.when(REMAINING);

                    verify(when1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    nofTwoMocks.when(ALL);
                    nofTwoMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    nofThreeMocks.when(ALL);
                    nofThreeMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                    verify(when3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofTwoMocksInASimpleClosedCurve.when(ALL);
                    nofTwoMocksInASimpleClosedCurve.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofThreeMocksInASimpleClosedCurve.when(ALL);
                    nofThreeMocksInASimpleClosedCurve.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                    verify(when3, times(1)).run();
                }
            }

            @Nested
            class WhenRemaining {

                @Test
                void success() throws Exception {
                    nofSingleMock.when(REMAINING);

                    verify(when1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    nofTwoMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    nofThreeMocks.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                    verify(when3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofTwoMocksInASimpleClosedCurve.when(REMAINING);

                    verify(when1, times(1)).run();
                    verify(when2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofThreeMocksInASimpleClosedCurve.when(REMAINING);

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
                            () -> nofThreeMocks.when(REMAINING)
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
                        nofSingleMock.whenBefore(FIRST);
                        nofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.whenBefore(FIRST);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.whenBefore(FIRST);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.whenBefore(FIRST);
                        nofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.whenBefore(FIRST);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.whenBefore(LAST);
                        nofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.whenBefore(LAST);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.whenBefore(LAST);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.whenBefore(LAST);
                        nofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.whenBefore(LAST);
                        nofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.whenBefore(MockEnums.FIRST_MOCK_ENUM);
                        nofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        nofTwoMocks.whenBefore(MockEnums.FIRST_MOCK_ENUM);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        nofTwoMocks.whenBefore(MockEnums.SECOND_MOCK_ENUM);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        nofThreeMocks.whenBefore(MockEnums.FIRST_MOCK_ENUM);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        nofThreeMocks.whenBefore(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        nofThreeMocks.whenBefore(MockEnums.THIRD_MOCK_ENUM);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(1)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.whenBefore(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocksInASimpleClosedCurve.when(REMAINING);

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
                        nofSingleMock.whenAfter(FIRST);
                        nofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.whenAfter(FIRST);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.whenAfter(FIRST);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.whenAfter(FIRST);
                        nofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.whenAfter(FIRST);
                        nofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.whenAfter(LAST);
                        nofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.whenAfter(LAST);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.whenAfter(LAST);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.whenAfter(LAST);
                        nofTwoMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.whenAfter(LAST);
                        nofThreeMocksInASimpleClosedCurve.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.whenAfter(MockEnums.FIRST_MOCK_ENUM);
                        nofSingleMock.when(REMAINING);

                        verify(when1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        nofTwoMocks.whenAfter(MockEnums.FIRST_MOCK_ENUM);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        nofTwoMocks.whenAfter(MockEnums.SECOND_MOCK_ENUM);
                        nofTwoMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        nofThreeMocks.whenAfter(MockEnums.FIRST_MOCK_ENUM);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(1)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        nofThreeMocks.whenAfter(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        nofThreeMocks.whenAfter(MockEnums.THIRD_MOCK_ENUM);
                        nofThreeMocks.when(REMAINING);

                        verify(when1, times(0)).run();
                        verify(when2, times(0)).run();
                        verify(when3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.whenAfter(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocksInASimpleClosedCurve.when(REMAINING);

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
                        () -> nofThreeMocks.when(null)
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
                nofSingleMock.whenBefore(FIRST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.whenBefore(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                nofSingleMock.whenBefore(LAST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.whenBefore(LAST);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.whenBefore(LAST);

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
                        () -> nofThreeMocks.whenBefore(LAST)
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
                nofSingleMock.whenBefore(MockEnums.FIRST_MOCK_ENUM);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                nofTwoMocks.whenBefore(MockEnums.FIRST_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                nofTwoMocks.whenBefore(MockEnums.SECOND_MOCK_ENUM);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                nofThreeMocks.whenBefore(MockEnums.FIRST_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                nofThreeMocks.whenBefore(MockEnums.SECOND_MOCK_ENUM);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                nofThreeMocks.whenBefore(MockEnums.THIRD_MOCK_ENUM);

                verify(when1, times(1)).run();
                verify(when2, times(1)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.whenBefore(MockEnums.SECOND_MOCK_ENUM);

                verify(when1, times(1)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenBefore(FIRST) or whenBefore(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofTwoMocksInASimpleClosedCurve.whenBefore(MockEnums.FIRST_MOCK_ENUM)
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
                        () -> nofThreeMocksInASimpleClosedCurve.whenBefore(MockEnums.FIRST_MOCK_ENUM)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call whenBefore(Object mock) for mock not in mocks!";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofThreeMocks.whenBefore(NotInMockEnums.MOCK_ENUM)
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
                        () -> nofThreeMocks.whenBefore(MockEnums.THIRD_MOCK_ENUM)
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
                nofSingleMock.whenAfter(FIRST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.whenAfter(FIRST);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.whenAfter(FIRST);

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
                        () -> nofThreeMocks.whenAfter(FIRST)
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
                nofSingleMock.whenAfter(LAST);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.whenAfter(LAST);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                nofSingleMock.whenAfter(MockEnums.FIRST_MOCK_ENUM);

                verify(when1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                nofTwoMocks.whenAfter(MockEnums.FIRST_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                nofTwoMocks.whenAfter(MockEnums.SECOND_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                nofThreeMocks.whenAfter(MockEnums.FIRST_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(1)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                nofThreeMocks.whenAfter(MockEnums.SECOND_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                nofThreeMocks.whenAfter(MockEnums.THIRD_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.whenAfter(MockEnums.SECOND_MOCK_ENUM);

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call whenAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenAfter(FIRST) or whenAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofTwoMocksInASimpleClosedCurve.whenAfter(MockEnums.FIRST_MOCK_ENUM)
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
                        () -> nofThreeMocksInASimpleClosedCurve.whenAfter(MockEnums.FIRST_MOCK_ENUM)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call whenAfter(Object mock) for mock not in mocks!";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofThreeMocks.whenAfter(NotInMockEnums.MOCK_ENUM)
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
                        () -> nofThreeMocks.whenAfter(MockEnums.FIRST_MOCK_ENUM)
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
                nofSingleMock.verify(ALL);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verify(ALL);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verify(ALL);

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
                        () -> nofThreeMocks.verify(ALL)
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
                    nofSingleMock.verify(ALL);
                    nofSingleMock.verify(REMAINING);

                    verify(verify1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    nofTwoMocks.verify(ALL);
                    nofTwoMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    nofThreeMocks.verify(ALL);
                    nofThreeMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofTwoMocksInASimpleClosedCurve.verify(ALL);
                    nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofThreeMocksInASimpleClosedCurve.verify(ALL);
                    nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }
            }

            @Nested
            class VerifyRemaining {

                @Test
                void success() throws Exception {
                    nofSingleMock.verify(REMAINING);

                    verify(verify1, times(1)).run();
                }

                @Test
                void twoMocks_success() throws Exception {
                    nofTwoMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocks_success() throws Exception {
                    nofThreeMocks.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                    verify(verify3, times(1)).run();
                }

                @Test
                void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                    verify(verify1, times(1)).run();
                    verify(verify2, times(1)).run();
                }

                @Test
                void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                    nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

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
                            () -> nofThreeMocks.verify(REMAINING)
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
                        nofSingleMock.verifyThrough(FIRST);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(1)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.verifyThrough(FIRST);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.verifyThrough(FIRST);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.verifyThrough(FIRST);
                        nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyThrough(FIRST);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.verifyThrough(LAST);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(1)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.verifyThrough(LAST);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.verifyThrough(LAST);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.verifyThrough(LAST);
                        nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyThrough(LAST);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.verifyThrough(MockEnums.FIRST_MOCK_ENUM);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(1)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        nofTwoMocks.verifyThrough(MockEnums.FIRST_MOCK_ENUM);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        nofTwoMocks.verifyThrough(MockEnums.SECOND_MOCK_ENUM);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        nofThreeMocks.verifyThrough(MockEnums.FIRST_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        nofThreeMocks.verifyThrough(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        nofThreeMocks.verifyThrough(MockEnums.THIRD_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyThrough(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

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
                        nofSingleMock.verifyBefore(FIRST);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.verifyBefore(FIRST);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.verifyBefore(FIRST);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.verifyBefore(FIRST);
                        nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyBefore(FIRST);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.verifyBefore(LAST);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.verifyBefore(LAST);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.verifyBefore(LAST);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.verifyBefore(LAST);
                        nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyBefore(LAST);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.verifyBefore(MockEnums.FIRST_MOCK_ENUM);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        nofTwoMocks.verifyBefore(MockEnums.FIRST_MOCK_ENUM);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        nofTwoMocks.verifyBefore(MockEnums.SECOND_MOCK_ENUM);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        nofThreeMocks.verifyBefore(MockEnums.FIRST_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        nofThreeMocks.verifyBefore(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        nofThreeMocks.verifyBefore(MockEnums.THIRD_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(1)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyBefore(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

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
                        nofSingleMock.verifyAfter(FIRST);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.verifyAfter(FIRST);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.verifyAfter(FIRST);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.verifyAfter(FIRST);
                        nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyAfter(FIRST);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }
                }

                @Nested
                class Last {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.verifyAfter(LAST);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_success() throws Exception {
                        nofTwoMocks.verifyAfter(LAST);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_success() throws Exception {
                        nofThreeMocks.verifyAfter(LAST);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofTwoMocksInASimpleClosedCurve.verifyAfter(LAST);
                        nofTwoMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyAfter(LAST);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                    }
                }

                @Nested
                class Mock {

                    @Test
                    void success() throws Exception {
                        nofSingleMock.verifyAfter(MockEnums.FIRST_MOCK_ENUM);
                        nofSingleMock.verify(REMAINING);

                        verify(verify1, times(0)).run();
                    }

                    @Test
                    void twoMocks_onFirstMock_success() throws Exception {
                        nofTwoMocks.verifyAfter(MockEnums.FIRST_MOCK_ENUM);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                    }

                    @Test
                    void twoMocks_onSecondMock_success() throws Exception {
                        nofTwoMocks.verifyAfter(MockEnums.SECOND_MOCK_ENUM);
                        nofTwoMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                    }

                    @Test
                    void threeMocks_onFirstMock_success() throws Exception {
                        nofThreeMocks.verifyAfter(MockEnums.FIRST_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(1)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onSecondMock_success() throws Exception {
                        nofThreeMocks.verifyAfter(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(1)).run();
                    }

                    @Test
                    void threeMocks_onThirdMock_success() throws Exception {
                        nofThreeMocks.verifyAfter(MockEnums.THIRD_MOCK_ENUM);
                        nofThreeMocks.verify(REMAINING);

                        verify(verify1, times(0)).run();
                        verify(verify2, times(0)).run();
                        verify(verify3, times(0)).run();
                    }

                    @Test
                    void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                        nofThreeMocksInASimpleClosedCurve.verifyAfter(MockEnums.SECOND_MOCK_ENUM);
                        nofThreeMocksInASimpleClosedCurve.verify(REMAINING);

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
                        () -> nofThreeMocks.verify(null)
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
                nofSingleMock.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verifyThrough(FIRST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyThrough(FIRST);

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
                        () -> nofThreeMocks.verifyThrough(FIRST)
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
                nofSingleMock.verifyThrough(LAST);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verifyThrough(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyThrough(LAST);

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
                        () -> nofThreeMocks.verifyThrough(LAST)
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
                nofSingleMock.verifyThrough(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(1)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                nofTwoMocks.verifyThrough(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                nofTwoMocks.verifyThrough(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                nofThreeMocks.verifyThrough(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                nofThreeMocks.verifyThrough(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                nofThreeMocks.verifyThrough(MockEnums.THIRD_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyThrough(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyThrough(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyThrough(FIRST) or verifyThrough(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofTwoMocksInASimpleClosedCurve.verifyThrough(MockEnums.FIRST_MOCK_ENUM)
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
                        () -> nofThreeMocksInASimpleClosedCurve.verifyThrough(MockEnums.FIRST_MOCK_ENUM)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(when1, times(0)).run();
                verify(when2, times(0)).run();
                verify(when3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyThrough(Object mock) for mock not in mocks!";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofThreeMocks.verifyThrough(NotInMockEnums.MOCK_ENUM)
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
                        () -> nofThreeMocks.verifyThrough(MockEnums.THIRD_MOCK_ENUM)
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
                nofSingleMock.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyBefore(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Last {

            @Test
            void success() throws Exception {
                nofSingleMock.verifyBefore(LAST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verifyBefore(LAST);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyBefore(LAST);

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
                        () -> nofThreeMocks.verifyBefore(LAST)
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
                nofSingleMock.verifyBefore(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                nofTwoMocks.verifyBefore(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                nofTwoMocks.verifyBefore(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                nofThreeMocks.verifyBefore(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                nofThreeMocks.verifyBefore(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                nofThreeMocks.verifyBefore(MockEnums.THIRD_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyBefore(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(1)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyBefore(FIRST) or verifyBefore(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofTwoMocksInASimpleClosedCurve.verifyBefore(MockEnums.FIRST_MOCK_ENUM)
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
                        () -> nofThreeMocksInASimpleClosedCurve.verifyBefore(MockEnums.FIRST_MOCK_ENUM)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyBefore(Object mock) for mock not in mocks!";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofThreeMocks.verifyBefore(NotInMockEnums.MOCK_ENUM)
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
                        () -> nofThreeMocks.verifyBefore(MockEnums.THIRD_MOCK_ENUM)
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
                nofSingleMock.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verifyAfter(FIRST);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyAfter(FIRST);

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
                        () -> nofThreeMocks.verifyAfter(FIRST)
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
                nofSingleMock.verifyAfter(LAST);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_success() throws Exception {
                nofTwoMocks.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_success() throws Exception {
                nofThreeMocks.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_success() throws Exception {
                nofTwoMocksInASimpleClosedCurve.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyAfter(LAST);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }
        }

        @Nested
        class Mock {

            @Test
            void success() throws Exception {
                nofSingleMock.verifyAfter(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(0)).run();
            }

            @Test
            void twoMocks_onFirstMock_success() throws Exception {
                nofTwoMocks.verifyAfter(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
            }

            @Test
            void twoMocks_onSecondMock_success() throws Exception {
                nofTwoMocks.verifyAfter(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
            }

            @Test
            void threeMocks_onFirstMock_success() throws Exception {
                nofThreeMocks.verifyAfter(MockEnums.FIRST_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void threeMocks_onSecondMock_success() throws Exception {
                nofThreeMocks.verifyAfter(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void threeMocks_onThirdMock_success() throws Exception {
                nofThreeMocks.verifyAfter(MockEnums.THIRD_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void threeMocksAreInASimpleClosedCurve_success() throws Exception {
                nofThreeMocksInASimpleClosedCurve.verifyAfter(MockEnums.SECOND_MOCK_ENUM);

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(1)).run();
            }

            @Test
            void twoMocksAreInASimpleClosedCurve_onFirstLastMock_thenThrowRuntimeException() throws Exception {
                String expectedMessage = "Cannot call verifyAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyAfter(FIRST) or verifyAfter(LAST).";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofTwoMocksInASimpleClosedCurve.verifyAfter(MockEnums.FIRST_MOCK_ENUM)
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
                        () -> nofThreeMocksInASimpleClosedCurve.verifyAfter(MockEnums.FIRST_MOCK_ENUM)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(0)).run();
                verify(verify3, times(0)).run();
            }

            @Test
            void calledWithMockNotInMocks_ThenThrowIllegalIllegalArgumentException() throws Exception {
                String expectedMessage = "Cannot call verifyAfter(Object mock) for mock not in mocks!";

                IllegalArgumentException actualException = assertThrows(
                        IllegalArgumentException.class,
                        () -> nofThreeMocks.verifyAfter(NotInMockEnums.MOCK_ENUM)
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
                        () -> nofThreeMocks.verifyAfter(MockEnums.FIRST_MOCK_ENUM)
                );

                assertEquals(expectedMessage, actualException.getMessage());

                verify(verify1, times(0)).run();
                verify(verify2, times(1)).run();
                verify(verify3, times(0)).run();
            }
        }
    }
}
