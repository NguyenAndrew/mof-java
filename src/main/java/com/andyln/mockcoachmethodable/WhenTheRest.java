package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenTheRest implements Methodable {

    public static WhenTheRest whenTheRest() {
        MethodableState.inProgress();
        return new WhenTheRest();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenTheRest();
    }

}