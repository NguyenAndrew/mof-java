package com.andyln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mof {

    public static final AllOrRemaining ALL = AllOrRemaining.ALL;
    public static final AllOrRemaining REMAINING = AllOrRemaining.REMAINING;

    public static final FirstOrLast FIRST = FirstOrLast.FIRST;
    public static final FirstOrLast LAST = FirstOrLast.LAST;

    private Object[] mocks;
    private WhenLambda[] whenLambdas;
    private VerifyLambda[] verifyLambdas;

    private NoInteractionLambda verifyNoInteractionLambda;

    private Map<Object, Integer> mockMap;

    private boolean containsMoreThanOneMock;

    private boolean isMocksInCircleChain;

    private int remainingWhenIndex = 0;
    private int remainingVerifyIndex = 0;

    private Mof(Object[] mocks, WhenLambda[] whenLambdas, VerifyLambda[] verifyLambdas) {
        mockMap = new HashMap<>();

        containsMoreThanOneMock = mocks.length > 1;

        isMocksInCircleChain = mocks[0] == mocks[mocks.length - 1];

        int lengthOfMocksToCheck = isMocksInCircleChain && containsMoreThanOneMock ? mocks.length - 1 : mocks.length;
        for (int i = 0; i < lengthOfMocksToCheck; i++) {
            Object potentiallyDuplicateMock = mockMap.put(mocks[i], i);
            boolean isDuplicateMock = potentiallyDuplicateMock != null;
            if (isDuplicateMock) {
                throw new IllegalArgumentException(String.format("m%d cannot be the same as a previous mock in mocks!", i + 1));
            }
        }

        this.mocks = mocks;
        this.whenLambdas = whenLambdas;
        this.verifyLambdas = verifyLambdas;
    }

    /**
     * Runs ALL or REMAINING whens
     *
     * @param aor ALL or REMAINING enum
     * @throws IllegalArgumentException Not calling with ALL or REMAINING enum
     */
    public void when(AllOrRemaining aor) {
        if (aor == AllOrRemaining.ALL) {
            for (int i = 0; i < this.mocks.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mocks.length;
            return;
        }

        if (aor == AllOrRemaining.REMAINING) {
            for (int i = remainingWhenIndex; i < this.mocks.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mocks.length;
            return;
        }

        throw new IllegalArgumentException("aor must be ALL or REMAINING.");
    }

    /**
     * Runs all whens up to, <STRONG>not</STRONG> including, the mock
     *
     * @param mock Any mock within mocks. Note: Excludes ambiguous first/last mock in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve A -> B -> A, when calling with A, do you mean the first or lack mock? Instead of passing A, Use FIRST or LAST instead.
     */
    public void whenBefore(Object mock) {
        if (mock == FirstOrLast.FIRST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingWhenIndex = 1;
            return;
        }

        if (mock == FirstOrLast.LAST) {
            int indexOfLastMock = this.mocks.length - 1;
            for (int i = 0; i < indexOfLastMock; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mocks.length;
            return;
        }

        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call whenBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenBefore(FIRST) or whenBefore(LAST).");
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

        remainingWhenIndex = indexOfMock + 1;
    }

    /**
     * Runs all whens after, <STRONG>not</STRONG> including, the mock
     *
     * @param mock Any mock within mocks. Note: Excludes ambiguous first/last mock in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve A -> B -> A, when calling with A, do you mean the first or lack mock? Instead of passing A, Use FIRST or LAST instead.
     */
    public void whenAfter(Object mock) {
        if (mock == FirstOrLast.FIRST) {
            for (int i = 1; i < this.mocks.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mocks.length;
            return;
        }

        if (mock == FirstOrLast.LAST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingWhenIndex = this.mocks.length;
            return;
        }

        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call whenAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenAfter(FIRST) or whenAfter(LAST).");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call whenAfter(Object mock) for mock not in mocks!");
        }

        for (int i = objectIndexOfMock + 1; i < this.mocks.length; i++) {
            try {
                whenLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }

        remainingWhenIndex = this.mocks.length;
    }

    /**
     * Runs ALL or REMAINING verifies
     *
     * @param aor ALL or REMAINING enum
     * @throws IllegalArgumentException Not calling with ALL or REMAINING enum
     */
    public void verify(AllOrRemaining aor) {
        if (aor == AllOrRemaining.ALL) {
            for (int i = 0; i < this.mocks.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (aor == AllOrRemaining.REMAINING) {
            for (int i = remainingVerifyIndex; i < this.mocks.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        throw new IllegalArgumentException("aor must be ALL or REMAINING.");
    }

    /**
     * Runs all verifies up to, and including, the mock
     *
     * @param mock Any mock within mocks. Note: Excludes ambiguous first/last mock in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve A -> B -> A, when calling with A, do you mean the first or lack mock? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyThrough(Object mock) {
        if (mock == FirstOrLast.FIRST) {
            try {
                verifyLambdas[0].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", 1), e);
            }
            remainingVerifyIndex = 1;
            return;
        }

        if (mock == FirstOrLast.LAST) {
            for (int i = 0; i < this.mocks.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call verifyThrough(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyThrough(FIRST) or verifyThrough(LAST).");
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

        remainingVerifyIndex = indexOfMock + 1;
    }

    /**
     * Runs all verifies up to, <STRONG>not</STRONG> including, the mock
     *
     * @param mock Any mock within mocks. Note: Excludes ambiguous first/last mock in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve A -> B -> A, when calling with A, do you mean the first or lack mock? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyBefore(Object mock) {
        if (mock == FirstOrLast.FIRST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingVerifyIndex = 1;
            return;
        }

        if (mock == FirstOrLast.LAST) {
            for (int i = 0; i < this.mocks.length - 1; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call verifyBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyBefore(FIRST) or verifyBefore(LAST).");
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

        remainingVerifyIndex = indexOfMock + 1;
    }

    /**
     * Runs all verifies after, <STRONG>not</STRONG> including, the mock
     *
     * @param mock Any mock within mocks. Note: Excludes ambiguous first/last mock in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve A -> B -> A, when calling with A, do you mean the first or lack mock? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyAfter(Object mock) {
        if (mock == FirstOrLast.FIRST) {
            for (int i = 1; i < this.mocks.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (mock == FirstOrLast.LAST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call verifyAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyAfter(FIRST) or verifyAfter(LAST).");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyAfter(Object mock) for mock not in mocks!");
        }

        for (int i = objectIndexOfMock + 1; i < this.mocks.length; i++) {
            try {
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }

        remainingVerifyIndex = this.mocks.length;
    }

    private Mof enableVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) {
        this.verifyNoInteractionLambda = verifyNoInteractionLambda;
        return this;
    }

    /**
     * Runs no interaction lambda for ALL or REMAINING mocks.
     *
     * @param aor ALL or REMAINING enum
     * @throws IllegalStateException    Calling this method when verifyNoInteractions is not enabled.
     * @throws IllegalArgumentException Not calling with ALL or REMAINING enum
     */
    public void verifyNoInteractions(AllOrRemaining aor) {

        if (verifyNoInteractionLambda == null) {
            throw new IllegalStateException("Must enableVerifyNoInteractions before calling verifyNoInteractions.");
        }

        int stoppingIndex;

        if (isMocksInCircleChain) {
            if (this.mocks.length == 1) {
                stoppingIndex = this.mocks.length;
            } else if (this.mocks.length == 2) {
                stoppingIndex = 0;
            } else {
                stoppingIndex = this.mocks.length - 1;
            }
        } else {
            stoppingIndex = this.mocks.length;
        }

        if (aor == AllOrRemaining.ALL) {
            for (int i = 0; i < stoppingIndex; i++) {
                try {
                    verifyNoInteractionLambda.run(mocks[i]);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("verifyNoInteractionLambda called with m%d throws an exception! Please check your verifyNoInteractionLambda and mocks.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (aor == AllOrRemaining.REMAINING) {
            for (int i = remainingVerifyIndex; i < stoppingIndex; i++) {
                try {
                    verifyNoInteractionLambda.run(mocks[i]);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("verifyNoInteractionLambda called with m%d throws an exception! Please check your verifyNoInteractionLambda and mocks.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        throw new IllegalArgumentException("aor must be ALL or REMAINING.");

    }

    /**
     * Runs no interaction lambda for all mocks that are <STRONG>only</STRONG> after, but not including, mock passed into method.
     *
     * @param mock Any mock within mocks. Note: Excludes ambiguous first/last mock in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalStateException    Calling this method when verifyNoInteractions is not enabled.
     * @throws IllegalArgumentException Calling with object not in mocks.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve A -> B -> A, when calling with A, do you mean the first or lack mock? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyNoInteractionsAfter(Object mock) {
        if (verifyNoInteractionLambda == null) {
            throw new IllegalStateException("Must enableVerifyNoInteractions before calling verifyNoInteractionsAfter.");
        }

        int stoppingIndex;

        if (isMocksInCircleChain) {
            if (this.mocks.length == 1) {
                stoppingIndex = this.mocks.length;
            } else if (this.mocks.length == 2) {
                stoppingIndex = 0;
            } else {
                stoppingIndex = this.mocks.length - 1;
            }
        } else {
            stoppingIndex = this.mocks.length;
        }

        if (mock == FirstOrLast.FIRST) {
            for (int i = 1; i < stoppingIndex; i++) {
                try {
                    verifyNoInteractionLambda.run(mocks[i]);
                } catch (Exception e) {
                    throw new RuntimeException(String.format("verifyNoInteractionLambda called with m%d throws an exception! Please check your verifyNoInteractionLambda and mocks.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (mock == FirstOrLast.LAST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingVerifyIndex = this.mocks.length;
            return;
        }

        if (containsMoreThanOneMock && isMocksInCircleChain && mock == mocks[0]) {
            throw new IllegalArgumentException("Cannot call verifyNoInteractionsAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyNoInteractionsAfter(FIRST) or verifyNoInteractionsAfter(LAST).");
        }

        Integer objectIndexOfMock = mockMap.get(mock);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyNoInteractionsAfter(Object mock) for mock not in mocks!");
        }

        for (int i = objectIndexOfMock + 1; i < stoppingIndex; i++) {
            try {
                verifyNoInteractionLambda.run(mocks[i]);
            } catch (Exception e) {
                throw new RuntimeException(String.format("verifyNoInteractionLambda called with m%d throws an exception! Please check your verifyNoInteractionLambda and mocks.", i + 1), e);
            }
        }

        remainingVerifyIndex = this.mocks.length;
    }

    public static class Builder {

        private List<Object> mocks;
        private List<WhenLambda> whens;
        private List<VerifyLambda> verifies;

        private NoInteractionLambda verifyNoInteractionLambda;

        /**
         * Creates a builder for Mof.
         */
        public Builder() {
            mocks = new ArrayList<>();
            whens = new ArrayList<>();
            verifies = new ArrayList<>();
        }

        private Builder(List<Object> mocks, List<WhenLambda> whens, List<VerifyLambda> verifies) {
            this.mocks = mocks;
            this.whens = whens;
            this.verifies = verifies;
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
            if (m == null) {
                throw new IllegalArgumentException("Cannot add null Mock to Mof Builder!");
            }

            if (w == null) {
                throw new IllegalArgumentException("Cannot add null WhenLambda to Mof Builder!");
            }

            if (v == null) {
                throw new IllegalArgumentException("Cannot add null VerifyLambda to Mof Builder!");
            }

            mocks.add(m);
            whens.add(w);
            verifies.add(v);
            return this;
        }

        public Builder copy() {
            return new Builder(this.mocks, this.whens, this.verifies);
        }

        /**
         * Allows usage of verifyNoInteractionsTheRest and verifyNoInteractionsTheRestAfter.
         *
         * @param verifyNoInteractionLambda A Java Lambda. Example: "enableVerifyNoInteractions(mock -&gt; verifyNoInteractions(mock))"
         */
        public Builder enableVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) {
            this.verifyNoInteractionLambda = verifyNoInteractionLambda;
            return this;
        }

        /**
         * Returns a new Mof.
         *
         * @return MockCoach
         */
        public Mof build() {
            if (mocks.size() == 0) {
                throw new IllegalStateException("Must add at least one mock before calling build on Mof Builder!");
            }

            Mof mof = new Mof(
                    mocks.toArray(new Object[0]),
                    whens.toArray(new WhenLambda[0]),
                    verifies.toArray(new VerifyLambda[0])
            );
            mof.enableVerifyNoInteractions(verifyNoInteractionLambda);
            return mof;
        }
    }

    /**
     * Creates a builder for Mof.
     *
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }
}
