package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyTheRestAfter implements Methodable {

    Object mock;

    VerifyTheRestAfter(Object mock) {
        this.mock = mock;
    }

    public static VerifyTheRestAfter verifyTheRestAfter(Object mock) {
        MethodableState.inProgress();
        return new VerifyTheRestAfter(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyTheRestAfter(mock);
    }

}
