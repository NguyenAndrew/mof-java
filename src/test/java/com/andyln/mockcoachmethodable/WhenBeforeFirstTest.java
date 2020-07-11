package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenBeforeFirst.whenBeforeFirst;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenBeforeFirstTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenBeforeFirst().in(mockCoach);
        verify(mockCoach).whenBeforeFirst();
    }
}