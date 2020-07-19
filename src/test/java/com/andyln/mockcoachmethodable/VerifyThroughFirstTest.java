package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyThroughFirstTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new VerifyThroughFirst().in(mockCoach);
        verify(mockCoach).verifyThroughFirst();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyThroughFirst();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyThroughFirstTest.forgotIn_ThenThrowRuntimeException(VerifyThroughFirstTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                VerifyThroughFirst::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyThroughFirst();
        assertThrows(
                RuntimeException.class,
                VerifyThroughFirst::new
        );
        new VerifyThroughFirst();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}