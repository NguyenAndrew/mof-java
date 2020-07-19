package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyTheRest implements Methodable {

    VerifyTheRest() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyTheRest();
    }

}
