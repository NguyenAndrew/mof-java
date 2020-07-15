package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenAll.whenAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenAllTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenAll().in(mockCoach);
        verify(mockCoach).whenAll();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        whenAll();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenAllTest.forgotIn_ThenThrowRuntimeException(WhenAllTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenAll::whenAll
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        whenAll();
        assertThrows(
                RuntimeException.class,
                WhenAll::whenAll
        );
        whenAll();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}