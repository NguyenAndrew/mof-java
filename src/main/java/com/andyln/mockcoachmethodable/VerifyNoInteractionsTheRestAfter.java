package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyNoInteractionsTheRestAfter implements Methodable {

    Object mock;

    VerifyNoInteractionsTheRestAfter(Object mock) {
        this.mock = mock;
    }

    public static VerifyNoInteractionsTheRestAfter verifyNoInteractionsTheRestAfter(Object mock) {
        MethodableState.inProgress();
        return new VerifyNoInteractionsTheRestAfter(mock);
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyNoInteractionsTheRestAfter(mock);
    }

}
