package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBefore implements Methodable {

    Object mock;

    VerifyBefore(Object mock) {
        this.mock = mock;
    }

    public static VerifyBefore verifyBefore(Object mock) {
        return new VerifyBefore(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.verifyBefore(mock);
    }
}
