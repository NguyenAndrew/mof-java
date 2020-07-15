package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyThroughFirst implements Methodable {

    public static VerifyThroughFirst verifyThroughFirst() {
        MethodableState.inProgress();
        return new VerifyThroughFirst();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyThroughFirst();
    }

}
