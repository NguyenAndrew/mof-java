package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyBefore.verifyBefore;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyBeforeTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        verifyBefore(mock).in(mockCoach);
        verify(mockCoach).verifyBefore(mock);
    }
}