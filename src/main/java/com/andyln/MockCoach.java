package com.andyln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MockCoach {

    private Object[] mocks;
    private WhenLambda[] whenLambdas;
    private VerifyLambda[] verifyLambdas;

    private NoInteractionLambda verifyNoInteractionLambda;

    private Map<Object, Integer> mockMap;

    private boolean containsMoreThanOneMock;

    private boolean canCallWhenTheRest;
    private boolean canCallVerifyTheRest;
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
    private void setupMockCoach(Object[] mocks, WhenLambda[] whenLambdas, VerifyLambda[] verifyLambdas) {
        if (mocks == null) {
            throw new IllegalArgumentException("mocks/whens/verifies cannot be null!");
        }

        if (mocks.length != whenLambdas.length) {
            throw new IllegalArgumentException("whens length does not match mocks length!");
        }

        if (mocks.length != verifyLambdas.length) {
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
        this.whenLambdas = whenLambdas;
        this.verifyLambdas = verifyLambdas;

        this.canCallWhenTheRest = false;
        this.lastSuccessfulMockIndex = 0;
    }

    /**
     * A Mock Coach.
     *
     * @param mocks         Array of Mocks injected or autowired into an object-under-test.
     * @param whenLambdas   Array of Java lambdas containing "when(...)" statements
     * @param verifyLambdas Array of Java lambdas containing "verify(...) statements
     * @throws IllegalArgumentException Prevents calling constructor with any mocks/whens/verifies that are empty, not the same length, or not permitted type.
     */
    MockCoach(Object[] mocks, WhenLambda[] whenLambdas, VerifyLambda[] verifyLambdas) {
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
    }

    /**
     * One Mock, MockCoach.
     *
     * @param m1 Mock One
     * @param w1 When Lambda One
     * @param v1 Verify Lambda One
     */
    public MockCoach(
            Object m1, WhenLambda w1, VerifyLambda v1
    ) {
        Object[] mocks = {m1};
        WhenLambda[] whenLambdas = {w1};
        VerifyLambda[] verifyLambdas = {v1};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2
    ) {
        Object[] mocks = {m1, m2};
        WhenLambda[] whenLambdas = {w1, w2};
        VerifyLambda[] verifyLambdas = {v1, v2};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2,
            Object m3, WhenLambda w3, VerifyLambda v3
    ) {
        Object[] mocks = {m1, m2, m3};
        WhenLambda[] whenLambdas = {w1, w2, w3};
        VerifyLambda[] verifyLambdas = {v1, v2, v3};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2,
            Object m3, WhenLambda w3, VerifyLambda v3,
            Object m4, WhenLambda w4, VerifyLambda v4
    ) {
        Object[] mocks = {m1, m2, m3, m4};
        WhenLambda[] whenLambdas = {w1, w2, w3, w4};
        VerifyLambda[] verifyLambdas = {v1, v2, v3, v4};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2,
            Object m3, WhenLambda w3, VerifyLambda v3,
            Object m4, WhenLambda w4, VerifyLambda v4,
            Object m5, WhenLambda w5, VerifyLambda v5
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5};
        WhenLambda[] whenLambdas = {w1, w2, w3, w4, w5};
        VerifyLambda[] verifyLambdas = {v1, v2, v3, v4, v5};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2,
            Object m3, WhenLambda w3, VerifyLambda v3,
            Object m4, WhenLambda w4, VerifyLambda v4,
            Object m5, WhenLambda w5, VerifyLambda v5,
            Object m6, WhenLambda w6, VerifyLambda v6
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6};
        WhenLambda[] whenLambdas = {w1, w2, w3, w4, w5, w6};
        VerifyLambda[] verifyLambdas = {v1, v2, v3, v4, v5, v6};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2,
            Object m3, WhenLambda w3, VerifyLambda v3,
            Object m4, WhenLambda w4, VerifyLambda v4,
            Object m5, WhenLambda w5, VerifyLambda v5,
            Object m6, WhenLambda w6, VerifyLambda v6,
            Object m7, WhenLambda w7, VerifyLambda v7
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7};
        WhenLambda[] whenLambdas = {w1, w2, w3, w4, w5, w6, w7};
        VerifyLambda[] verifyLambdas = {v1, v2, v3, v4, v5, v6, v7};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
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
            Object m1, WhenLambda w1, VerifyLambda v1,
            Object m2, WhenLambda w2, VerifyLambda v2,
            Object m3, WhenLambda w3, VerifyLambda v3,
            Object m4, WhenLambda w4, VerifyLambda v4,
            Object m5, WhenLambda w5, VerifyLambda v5,
            Object m6, WhenLambda w6, VerifyLambda v6,
            Object m7, WhenLambda w7, VerifyLambda v7,
            Object m8, WhenLambda w8, VerifyLambda v8
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8};
        WhenLambda[] whenLambdas = {w1, w2, w3, w4, w5, w6, w7, w8};
        VerifyLambda[] verifyLambdas = {v1, v2, v3, v4, v5, v6, v7, v8};
        setupMockCoach(mocks, whenLambdas, verifyLambdas);
    }

    /**
     * Allows usage of verifyNoInteractionsTheRest and verifyNoInteractionsTheRestAfter.
     *
     * @param verifyNoInteractionLambda A Java Lambda. Example: "setVerifyNoInteractions(mock -&gt; verifyNoInteractions(mock))"
     */
    public MockCoach putVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) {
        this.verifyNoInteractionLambda = verifyNoInteractionLambda;
        return this;
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
                whenLambdas[i].run();
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
                whenLambdas[i].run();
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
                whenLambdas[i].run();
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
            throw new IllegalStateException("Cannot call whenTheRest()! Must be called only after whenBefore(mock) or whenThroughFirst()");
        }

        for (int i = lastSuccessfulMockIndex + 1; i < this.mocks.length; i++) {
            try {
                whenLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }

        canCallWhenTheRest = false;
    }

    /**
     * Runs when lambdas after, but not including, mock passed into method.
     *
     * @param mock Mock after previously used mock, excluding last mock
     * @throws IllegalStateException    Calling this method when not using whenBefore(mock)
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with mock that is at the end of mock list (this method does not have to be called, in this case).
     *                                  Calling with mock not after previously used mock (would unnecessarily re-run previously run when lambdas).
     */
    public void whenTheRestAfter(Object mock) {
        if (!canCallWhenTheRest) {
            throw new IllegalStateException("Cannot call whenTheRestAfter(Object mock)! Must be called only after whenBefore(mock) or whenThroughFirst()");
        }

        if (isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call whenTheRestAfter(Object mock) for first or last mock in circle chain. If specifying first mock, use whenTheRest(). If specifying the last mock, then this method does not have to be called (will have identical functionality)");
        }

        if (mock == mocks[mocks.length - 1]) {
            throw new IllegalArgumentException("Cannot call whenTheRestAfter(Object mock) for the last mock! Not calling this method will have identical functionality");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call whenTheRestAfter(Object mock) for mock not in mocks!");
        }

        if (objectIndexOfMock < lastSuccessfulMockIndex) {
            throw new IllegalArgumentException("Cannot call whenTheRestAfter(Object mock) for a mock located before previously used mock! Make sure correct mock is being passed into this method");
        }

        for (int i = objectIndexOfMock + 1; i < this.mocks.length; i++) {
            try {
                whenLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }
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
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }

        canCallVerifyTheRest = true;
        lastSuccessfulMockIndex = indexOfMock;
    }

    /**
     * Runs all verifies up to, and including, verify corresponding to mock.
     *
     * @param mock Any mock within mocks.
     * @throws IllegalStateException    Calling this method for first/last mocks in circle chain
     *                                  (because first and last mock are the same, there would no way to tell which mock to use).
     *                                  For circle chains, call either verifyThroughFirst() or verifyThroughLast()
     * @throws IllegalArgumentException Calling with object not in mocks.
     */
    public void verifyThrough(Object mock) {
        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalStateException("Cannot call verifyThrough(Object mock) for first/last mock in a circle chain! For mocks in a circle chain, use verifyThroughFirst() or verifyThroughLast()");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyThrough(Object mock) for mock not in mocks!");
        }

        int indexOfMock = objectIndexOfMock;

        for (int i = 0; i <= indexOfMock; i++) {
            try {
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }

        canCallVerifyTheRest = true;
        lastSuccessfulMockIndex = indexOfMock;
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

        canCallVerifyTheRest = true;
        lastSuccessfulMockIndex = 0;
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
                verifyLambdas[i].run();
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
     *                               For directed path chains, call verifyThrough(INSERT_FIRST_MOCK_HERE)
     */
    public void verifyThroughFirst() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyThroughFirst() for mocks in a path graph! For mocks in a path graph, use verifyThrough(INSERT_FIRST_MOCK_HERE)");
        }

        try {
            verifyLambdas[0].run();
        } catch (Exception e) {
            throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", 1), e);
        }

        canCallVerifyTheRest = true;
        lastSuccessfulMockIndex = 0;
    }

    /**
     * Runs all verifies. This method exists, because it creates a better user experience
     * when refactoring tests.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call verifyThrough(INSERT_LAST_MOCK_HERE)
     */
    public void verifyThroughLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyThroughLast() for mocks in a path graph! For mocks in a path graph, use verifyThrough(INSERT_LAST_MOCK_HERE)");
        }

        this.verifyAll();
    }

    /**
     * Runs all verifies.
     */
    public void verifyAll() {
        for (int i = 0; i < this.mocks.length; i++) {
            try {
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }
    }

    /**
     * Runs verify lambdas after, but not including, mock used in previous method.
     */
    public void verifyTheRest() {
        if (!canCallVerifyTheRest) {
            throw new IllegalStateException("Cannot call verifyTheRest()! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()");
        }

        for (int i = lastSuccessfulMockIndex + 1; i < this.mocks.length; i++) {
            try {
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }

        canCallVerifyTheRest = false;
    }

    /**
     * Runs no interaction lambda after, but not including, mocks used in previous method.
     */
    public void verifyNoInteractionsTheRest() {
        if (verifyNoInteractionLambda == null) {
            throw new IllegalStateException("Must setVerifyNoInteractions(Lambda). Example: 'MockCoach mockCoach = new MockCoach(...).setVerifyNoInteractions(mock -> verifyNoMoreInteractions(mock));'");
        }

        if (!canCallVerifyTheRest) {
            throw new IllegalStateException("Cannot call verifyNoInteractionsTheRest()! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()");
        }

        for (int i = lastSuccessfulMockIndex + 1; i < this.mocks.length; i++) {
            try {
                verifyNoInteractionLambda.run(mocks[i]);
            } catch (Exception e) {
                throw new RuntimeException(String.format("m%d throws an exception! Please check your mocks and verification lambda", i + 1), e);
            }
        }

        canCallVerifyTheRest = false;
    }

    /**
     * Runs verify lambdas after, but not including, mock passed into method.
     *
     * @param mock Mock after previously used mock, excluding last mock
     * @throws IllegalStateException    Calling this method when not using verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with mock that is at the end of mock list (this method does not have to be called, in this case).
     *                                  Calling with mock not after previously used mock (would unnecessarily re-run previously run when lambdas).
     */
    public void verifyTheRestAfter(Object mock) {
        if (!canCallVerifyTheRest) {
            throw new IllegalStateException("Cannot call verifyTheRestAfter(Object mock)! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()");
        }

        if (isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call verifyTheRestAfter(Object mock) for first or last mock in circle chain. If specifying first mock, use verifyTheRest(). If specifying the last mock, then this method does not have to be called (will have identical functionality)");
        }

        if (mock == mocks[mocks.length - 1]) {
            throw new IllegalArgumentException("Cannot call verifyTheRestAfter(Object mock) for the last mock! Not calling this method will have identical functionality");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyTheRestAfter(Object mock) for mock not in mocks!");
        }

        if (objectIndexOfMock < lastSuccessfulMockIndex) {
            throw new IllegalArgumentException("Cannot call verifyTheRestAfter(Object mock) for a mock located before previously used mock! Make sure correct mock is being passed into this method");
        }

        for (int i = objectIndexOfMock + 1; i < this.mocks.length; i++) {
            try {
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }
    }

    /**
     * Runs no interaction lambda after, but not including, mock passed into method.
     *
     * @param mock Mock after previously used mock, excluding last mock
     * @throws IllegalStateException    Calling this method when not using verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with mock that is at the end of mock list (this method does not have to be called, in this case).
     *                                  Calling with mock not after previously used mock (would unnecessarily re-run previously run when lambdas).
     */
    public void verifyNoInteractionsTheRestAfter(Object mock) {
        if (verifyNoInteractionLambda == null) {
            throw new IllegalStateException("Must setVerifyNoInteractions(Lambda). Example: 'MockCoach mockCoach = new MockCoach(...).setVerifyNoInteractions(mock -> verifyNoMoreInteractions(mock));'");
        }

        if (!canCallVerifyTheRest) {
            throw new IllegalStateException("Cannot call verifyNoInteractionsTheRestAfter(Object mock)! Must be called only after verifyBefore(mock)/verifyThrough(mock) or verifyBeforeFirst()/verifyThroughFirst()");
        }

        if (isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call verifyNoInteractionsTheRestAfter(Object mock) for first or last mock in circle chain. If specifying first mock, use verifyTheRest(). If specifying the last mock, then this method does not have to be called (will have identical functionality)");
        }

        if (mock == mocks[mocks.length - 1]) {
            throw new IllegalArgumentException("Cannot call verifyNoInteractionsTheRestAfter(Object mock) for the last mock! Not calling this method will have identical functionality");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyNoInteractionsTheRestAfter(Object mock) for mock not in mocks!");
        }

        if (objectIndexOfMock < lastSuccessfulMockIndex) {
            throw new IllegalArgumentException("Cannot call verifyNoInteractionsTheRestAfter(Object mock) for a mock located before previously used mock! Make sure correct mock is being passed into this method");
        }

        for (int i = objectIndexOfMock + 1; i < this.mocks.length; i++) {
            try {
                verifyNoInteractionLambda.run(mocks[i]);
            } catch (Exception e) {
                throw new RuntimeException(String.format("m%d throws an exception! Please check your mocks and verification lambda.", i + 1), e);
            }
        }
    }

    public static class Builder {
        private List<Object> mocks;
        private List<WhenLambda> whens;
        private List<VerifyLambda> verifies;

        private NoInteractionLambda verifyNoInteractionLambda;

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
         * @return Builder
         */
        public Builder add(Object m, WhenLambda w, VerifyLambda v) {
            mocks.add(m);
            whens.add(w);
            verifies.add(v);
            return this;
        }

        /**
         * Allows usage of verifyNoInteractionsTheRest and verifyNoInteractionsTheRestAfter.
         * @param verifyNoInteractionLambda A Java Lambda. Example: "setVerifyNoInteractions(mock -&gt; verifyNoInteractions(mock))"
         * @return Builder
         */
        public Builder withVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) {
            this.verifyNoInteractionLambda = verifyNoInteractionLambda;
            return this;
        }

        /**
         * Returns a new MockCoach from previously added mocks, when lambdas, and verify lambdas.
         *
         * @return MockCoach
         */
        public MockCoach build() {
            MockCoach mockCoach = new MockCoach(
                    mocks.toArray(new Object[0]),
                    whens.toArray(new WhenLambda[0]),
                    verifies.toArray(new VerifyLambda[0])
            );
            mockCoach.putVerifyNoInteractions(verifyNoInteractionLambda);
            return mockCoach;
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
