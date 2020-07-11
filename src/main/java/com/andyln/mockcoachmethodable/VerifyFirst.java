package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyFirst implements Methodable {

    public static VerifyFirst verifyFirst() {
        return new VerifyFirst();
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.verifyFirst();
    }
}
