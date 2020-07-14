package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenTheRestAfter implements Methodable {

    Object mock;

    WhenTheRestAfter(Object mock) {
        this.mock = mock;
    }

    public static WhenTheRestAfter whenTheRestAfter(Object mock) {
        return new WhenTheRestAfter(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.whenTheRestAfter(mock);
    }
}