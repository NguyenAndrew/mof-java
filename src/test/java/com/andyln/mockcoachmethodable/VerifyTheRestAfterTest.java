package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyNoInteractionsTheRestAfter.verifyNoInteractionsTheRestAfter;
import static com.andyln.mockcoachmethodable.VerifyTheRestAfter.verifyTheRestAfter;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyTheRestAfterTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        verifyTheRestAfter(mock).in(mockCoach);
        verify(mockCoach).verifyTheRestAfter(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        verifyTheRestAfter(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyTheRestAfterTest.forgotIn_ThenThrowRuntimeException(VerifyTheRestAfterTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> verifyTheRestAfter(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        verifyTheRestAfter(mock);
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