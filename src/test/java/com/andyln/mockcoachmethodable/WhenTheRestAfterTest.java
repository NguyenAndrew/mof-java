package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenTheRestAfterTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        new WhenTheRestAfter(mock).in(mockCoach);
        verify(mockCoach).whenTheRestAfter(mock);
    }

    @Test
    public void forgotIn_ThenThrowRuntimeException() {
        RuntimeException placeholderException = new RuntimeException();
        new WhenTheRestAfter(mock);
        String expectedMessage = String.format(
                "Missing .in(MockCoach) at com.andyln.mockcoachmethodable.WhenTheRestAfterTest.forgotIn_ThenThrowRuntimeException(WhenTheRestAfterTest.java:%d)",
                placeholderException.getStackTrace()[0].getLineNumber() + 1
        );

        RuntimeException actualException = assertThrows(
                RuntimeException.class,
                () -> new WhenTheRestAfter(mock)
        );

        assertEquals(expectedMessage, actualException.getMessage());
    }

    @Test
    public void clearWorks_ThenSuccess() {
        new WhenTheRestAfter(mock);
        assertThrows(
                RuntimeException.class,
                () -> new WhenTheRestAfter(mock)
        );
        new WhenTheRestAfter(mock);
    }

    @AfterAll
    public static void cleanUp() {
        MethodableState.clear();
    }

}