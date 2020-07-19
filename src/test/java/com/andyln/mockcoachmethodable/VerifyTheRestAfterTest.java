package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyTheRestAfterTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        new VerifyTheRestAfter(mock).in(mockCoach);
        verify(mockCoach).verifyTheRestAfter(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyTheRestAfter(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyTheRestAfterTest.forgotIn_ThenThrowRuntimeException(VerifyTheRestAfterTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> new VerifyTheRestAfter(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyTheRestAfter(mock);
        assertThrows(
                RuntimeException.class,
                () -> new VerifyTheRestAfter(mock)
        );
        new VerifyTheRestAfter(mock);
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}