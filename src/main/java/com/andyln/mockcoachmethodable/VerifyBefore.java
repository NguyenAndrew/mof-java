package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBefore implements Methodable {

    Object mock;

    VerifyBefore(Object mock) {
        this.mock = mock;
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyBefore(mock);
    }

}
