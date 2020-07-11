package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBefore implements Methodable {
    Object mock;

    WhenBefore(Object mock) {
        this.mock = mock;
    }

    public static WhenBefore whenBefore(Object mock) {
        return new WhenBefore(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.whenBefore(mock);
    }
}
