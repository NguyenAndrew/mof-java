package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBeforeFirst implements Methodable {

    VerifyBeforeFirst() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyBeforeFirst();
    }

}
