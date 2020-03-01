package com.andyln;

public class MockCoachBuilder {
    private Object[] mocks;
    private MockCoachRunnable[] whens;
    private MockCoachRunnable[] verifies;

    /**
     * Creates a builder used to construct a MockCoach. May provide a better experience than constructing MockCoach using its constructor.
     * A single instance of MockCoach should be created for several unit tests, corresponding to a method of the class under test.
     */
    public MockCoachBuilder() {
    }

    /**
     * Add mocks to MockCoachBuiler. These mocks are any object that are injected or autowired into an object under test.
     * @param mocks These mocks are any object that are injected or autowired into an object under test.
     * @return MockCoachBuilder (with mocks)
     */
    public MockCoachBuilder mock(Object... mocks) {
        this.mocks = mocks;
        return this;
    }

    /**
     * Add whens to MockCoachBuilder. Whens is an array of runnables, where each runnable may contain multiple when statements.
     * <p>
     * Example of a single when: {@code
     * () -> &#123;
     *     when(mock.method()).thenReturn(someValue);
     *     when(mock.anotherMethod()).thenReturn(anotherValue);
     * &#125;
     * } </p>
     * @param whens Multiple when runnables
     * @return MockCoachBuilder (with whens)
     */
    public MockCoachBuilder when(MockCoachRunnable... whens) {
        this.whens = whens;
        return this;
    }

    /**
     * Add verifies to MockCoachBuilder. Verifies is an array of runnables, where each runnable may contain multiple verify statements.
     * <p>
     * Example of a single verify: {@code
     * () -> &#123;
     *     verify(mock, times(1)).method();
     *     verify(mock, times(1)).anotherMethod());
     * &#125;
     * } </p>
     * @param verifies Multiple verify runnables
     * @return MockCoachBuilder (with verifies)
     */
    public MockCoachBuilder verify(MockCoachRunnable... verifies) {
        this.verifies = verifies;
        return this;
    }

    /**
     * Returns a new MockCoach using its mocks, whens, and verifies.
     * @return MockCoach
     */
    public MockCoach build() {
        return new MockCoach(mocks, whens, verifies);
    }

}
