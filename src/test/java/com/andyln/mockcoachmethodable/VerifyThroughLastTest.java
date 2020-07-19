package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyThroughLastTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new VerifyThroughLast().in(mockCoach);
        verify(mockCoach).verifyThroughLast();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyThroughLast();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyThroughLastTest.forgotIn_ThenThrowRuntimeException(VerifyThroughLastTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                VerifyThroughLast::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyThroughLast();
        assertThrows(
                RuntimeException.class,
                VerifyThroughLast::new
        );
        new VerifyThroughLast();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}