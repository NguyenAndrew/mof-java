package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyFirst.verifyFirst;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyFirstTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyFirst().in(mockCoach);
        verify(mockCoach).verifyFirst();
    }
}