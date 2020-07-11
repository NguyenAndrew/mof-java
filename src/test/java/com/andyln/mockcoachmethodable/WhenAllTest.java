package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenAll.whenAll;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenAllTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    @Test
    public void success() {
        whenAll().in(mockCoach);
        verify(mockCoach).whenAll();
    }
}