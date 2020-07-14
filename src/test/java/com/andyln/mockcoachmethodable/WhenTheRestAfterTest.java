package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.WhenTheRestAfter.whenTheRestAfter;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class WhenTheRestAfterTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Test
    public void success() {
        whenTheRestAfter(mock).in(mockCoach);
        verify(mockCoach).whenTheRestAfter(mock);
    }
}