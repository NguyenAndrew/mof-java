package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyThroughLast implements Methodable {

    VerifyThroughLast() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyThroughLast();
    }

}
