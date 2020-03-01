package com.andyln;

public class MockCoachLegacyBuilder {
    private Object[] mocks;
    private MockCoachRunnable[] whens;
    private MockCoachRunnable[] verifies;

    /**
     * Creates a builder used to construct a MockCoachLegacy. May provide a better experience than constructing MockCoachLegacy using its constructor.
     * A single instance of MockCoachLegacy should be created for several unit tests, corresponding to a method of the class under test.
     *
     * It is recommended to use MockCoach, whenever possible, to avoid having to manage a list of "mocks",
     * and to enforce a Service Dipath Chain within your methods.
     */
    public MockCoachLegacyBuilder() {
    }

    /**
     * Add "mocks" to MockCoachLegacyBuiler. These "mocks" are supposed to be any object that are injected or autowired into an object under test.
     * These "mocks" are also meant to represent services used in Service Cyclic Graphs, but they can be used to represent any place in the codebase.
     * @param mocks These mocks are supposed to be any object that are injected or autowired into an object under test,
     *              but LegacyMockCoach can use these to represent any place in the codebase.
     * @return MockCoachBuilder (with mocks)
     */
    public MockCoachLegacyBuilder mock(Object... mocks) {
        this.mocks = mocks;
        return this;
    }

    /**
     * Add whens to MockCoachLegacyBuilder. Whens is an array of runnables, where each runnable may contain multiple when statements.
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
    public MockCoachLegacyBuilder when(MockCoachRunnable... whens) {
        this.whens = whens;
        return this;
    }

    /**
     * Add verifies to MockCoachLegacyBuilder. Verifies is an array of runnables, where each runnable may contain multiple verify statements.
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
    public MockCoachLegacyBuilder verify(MockCoachRunnable... verifies) {
        this.verifies = verifies;
        return this;
    }

    /**
     * Returns a new MockCoachLegacy using its mocks, whens, and verifies.
     * @return MockCoach
     */
    public MockCoachLegacy build() {
        return new MockCoachLegacy(mocks, whens, verifies);
    }

}
