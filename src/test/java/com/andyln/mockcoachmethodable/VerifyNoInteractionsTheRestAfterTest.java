package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyNoInteractionsTheRestAfterTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        new VerifyNoInteractionsTheRestAfter(mock).in(mockCoach);
        verify(mockCoach).verifyNoInteractionsTheRestAfter(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyNoInteractionsTheRestAfter(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyNoInteractionsTheRestAfterTest.forgotIn_ThenThrowRuntimeException(VerifyNoInteractionsTheRestAfterTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> new VerifyNoInteractionsTheRestAfter(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyNoInteractionsTheRestAfter(mock);
        assertThrows(
                RuntimeException.class,
                () -> new VerifyNoInteractionsTheRestAfter(mock)
        );
        new VerifyNoInteractionsTheRestAfter(mock);
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}