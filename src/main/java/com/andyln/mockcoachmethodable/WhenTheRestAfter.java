package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenTheRestAfter implements Methodable {

    Object mock;

    WhenTheRestAfter(Object mock) {
        this.mock = mock;
    }

    public static WhenTheRestAfter whenTheRestAfter(Object mock) {
        MethodableState.inProgress();
        return new WhenTheRestAfter(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenTheRestAfter(mock);
    }

}