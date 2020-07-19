package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyBeforeLastTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        new VerifyBeforeLast().in(mockCoach);
        verify(mockCoach).verifyBeforeLast();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new VerifyBeforeLast();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyBeforeLastTest.forgotIn_ThenThrowRuntimeException(VerifyBeforeLastTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                VerifyBeforeLast::new
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new VerifyBeforeLast();
        assertThrows(
                RuntimeException.class,
                VerifyBeforeLast::new
        );
        new VerifyBeforeLast();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}