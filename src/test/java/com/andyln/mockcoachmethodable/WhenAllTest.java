package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenAllTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new WhenAll().in(mockCoach);
        verify(mockCoach).whenAll();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new WhenAll();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenAllTest.forgotIn_ThenThrowRuntimeException(WhenAllTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenAll::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new WhenAll();
        assertThrows(
                RuntimeException.class,
                WhenAll::new
        );
        new WhenAll();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}