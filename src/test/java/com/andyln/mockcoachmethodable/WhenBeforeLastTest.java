package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import com.andyln.MockCoachLegacy;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenBeforeLast.whenBeforeLast;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenBeforeLastTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenBeforeLast().in(mockCoach);
        verify(mockCoach).whenBeforeLast();
    }
}