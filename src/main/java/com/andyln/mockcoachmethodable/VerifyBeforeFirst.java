package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyBeforeFirst implements Methodable {

    public static VerifyBeforeFirst verifyBeforeFirst() {
        MethodableState.inProgress();
        return new VerifyBeforeFirst();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyBeforeFirst();
    }

}
