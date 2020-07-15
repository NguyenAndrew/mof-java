package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyBeforeFirst.verifyBeforeFirst;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyBeforeFirstTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyBeforeFirst().in(mockCoach);
        verify(mockCoach).verifyBeforeFirst();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        verifyBeforeFirst();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyBeforeFirstTest.forgotIn_ThenThrowRuntimeException(VerifyBeforeFirstTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                VerifyBeforeFirst::verifyBeforeFirst
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        verifyBeforeFirst();
        assertThrows(
                RuntimeException.class,
                VerifyBeforeFirst::verifyBeforeFirst
        );
        verifyBeforeFirst();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}