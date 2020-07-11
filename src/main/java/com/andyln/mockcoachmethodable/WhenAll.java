package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;

public class WhenAll implements Methodable {

    public static WhenAll whenAll() {
        return new WhenAll();
    }

    @Override
    public void in(MockCoach mockCoach) {
        mockCoach.whenAll();
    }
}
