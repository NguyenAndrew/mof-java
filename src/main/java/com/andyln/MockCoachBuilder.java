package com.andyln;

public class MockCoachBuilder {
    Object[] mocks;
    MockCoachRunnable[] whens;
    MockCoachRunnable[] verifies;

    public MockCoachBuilder mock(Object... mocks) {
        this.mocks = mocks;
        return this;
    }

    public MockCoachBuilder when(MockCoachRunnable... whens) {
        this.whens = whens;
        return this;
    }

    public MockCoachBuilder verify(MockCoachRunnable... verifies) {
        this.verifies = verifies;
        return this;
    }

    public MockCoach build() {
        return new MockCoach(mocks, whens, verifies);
    }

}
