package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenBeforeFirst.whenBeforeFirst;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenBeforeFirstTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenBeforeFirst().in(mockCoach);
        verify(mockCoach).whenBeforeFirst();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        whenBeforeFirst();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenBeforeFirstTest.forgotIn_ThenThrowRuntimeException(WhenBeforeFirstTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenBeforeFirst::whenBeforeFirst
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        whenBeforeFirst();
        assertThrows(
                RuntimeException.class,
                WhenBeforeFirst::whenBeforeFirst
        );
        whenBeforeFirst();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}