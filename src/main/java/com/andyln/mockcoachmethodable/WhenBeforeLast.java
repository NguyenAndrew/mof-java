package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBeforeLast implements Methodable {

    public static WhenBeforeLast whenBeforeLast() {
        return new WhenBeforeLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.whenBeforeLast();
    }
}
