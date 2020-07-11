package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyLast implements Methodable {

    public static VerifyLast verifyLast() {
        return new VerifyLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.verifyLast();
    }
}
