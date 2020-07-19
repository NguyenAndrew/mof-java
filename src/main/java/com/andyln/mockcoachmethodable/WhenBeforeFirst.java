package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenBeforeFirst implements Methodable {

    WhenBeforeFirst() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenBeforeFirst();
    }

}
