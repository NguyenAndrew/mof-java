package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBefore implements Methodable {

    Object mock;

    WhenBefore(Object mock) {
        this.mock = mock;
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenBefore(mock);
    }

}
