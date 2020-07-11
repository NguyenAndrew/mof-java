package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyBeforeLast.verifyBeforeLast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyBeforeLastTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyBeforeLast().in(mockCoach);
        verify(mockCoach).verifyBeforeLast();
    }
}