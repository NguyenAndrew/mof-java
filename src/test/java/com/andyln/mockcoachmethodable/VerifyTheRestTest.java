package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

class VerifyTheRestTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new VerifyTheRest().in(mockCoach);
        verify(mockCoach).verifyTheRest();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyTheRest();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyTheRestTest.forgotIn_ThenThrowRuntimeException(VerifyTheRestTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                VerifyTheRest::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyTheRest();
        assertThrows(
                RuntimeException.class,
                VerifyTheRest::new
        );
        new VerifyTheRest();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}