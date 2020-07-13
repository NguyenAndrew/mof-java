package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyLast.verifyLast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyLastTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyLast().in(mockCoach);
        verify(mockCoach).verifyLast();
    }
}