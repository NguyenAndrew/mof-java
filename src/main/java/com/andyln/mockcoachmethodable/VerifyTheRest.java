package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyTheRest implements Methodable {

    public static VerifyTheRest verifyTheRest() {
        MethodableState.inProgress();
        return new VerifyTheRest();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyTheRest();
    }
}
