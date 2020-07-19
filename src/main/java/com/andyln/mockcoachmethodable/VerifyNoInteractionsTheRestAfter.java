package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyNoInteractionsTheRestAfter implements Methodable {

    Object mock;

    VerifyNoInteractionsTheRestAfter(Object mock) {
        this.mock = mock;
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyNoInteractionsTheRestAfter(mock);
    }

}
