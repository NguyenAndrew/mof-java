package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenTheRest implements Methodable {

    WhenTheRest() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenTheRest();
    }

}