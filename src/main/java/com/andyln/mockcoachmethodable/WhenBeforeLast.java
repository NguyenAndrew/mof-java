package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBeforeLast implements Methodable {

    public static WhenBeforeLast whenBeforeLast() {
        MethodableState.inProgress();
        return new WhenBeforeLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenBeforeLast();
    }

}
