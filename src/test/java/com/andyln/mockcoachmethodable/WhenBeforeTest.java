package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenBeforeTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        new WhenBefore(mock).in(mockCoach);
        verify(mockCoach).whenBefore(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new WhenBefore(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenBeforeTest.forgotIn_ThenThrowRuntimeException(WhenBeforeTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> new WhenBefore(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new WhenBefore(mock);
        assertThrows(
                RuntimeException.class,
                () -> new WhenBefore(mock)
        );
        new WhenBefore(mock);
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}