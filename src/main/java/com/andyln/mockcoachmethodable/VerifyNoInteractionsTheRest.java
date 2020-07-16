package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class VerifyNoInteractionsTheRest implements Methodable {

    public static VerifyNoInteractionsTheRest verifyNoInteractionsTheRest() {
        MethodableState.inProgress();
        return new VerifyNoInteractionsTheRest();
    }

    @Override
    public void in(MockCoach mockCoach) {
        MethodableState.clear();
        mockCoach.verifyNoInteractionsTheRest();
    }

}
