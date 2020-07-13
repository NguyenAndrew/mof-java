package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyThrough implements Methodable {

    Object mock;

    VerifyThrough(Object mock) {
        this.mock = mock;
    }

    public static VerifyThrough verifyThrough(Object mock) {
        return new VerifyThrough(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.verifyThrough(mock);
    }
}
