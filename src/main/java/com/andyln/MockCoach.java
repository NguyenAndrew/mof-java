package com.andyln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockCoach {

    private Object[] mocks;
    private MockCoachRunnable[] whenRunnables;
    private MockCoachRunnable[] verifyRunnables;

    private Map<Object, Integer> mockMap;

    private boolean containsMoreThanOneMock;

    private boolean canCallWhenTheRest;
    private int lastSuccessfulMockIndex;

    /*
     * If true, mocks are in a circle chain
     * If false, mocks are in a path graph chain
     */
    private boolean isMocksInCircleChain;

    /**
     * super() for subclass
     */
    protected MockCoach() {
    }

    // Shared private constructor
    private void setupMockCoach(Object[] mocks, MockCoachRunnable[] whenRunnables, MockCoachRunnable[] verifyRunnables) {
        if (mocks == null) {
            throw new IllegalArgumentException("mocks/whens/verifies cannot be null!");
        }

        if (mocks.length != whenRunnables.length) {
            throw new IllegalArgumentException("whens length does not match mocks length!");
        }

        if (mocks.length != verifyRunnables.length) {
            throw new IllegalArgumentException("verifies length does not match mocks length!");
        }

        if (mocks.length == 0) {
            throw new IllegalArgumentException("mocks/whens/verifies cannot be empty!");
        }

        mockMap = new HashMap<>();

        containsMoreThanOneMock = mocks.length > 1;

        isMocksInCircleChain = mocks[0] == mocks[mocks.length - 1];

        int lengthOfMocksToCheck = isMocksInCircleChain && containsMoreThanOneMock ? mocks.length - 1 : mocks.length;
        for (int i = 0; i < lengthOfMocksToCheck; i++) {
            if (mocks[i] == null) {
                throw new IllegalArgumentException(String.format("m%d cannot be null!", i + 1));
            }

            if (mocks[i] instanceof Integer) {
                throw new IllegalArgumentException(String.format("m%d cannot be instance of Integer! Please use LegacyMockCoachBuilder and LegacyMockCoach for Integer support.", i + 1));
            }

            if (mocks[i] instanceof Character) {
                throw new IllegalArgumentException(String.format("m%d cannot be instance of Character! Please use LegacyMockCoachBuilder and LegacyMockCoach for Character support.", i + 1));
            }

            if (mocks[i] instanceof String) {
                throw new IllegalArgumentException(String.format("m%d cannot be instance of String! Please use LegacyMockCoachBuilder and LegacyMockCoach for String support.", i + 1));
            }

            if (mocks[i] instanceof Enum<?>) {
                throw new IllegalArgumentException(String.format("m%d cannot be instance of Enum! Please use LegacyMockCoachBuilder and LegacyMockCoach for Enum support.", i + 1));
            }

            Object potentiallyDuplicateMock = mockMap.put(mocks[i], i);
            boolean isDuplicateMock = potentiallyDuplicateMock != null;
            if (isDuplicateMock) {
                throw new IllegalArgumentException(String.format("m%d cannot be the same as a previous mock in mocks!", i + 1));
            }
        }

        this.mocks = mocks;
        this.whenRunnables = whenRunnables;
        this.verifyRunnables = verifyRunnables;

        this.canCallWhenTheRest = false;
        this.lastSuccessfulMockIndex = 0;
    }

    /**
     * A Mock Coach.
     *
     * @param mocks           Array of Mocks injected or autowired into an object-under-test.
     * @param whenRunnables   Array of Java lambdas containing "when(...)" statements
     * @param verifyRunnables Array of JAva lambdas containing "verify(...) statements
     * @throws IllegalArgumentException Prevents calling constructor with any mocks/whens/verifies that are empty, not the same length, or not permitted type.
     */
    MockCoach(Object[] mocks, MockCoachRunnable[] whenRunnables, MockCoachRunnable[] verifyRunnables) {
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * One Mock, MockCoach.
     *
     * @param m1 Mock One
     * @param w1 When Lambda One
     * @param v1 Verify Lambda One
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1
    ) {
        Object[] mocks = {m1};
        MockCoachRunnable[] whenRunnables = {w1};
        MockCoachRunnable[] verifyRunnables = {v1};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Two Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2
    ) {
        Object[] mocks = {m1, m2};
        MockCoachRunnable[] whenRunnables = {w1, w2};
        MockCoachRunnable[] verifyRunnables = {v1, v2};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Three Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     * @param m3 Mock Three
     * @param w3 When Three
     * @param v3 Verify Three
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3
    ) {
        Object[] mocks = {m1, m2, m3};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Four Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     * @param m3 Mock Three
     * @param w3 When Three
     * @param v3 Verify Three
     * @param m4 Mock Four
     * @param w4 When Four
     * @param v4 Verify Four
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4
    ) {
        Object[] mocks = {m1, m2, m3, m4};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Five Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     * @param m3 Mock Three
     * @param w3 When Three
     * @param v3 Verify Three
     * @param m4 Mock Four
     * @param w4 When Four
     * @param v4 Verify Four
     * @param m5 Mock Five
     * @param w5 When Five
     * @param v5 Verify Five
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Six Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     * @param m3 Mock Three
     * @param w3 When Three
     * @param v3 Verify Three
     * @param m4 Mock Four
     * @param w4 When Four
     * @param v4 Verify Four
     * @param m5 Mock Five
     * @param w5 When Five
     * @param v5 Verify Five
     * @param m6 Mock Six
     * @param w6 When Six
     * @param v6 Verify Six
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Seven Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     * @param m3 Mock Three
     * @param w3 When Three
     * @param v3 Verify Three
     * @param m4 Mock Four
     * @param w4 When Four
     * @param v4 Verify Four
     * @param m5 Mock Five
     * @param w5 When Five
     * @param v5 Verify Five
     * @param m6 Mock Six
     * @param w6 When Six
     * @param v6 Verify Six
     * @param m7 Mock Seven
     * @param w7 When Seven
     * @param v7 Verify Seven
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Eight Mocks, Mock Coach.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     * @param m3 Mock Three
     * @param w3 When Three
     * @param v3 Verify Three
     * @param m4 Mock Four
     * @param w4 When Four
     * @param v4 Verify Four
     * @param m5 Mock Five
     * @param w5 When Five
     * @param v5 Verify Five
     * @param m6 Mock Six
     * @param w6 When Six
     * @param v6 Verify Six
     * @param m7 Mock Seven
     * @param w7 When Seven
     * @param v7 Verify Seven
     * @param m8 Mock Eight
     * @param w8 When Eight
     * @param v8 Verify Eight
     */
    public MockCoach(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8};
        setupMockCoach(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Runs all whens before, and not including, when corresponding to mock.
     *
     * @param mock Any mock within mocks.
     * @throws IllegalStateException    Calling this method for first/last mocks in circle chain
     *                                  (because first and last mock are the same, there would no way to tell which mock to use).
     *                                  For circle chains, call either whenBeforeFirst() or whenBeforeLast()
     * @throws IllegalArgumentException Calling with object not in mocks.
     */
    public void whenBefore(Object mock) {
        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalStateException("Cannot call whenBefore(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use whenBeforeFirst() or whenBeforeLast()");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call whenBefore(Object mock) for mock not in mocks!");
        }

        int indexOfMock = objectIndexOfMock;

        for (int i = 0; i < indexOfMock; i++) {
            try {
                whenRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }

        canCallWhenTheRest = true;
        lastSuccessfulMockIndex = indexOfMock;
    }

    /**
     * Placeholder for testing with mocks in circle chain. This method exists, because it creates a better user experience
     * when refactoring tests.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call whenBefore(INSERT_FIRST_MOCK_HERE)
     */
    public void whenBeforeFirst() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call whenBeforeFirst() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_FIRST_MOCK_HERE)");
        }

        canCallWhenTheRest = true;
        lastSuccessfulMockIndex = 0;
    }

    /**
     * Runs all whens before, and not including, when corresponding to last mock in mocks.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call whenBefore(INSERT_LAST_MOCK_HERE)
     */
    public void whenBeforeLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call whenBeforeLast() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_LAST_MOCK_HERE)");
        }

        int indexOfLastMock = this.mocks.length - 1;
        for (int i = 0; i < indexOfLastMock; i++) {
            try {
                whenRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }
    }

    /**
     * Runs all whens.
     */
    public void whenAll() {
        for (int i = 0; i < this.mocks.length; i++) {
            try {
                whenRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }
    }

    /**
     * Runs when lambdas after, but not including, mock used in previous method.
     */
    public void whenTheRest() {
        if (!canCallWhenTheRest) {
            throw new IllegalStateException("Cannot call whenTheRest()! Must be called only after whenBefore(mock) or whenFirst()");
        }

        for (int i = lastSuccessfulMockIndex + 1; i < this.mocks.length; i++) {
            try {
                whenRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }

        canCallWhenTheRest = false;
    }

    /**
     * Runs all verifies before, and not including, verify corresponding to mock.
     *
     * @param mock Any mock within mocks.
     * @throws IllegalStateException    Calling this method for first/last mocks in circle chain
     *                                  (because first and last mock are the same, there would no way to tell which mock to use).
     *                                  For circle chains, call either verifyBeforeFirst() or verifyBeforeLast()
     * @throws IllegalArgumentException Calling with object not in mocks.
     */
    public void verifyBefore(Object mock) {
        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalStateException("Cannot call verifyBefore(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyBeforeFirst() or verifyBeforeLast()");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyBefore(Object mock) for mock not in mocks!");
        }

        int indexOfMock = objectIndexOfMock;

        for (int i = 0; i < indexOfMock; i++) {
            try {
                verifyRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }
    }

    /**
     * Runs all verifies up to, and including, verify corresponding to mock.
     *
     * @param mock Any mock within mocks.
     * @throws IllegalStateException    Calling this method for first/last mocks in circle chain
     *                                  (because first and last mock are the same, there would no way to tell which mock to use).
     *                                  For circle chains, call either verifyFirst() or verifyLast()
     * @throws IllegalArgumentException Calling with object not in mocks.
     */
    public void verifyThrough(Object mock) {
        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalStateException("Cannot call verify(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyFirst() or verifyLast()");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verify(Object mock) for mock not in mocks!");
        }

        int indexOfMock = objectIndexOfMock;

        for (int i = 0; i <= indexOfMock; i++) {
            try {
                verifyRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }
    }

    /**
     * Placeholder for testing with mocks in circle chain. This method exists, because it creates a better user experience
     * when refactoring tests.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call verifyBefore(INSERT_FIRST_MOCK_HERE)
     */
    public void verifyBeforeFirst() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyBeforeFirst() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_FIRST_MOCK_HERE)");
        }

        // Does nothing. Used as a placeholder.
    }

    /**
     * Runs all verifies before, and not including, verify corresponding to last mock in mocks.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call verifyBefore(INSERT_LAST_MOCK_HERE)
     */
    public void verifyBeforeLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyBeforeLast() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_LAST_MOCK_HERE)");
        }

        for (int i = 0; i < this.mocks.length - 1; i++) {
            try {
                verifyRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }
    }

    /**
     * Placeholder for testing with mocks in circle chain. This method exists, because it creates a better user experience
     * when refactoring tests.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call verify(INSERT_FIRST_MOCK_HERE)
     */
    public void verifyFirst() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyFirst() for mocks in a path graph! For mocks in a path graph, use verify(INSERT_FIRST_MOCK_HERE)");
        }

        try {
            verifyRunnables[0].run();
        } catch (Exception e) {
            throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", 1), e);
        }
    }

    /**
     * Runs all verifies. This method exists, because it creates a better user experience
     * when refactoring tests.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call verify(INSERT_LAST_MOCK_HERE)
     */
    public void verifyLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyLast() for mocks in a path graph! For mocks in a path graph, use verify(INSERT_LAST_MOCK_HERE)");
        }

        this.verifyAll();
    }

    /**
     * Runs all verifies.
     */
    public void verifyAll() {
        for (int i = 0; i < this.mocks.length; i++) {
            try {
                verifyRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }
    }

    public static class Builder {
        private List<Object> mocks;
        private List<MockCoachRunnable> whens;
        private List<MockCoachRunnable> verifies;

        /**
         * Creates a builder for MockCoach.
         */
        public Builder() {
            mocks = new ArrayList<>();
            whens = new ArrayList<>();
            verifies = new ArrayList<>();
        }

        /**
         * Adds to end of builder.
         *
         * @param m Mock
         * @param w When Lambda
         * @param v Verify Lambda
         */
        public Builder add(Object m, MockCoachRunnable w, MockCoachRunnable v) {
            mocks.add(m);
            whens.add(w);
            verifies.add(v);
            return this;
        }

        /**
         * Returns a new MockCoach from previously added mocks, when lambdas, and verify lambdas.
         *
         * @return MockCoach
         */
        public MockCoach build() {
            return new MockCoach(
                    mocks.toArray(new Object[0]),
                    whens.toArray(new MockCoachRunnable[0]),
                    verifies.toArray(new MockCoachRunnable[0])
            );
        }
    }

    /**
     * Creates a builder for MockCoach.
     *
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }
}
