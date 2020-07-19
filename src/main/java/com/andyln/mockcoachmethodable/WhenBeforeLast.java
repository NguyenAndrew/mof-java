package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBeforeLast implements Methodable {

    WhenBeforeLast() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenBeforeLast();
    }

}
