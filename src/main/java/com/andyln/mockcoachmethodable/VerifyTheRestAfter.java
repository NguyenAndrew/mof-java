package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyTheRestAfter implements Methodable {

    Object mock;

    VerifyTheRestAfter(Object mock) {
        this.mock = mock;
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyTheRestAfter(mock);
    }

}
