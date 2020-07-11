package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBeforeFirst implements Methodable {

    public static WhenBeforeFirst whenBeforeFirst() {
        return new WhenBeforeFirst();
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.whenBeforeFirst();
    }
}
