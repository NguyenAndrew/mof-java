package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyNoInteractionsTheRest implements Methodable {

    VerifyNoInteractionsTheRest() {
        MethodableState.inProgress();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyNoInteractionsTheRest();
    }

}
