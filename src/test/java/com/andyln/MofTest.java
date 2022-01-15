package com.andyln;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class MofTest {

    private final Object mock1 = mock(Object.class);
    private final Object mock2 = mock(Object.class);
    private final Object mock3 = mock(Object.class);
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
        void withTwoMocksInCircleChain_success() {
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
        void whenThreeMocksAreInCircleChain_ThenSuccess() {
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
        void whenMocksInACyclicGraphThatIsNotACircleChain_ThenThrowIllegalArgumentException() {
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
}
