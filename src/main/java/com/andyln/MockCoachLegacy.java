package com.andyln;

import java.util.HashMap;
import java.util.Map;

public class MockCoachLegacy extends MockCoach {
    private Object[] mocks;
    private MockCoachRunnable[] whenRunnables;
    private MockCoachRunnable[] verifyRunnables;

    private Map<Object, Integer> mockMap;

    private boolean containsMoreThanOneMock;

    /*
     * If true, mocks are in a circle chain
     * If false, mocks are in a path graph chain
     */
    private boolean isMocksInCircleChain;

    // Shared private constructor
    private void setupMockCoachLegacy(Object[] mocks, MockCoachRunnable[] whenRunnables, MockCoachRunnable[] verifyRunnables) {
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

        if (!containsMoreThanOneMock) {
            // Only contains single mock in mocks
            mockMap.put(mocks[0], 0);
            this.mocks = mocks;
            this.whenRunnables = whenRunnables;
            this.verifyRunnables = verifyRunnables;
            return;
        }

        isMocksInCircleChain = mocks[0] == mocks[mocks.length - 1];

        int lengthOfMocksToCheck = isMocksInCircleChain ? mocks.length - 1 : mocks.length;
        for (int i = 0; i < lengthOfMocksToCheck; i++) {
            if (mocks[i] == null) {
                throw new IllegalArgumentException(String.format("mocks[%d] cannot be null!", i));
            }

            Object potentiallyDuplicateMock = mockMap.put(mocks[i], i);
            boolean isDuplicateMock = potentiallyDuplicateMock != null;
            if (isDuplicateMock) {
                throw new IllegalArgumentException(String.format("mocks[%d] cannot be the same as a previous mock in mocks!", i));
            }
        }

        this.mocks = mocks;
        this.whenRunnables = whenRunnables;
        this.verifyRunnables = verifyRunnables;
    }

    /**
     * A Mock Coach Legacy.
     * <p>
     * Unlike MockCoach, MockCoachLegacy can use any object for its "mocks" (such as an Enum).
     * These "mocks" are also meant to represent mocks used in Service Cyclic Graphs, but they can be used to represent any place in the codebase.
     * <p>
     * It is recommended to use MockCoach, whenever possible, to avoid having to manage a list of "mocks",
     * and to enforce a Service Dipath Chain within your methods.
     *
     * @param mocks           Array of Mocks injected or autowired into an object-under-test.
     * @param whenRunnables   Array of Java lambdas containing "when(...)" statements
     * @param verifyRunnables Array of JAva lambdas containing "verify(...) statements
     * @throws IllegalArgumentException Prevents calling constructor with any mocks/whens/verifies that are empty, not the same length, or not permitted type.
     */
    public MockCoachLegacy(Object[] mocks, MockCoachRunnable[] whenRunnables, MockCoachRunnable[] verifyRunnables) {
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * One Mock, Mock Coach Legacy.
     *
     * @param m1 Mock One
     * @param w1 When Lambda One
     * @param v1 Verify Lambda One
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1
    ) {
        Object[] mocks = {m1};
        MockCoachRunnable[] whenRunnables = {w1};
        MockCoachRunnable[] verifyRunnables = {v1};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Two Mocks, Mock Coach Legacy.
     *
     * @param m1 Mock One
     * @param w1 When One
     * @param v1 Verify One
     * @param m2 Mock Two
     * @param w2 When Two
     * @param v2 Verify Two
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2
    ) {
        Object[] mocks = {m1, m2};
        MockCoachRunnable[] whenRunnables = {w1, w2};
        MockCoachRunnable[] verifyRunnables = {v1, v2};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Three Mocks, Mock Coach Legacy.
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
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3
    ) {
        Object[] mocks = {m1, m2, m3};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Four Mocks, Mock Coach Legacy.
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
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4
    ) {
        Object[] mocks = {m1, m2, m3, m4};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Five Mocks, Mock Coach Legacy.
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
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Six Mocks, Mock Coach Legacy.
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
    public MockCoachLegacy(
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
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Seven Mocks, Mock Coach Legacy.
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
    public MockCoachLegacy(
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
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Eight Mocks, Mock Coach Legacy.
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
    public MockCoachLegacy(
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
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Nine Mocks, Mock Coach Legacy.
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
     * @param m9 Mock Nine
     * @param w9 When Nine
     * @param v9 Verify Nine
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Ten Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Eleven Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     * @param m11 Mock Eleven
     * @param w11 When Eleven
     * @param v11 Verify Eleven
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10,
            Object m11, MockCoachRunnable w11, MockCoachRunnable v11
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Twelve Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     * @param m11 Mock Eleven
     * @param w11 When Eleven
     * @param v11 Verify Eleven
     * @param m12 Mock Twelve
     * @param w12 When Twelve
     * @param v12 Verify Twelve
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10,
            Object m11, MockCoachRunnable w11, MockCoachRunnable v11,
            Object m12, MockCoachRunnable w12, MockCoachRunnable v12
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Thirteen Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     * @param m11 Mock Eleven
     * @param w11 When Eleven
     * @param v11 Verify Eleven
     * @param m12 Mock Twelve
     * @param w12 When Twelve
     * @param v12 Verify Twelve
     * @param m13 Mock Thirteen
     * @param w13 When Thirteen
     * @param v13 Verify Thirteen
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10,
            Object m11, MockCoachRunnable w11, MockCoachRunnable v11,
            Object m12, MockCoachRunnable w12, MockCoachRunnable v12,
            Object m13, MockCoachRunnable w13, MockCoachRunnable v13
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Fourteen Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     * @param m11 Mock Eleven
     * @param w11 When Eleven
     * @param v11 Verify Eleven
     * @param m12 Mock Twelve
     * @param w12 When Twelve
     * @param v12 Verify Twelve
     * @param m13 Mock Thirteen
     * @param w13 When Thirteen
     * @param v13 Verify Thirteen
     * @param m14 Mock Fourteen
     * @param w14 When Fourteen
     * @param v14 Verify Fourteen
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10,
            Object m11, MockCoachRunnable w11, MockCoachRunnable v11,
            Object m12, MockCoachRunnable w12, MockCoachRunnable v12,
            Object m13, MockCoachRunnable w13, MockCoachRunnable v13,
            Object m14, MockCoachRunnable w14, MockCoachRunnable v14
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Fifteen Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     * @param m11 Mock Eleven
     * @param w11 When Eleven
     * @param v11 Verify Eleven
     * @param m12 Mock Twelve
     * @param w12 When Twelve
     * @param v12 Verify Twelve
     * @param m13 Mock Thirteen
     * @param w13 When Thirteen
     * @param v13 Verify Thirteen
     * @param m14 Mock Fourteen
     * @param w14 When Fourteen
     * @param v14 Verify Fourteen
     * @param m15 Mock Fifteen
     * @param w15 When Fifteen
     * @param v15 Verify Fifteen
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10,
            Object m11, MockCoachRunnable w11, MockCoachRunnable v11,
            Object m12, MockCoachRunnable w12, MockCoachRunnable v12,
            Object m13, MockCoachRunnable w13, MockCoachRunnable v13,
            Object m14, MockCoachRunnable w14, MockCoachRunnable v14,
            Object m15, MockCoachRunnable w15, MockCoachRunnable v15
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
    }

    /**
     * Sixteen Mocks, Mock Coach Legacy.
     *
     * @param m1  Mock One
     * @param w1  When One
     * @param v1  Verify One
     * @param m2  Mock Two
     * @param w2  When Two
     * @param v2  Verify Two
     * @param m3  Mock Three
     * @param w3  When Three
     * @param v3  Verify Three
     * @param m4  Mock Four
     * @param w4  When Four
     * @param v4  Verify Four
     * @param m5  Mock Five
     * @param w5  When Five
     * @param v5  Verify Five
     * @param m6  Mock Six
     * @param w6  When Six
     * @param v6  Verify Six
     * @param m7  Mock Seven
     * @param w7  When Seven
     * @param v7  Verify Seven
     * @param m8  Mock Eight
     * @param w8  When Eight
     * @param v8  Verify Eight
     * @param m9  Mock Nine
     * @param w9  When Nine
     * @param v9  Verify Nine
     * @param m10 Mock Ten
     * @param w10 When Ten
     * @param v10 Verify Ten
     * @param m11 Mock Eleven
     * @param w11 When Eleven
     * @param v11 Verify Eleven
     * @param m12 Mock Twelve
     * @param w12 When Twelve
     * @param v12 Verify Twelve
     * @param m13 Mock Thirteen
     * @param w13 When Thirteen
     * @param v13 Verify Thirteen
     * @param m14 Mock Fourteen
     * @param w14 When Fourteen
     * @param v14 Verify Fourteen
     * @param m15 Mock Fifteen
     * @param w15 When Fifteen
     * @param v15 Verify Fifteen
     * @param m16 Mock Sixteen
     * @param w16 When Sixteen
     * @param v16 Verify Sixteen
     */
    public MockCoachLegacy(
            Object m1, MockCoachRunnable w1, MockCoachRunnable v1,
            Object m2, MockCoachRunnable w2, MockCoachRunnable v2,
            Object m3, MockCoachRunnable w3, MockCoachRunnable v3,
            Object m4, MockCoachRunnable w4, MockCoachRunnable v4,
            Object m5, MockCoachRunnable w5, MockCoachRunnable v5,
            Object m6, MockCoachRunnable w6, MockCoachRunnable v6,
            Object m7, MockCoachRunnable w7, MockCoachRunnable v7,
            Object m8, MockCoachRunnable w8, MockCoachRunnable v8,
            Object m9, MockCoachRunnable w9, MockCoachRunnable v9,
            Object m10, MockCoachRunnable w10, MockCoachRunnable v10,
            Object m11, MockCoachRunnable w11, MockCoachRunnable v11,
            Object m12, MockCoachRunnable w12, MockCoachRunnable v12,
            Object m13, MockCoachRunnable w13, MockCoachRunnable v13,
            Object m14, MockCoachRunnable w14, MockCoachRunnable v14,
            Object m15, MockCoachRunnable w15, MockCoachRunnable v15,
            Object m16, MockCoachRunnable w16, MockCoachRunnable v16
    ) {
        Object[] mocks = {m1, m2, m3, m4, m5, m6, m7, m8, m9, m10, m11, m12, m13, m14, m15, m16};
        MockCoachRunnable[] whenRunnables = {w1, w2, w3, w4, w5, w6, w7, w8, w9, w10, w11, w12, w13, w14, w15, w16};
        MockCoachRunnable[] verifyRunnables = {v1, v2, v3, v4, v5, v6, v7, v8, v9, v10, v11, v12, v13, v14, v15, v16};
        setupMockCoachLegacy(mocks, whenRunnables, verifyRunnables);
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
    @Override
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
                throw new RuntimeException(String.format("whens[%d] throws an exception! Please check your whens.", i), e);
            }
        }
    }

    /**
     * Placeholder for testing with mocks in circle chain. This method exists, because it creates a better user experience
     * when refactoring tests.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call whenBefore(INSERT_FIRST_MOCK_HERE)
     */
    @Override
    public void whenBeforeFirst() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call whenBeforeFirst() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_FIRST_MOCK_HERE)");
        }

        // Does nothing. Used as a placeholder.
    }

    /**
     * Runs all whens before, and not including, when corresponding to last mock in mocks.
     *
     * @throws IllegalStateException Calling this method for mocks in directed path chain
     *                               (using this method in directed path chain, may cause confusion on which mock is being referred to).
     *                               For directed path chains, call whenBefore(INSERT_LAST_MOCK_HERE)
     */
    @Override
    public void whenBeforeLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call whenBeforeLast() for mocks in a path graph! For mocks in a path graph, use whenBefore(INSERT_LAST_MOCK_HERE)");
        }

        int indexOfLastMock = this.mocks.length - 1;
        for (int i = 0; i < indexOfLastMock; i++) {
            try {
                whenRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("whens[%d] throws an exception! Please check your whens.", i), e);
            }
        }
    }

    /**
     * Runs all whens.
     */
    @Override
    public void whenEverything() {
        for (int i = 0; i < this.mocks.length; i++) {
            try {
                whenRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("whens[%d] throws an exception! Please check your whens.", i), e);
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
    @Override
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
                throw new RuntimeException(String.format("verifies[%d] throws an exception! Please check your verifies.", i), e);
            }
        }
    }

    /**
     * Runs all verifies before, and including, verify corresponding to mock.
     *
     * @param mock Any mock within mocks.
     * @throws IllegalStateException    Calling this method for first/last mocks in circle chain
     *                                  (because first and last mock are the same, there would no way to tell which mock to use).
     *                                  For circle chains, call either verifyFirst() or verifyLast()
     * @throws IllegalArgumentException Calling with object not in mocks.
     */
    @Override
    public void verify(Object mock) {
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
                throw new RuntimeException(String.format("verifies[%d] throws an exception! Please check your verifies.", i), e);
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
    @Override
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
    @Override
    public void verifyBeforeLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyBeforeLast() for mocks in a path graph! For mocks in a path graph, use verifyBefore(INSERT_LAST_MOCK_HERE)");
        }

        for (int i = 0; i < this.mocks.length - 1; i++) {
            try {
                verifyRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("verifies[%d] throws an exception! Please check your verifies.", i), e);
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
    @Override
    public void verifyFirst() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyFirst() for mocks in a path graph! For mocks in a path graph, use verify(INSERT_FIRST_MOCK_HERE)");
        }

        try {
            verifyRunnables[0].run();
        } catch (Exception e) {
            throw new RuntimeException(String.format("verifies[%d] throws an exception! Please check your verifies.", 0), e);
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
    @Override
    public void verifyLast() {
        if (containsMoreThanOneMock && !isMocksInCircleChain) {
            throw new IllegalStateException("Cannot call verifyLast() for mocks in a path graph! For mocks in a path graph, use verify(INSERT_LAST_MOCK_HERE)");
        }

        this.verifyEverything();
    }

    /**
     * Runs all verifies.
     */
    @Override
    public void verifyEverything() {
        for (int i = 0; i < this.mocks.length; i++) {
            try {
                verifyRunnables[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("verifies[%d] throws an exception! Please check your verifies.", i), e);
            }
        }
    }
}
