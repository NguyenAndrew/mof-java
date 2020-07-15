package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenTheRest.whenTheRest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenTheRestTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenTheRest().in(mockCoach);
        verify(mockCoach).whenTheRest();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        whenTheRest();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenTheRestTest.forgotIn_ThenThrowRuntimeException(WhenTheRestTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                WhenTheRest::whenTheRest
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        whenTheRest();
        assertThrows(
                RuntimeException.class,
                WhenTheRest::whenTheRest
        );
        whenTheRest();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}