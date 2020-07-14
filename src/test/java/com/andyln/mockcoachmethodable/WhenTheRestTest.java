package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenTheRest.whenTheRest;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenTheRestTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenTheRest().in(mockCoach);
        verify(mockCoach).whenTheRest();
    }
}