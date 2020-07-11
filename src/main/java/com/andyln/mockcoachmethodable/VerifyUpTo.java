package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyUpTo implements Methodable {

    Object mock;

    VerifyUpTo(Object mock) {
        this.mock = mock;
    }

    public static VerifyUpTo verifyUpTo(Object mock) {
        return new VerifyUpTo(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.verifyUpTo(mock);
    }
}
