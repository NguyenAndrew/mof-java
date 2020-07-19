package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyThroughTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        new VerifyThrough(mock).in(mockCoach);
        verify(mockCoach).verifyThrough(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyThrough(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyThroughTest.forgotIn_ThenThrowRuntimeException(VerifyThroughTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> new VerifyThrough(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyThrough(mock);
        assertThrows(
                RuntimeException.class,
                () -> new VerifyThrough(mock)
        );
        new VerifyThrough(mock);
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}