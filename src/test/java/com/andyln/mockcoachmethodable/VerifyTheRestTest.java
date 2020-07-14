package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.VerifyTheRest.verifyTheRest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class VerifyTheRestTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        verifyTheRest().in(mockCoach);
        verify(mockCoach).verifyTheRest();
    }
}