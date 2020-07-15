package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyThroughLast implements Methodable {

    public static VerifyThroughLast verifyThroughLast() {
        MethodableState.inProgress();
        return new VerifyThroughLast();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyThroughLast();
    }

}
