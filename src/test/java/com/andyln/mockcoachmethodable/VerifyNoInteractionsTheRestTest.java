package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyNoInteractionsTheRest.verifyNoInteractionsTheRest;
import static com.andyln.mockcoachmethodable.VerifyTheRest.verifyTheRest;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyNoInteractionsTheRestTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyNoInteractionsTheRest().in(mockCoach);
        verify(mockCoach).verifyNoInteractionsTheRest();
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        verifyNoInteractionsTheRest();
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.VerifyNoInteractionsTheRestTest.forgotIn_ThenThrowRuntimeException(VerifyNoInteractionsTheRestTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                VerifyNoInteractionsTheRest::verifyNoInteractionsTheRest
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        verifyNoInteractionsTheRest();
        assertThrows(
                RuntimeException.class,
                VerifyNoInteractionsTheRest::verifyNoInteractionsTheRest
        );
        verifyTheRest();
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}