package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBeforeFirst implements Methodable {

    public static WhenBeforeFirst whenBeforeFirst() {
        MethodableState.inProgress();
        return new WhenBeforeFirst();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenBeforeFirst();
    }

}
