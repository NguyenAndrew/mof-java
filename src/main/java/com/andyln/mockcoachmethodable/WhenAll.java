package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenAll implements Methodable {

    public static WhenAll whenAll() {
        MethodableState.inProgress();
        return new WhenAll();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.whenAll();
    }
}
