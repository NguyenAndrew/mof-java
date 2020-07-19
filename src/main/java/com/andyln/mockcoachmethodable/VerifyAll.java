package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyAll implements Methodable {

    VerifyAll() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyAll();
    }

}
