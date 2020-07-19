package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenBeforeFirstTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new WhenBeforeFirst().in(mockCoach);
        verify(mockCoach).whenBeforeFirst();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new WhenBeforeFirst();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenBeforeFirstTest.forgotIn_ThenThrowRuntimeException(WhenBeforeFirstTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenBeforeFirst::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new WhenBeforeFirst();
        assertThrows(
                RuntimeException.class,
                WhenBeforeFirst::new
        );
        new WhenBeforeFirst();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}