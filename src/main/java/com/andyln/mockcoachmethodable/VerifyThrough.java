package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyThrough implements Methodable {

    Object mock;

    VerifyThrough(Object mock) {
        this.mock = mock;
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyThrough(mock);
    }

}
