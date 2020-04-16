package com.andyln;

import java.util.ArrayList;
import java.util.List;

public class Whens {
    private List<Object> mocks;
    private List<MockCoachRunnable> runnables;

    public Whens(Object... alternatingMocksAndRunnables) {
        if (alternatingMocksAndRunnables.length == 0) {
            throw new IllegalArgumentException("Passed in Mocks and Runnables array must not be empty!");
        }

        if (alternatingMocksAndRunnables.length % 2 != 0) {
            throw new IllegalArgumentException("Passed in Mocks and Runnables array must come in pairs! Please check this array, as it is an odd length.");
        }

        List<Object> mocks = new ArrayList<>();
        for (int i = 0; i < alternatingMocksAndRunnables.length; i += 2) {
            mocks.add(alternatingMocksAndRunnables[i]);
        }

        List<MockCoachRunnable> runnables = new ArrayList<>();
        for (int i = 1; i < alternatingMocksAndRunnables.length; i += 2) {
            if (!(alternatingMocksAndRunnables[i] instanceof MockCoachRunnable)) {
                throw new IllegalArgumentException(String.format("alternatingMocksAndRunnables[%d] must be an instance of MockCoachRunnable!", i));
            }

            runnables.add((MockCoachRunnable) alternatingMocksAndRunnables[i]);
        }

        this.mocks = mocks;
        this.runnables = runnables;
    }

    protected List<Object> getMocks() {
        return mocks;
    }

    protected List<MockCoachRunnable> getRunnables() {
        return runnables;
    }
}
