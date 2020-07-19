package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenTheRestTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new WhenTheRest().in(mockCoach);
        verify(mockCoach).whenTheRest();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new WhenTheRest();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenTheRestTest.forgotIn_ThenThrowRuntimeException(WhenTheRestTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenTheRest::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new WhenTheRest();
        assertThrows(
                RuntimeException.class,
                WhenTheRest::new
        );
        new WhenTheRest();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}