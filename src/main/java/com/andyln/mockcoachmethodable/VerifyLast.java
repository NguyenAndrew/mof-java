package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyLast implements Methodable {

    public static VerifyLast verifyLast() {
        MethodableState.inProgress();
        return new VerifyLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyLast();
    }

}
