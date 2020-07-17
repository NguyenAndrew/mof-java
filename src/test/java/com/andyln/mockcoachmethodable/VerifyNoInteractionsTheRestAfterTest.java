package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyNoInteractionsTheRestAfter.verifyNoInteractionsTheRestAfter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyNoInteractionsTheRestAfterTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        verifyNoInteractionsTheRestAfter(mock).in(mockCoach);
        verify(mockCoach).verifyNoInteractionsTheRestAfter(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        verifyNoInteractionsTheRestAfter(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyNoInteractionsTheRestAfterTest.forgotIn_ThenThrowRuntimeException(VerifyNoInteractionsTheRestAfterTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> verifyNoInteractionsTheRestAfter(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        verifyNoInteractionsTheRestAfter(mock);
        assertThrows(
                RuntimeException.class,
                () -> verifyNoInteractionsTheRestAfter(mock)
        );
        verifyNoInteractionsTheRestAfter(mock);
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}