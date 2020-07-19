package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyThroughFirst implements Methodable {

    VerifyThroughFirst() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyThroughFirst();
    }

}
