package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyAll.verifyAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyAllTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyAll().in(mockCoach);
        verify(mockCoach).verifyAll();
    }
}