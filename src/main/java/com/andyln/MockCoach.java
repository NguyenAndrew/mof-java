package com.andyln;

import java.util.HashSet;
import java.util.Set;

public class MockCoach {
    private Object[] mocks;
    private MockCoachRunnable[] whens;
    private MockCoachRunnable[] verifies;

    public MockCoach(Object[] mocks, MockCoachRunnable[] whens, MockCoachRunnable[] verifies) {
        if (mocks.length != whens.length) {
            throw new IllegalArgumentException("whens length does not match mocks length!");
        }

        if (mocks.length != verifies.length) {
            throw new IllegalArgumentException("verifies length does not match whens length!");
        }

        Set<Object> mockSet = new HashSet<>();
        for (int i = 0; i < mocks.length; i++) {
            if (mocks[i] == null) {
                throw new IllegalArgumentException(String.format("mocks[%d] cannot be null!", i));
            }

            boolean isDuplicateMock = !mockSet.add(mocks[i]);

            if (isDuplicateMock) {
                throw new IllegalArgumentException(String.format("mocks[%d] cannot be the same as a previous mock in mocks!", i));
            }
        }

        this.mocks = mocks;
        this.whens = whens;
        this.verifies = verifies;
    }

    public void whenBefore(Object mock) throws Exception {
        for (int i = 0; i < this.mocks.length; i++) {
            if (this.mocks[i] == mock) {
                return;
            }
            whens[i].run();
        }
    }

    public void whenEverything() throws Exception {
        for (int i = 0; i < this.mocks.length; i++) {
            whens[i].run();
        }
    }

    public void verifyBefore(Object mock) throws Exception {
        for (int i = 0; i < this.mocks.length; i++) {
            if (this.mocks[i] == mock) {
                return;
            }
            verifies[i].run();
        }
    }

    public void verify(Object mock) throws Exception {
        for (int i = 0; i < this.mocks.length; i++) {
            verifies[i].run();
            if (this.mocks[i] == mock) {
                return;
            }
        }
    }

    public void verifyEverything() throws Exception {
        for (int i = 0; i < this.mocks.length; i++) {
            verifies[i].run();
        }
    }
}
