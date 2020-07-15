package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBeforeLast implements Methodable {

    public static VerifyBeforeLast verifyBeforeLast() {
        MethodableState.inProgress();
        return new VerifyBeforeLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyBeforeLast();
    }

}
