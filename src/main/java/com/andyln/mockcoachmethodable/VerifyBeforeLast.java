package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBeforeLast implements Methodable {

    VerifyBeforeLast() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyBeforeLast();
    }

}
