package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyThrough.verifyUpTo;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyThroughTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        verifyUpTo(mock).in(mockCoach);
        verify(mockCoach).verifyThrough(mock);
    }
}