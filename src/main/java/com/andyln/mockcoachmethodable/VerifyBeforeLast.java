package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBeforeLast implements Methodable {

    public static VerifyBeforeLast verifyBeforeLast() {
        return new VerifyBeforeLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.verifyBeforeLast();
    }
}
