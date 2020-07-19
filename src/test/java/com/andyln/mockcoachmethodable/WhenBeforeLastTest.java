package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenBeforeLastTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new WhenBeforeLast().in(mockCoach);
        verify(mockCoach).whenBeforeLast();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new WhenBeforeLast();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenBeforeLastTest.forgotIn_ThenThrowRuntimeException(WhenBeforeLastTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenBeforeLast::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new WhenBeforeLast();
        assertThrows(
                RuntimeException.class,
                WhenBeforeLast::new
        );
        new WhenBeforeLast();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}