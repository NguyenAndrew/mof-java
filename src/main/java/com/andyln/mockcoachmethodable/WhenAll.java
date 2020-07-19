package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenAll implements Methodable {

    WhenAll() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenAll();
    }

}
