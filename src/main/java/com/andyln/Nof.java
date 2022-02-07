package com.andyln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Nof {

    public static final AllOrRemaining ALL = AllOrRemaining.ALL;
    public static final AllOrRemaining REMAINING = AllOrRemaining.REMAINING;

    public static final FirstOrLast FIRST = FirstOrLast.FIRST;
    public static final FirstOrLast LAST = FirstOrLast.LAST;

    private Enum<?>[] mockEnums;
    private WhenLambda[] whenLambdas;
    private VerifyLambda[] verifyLambdas;

    private NoInteractionLambda verifyNoInteractionLambda;

    private Map<Enum<?>, Integer> mockEnumMap;

    private boolean containsMoreThanOneMockEnum;

    private boolean isMockEnumsInClosedCurve;

    private int remainingWhenIndex = 0;
    private int remainingVerifyIndex = 0;

    private Nof(Enum<?>[] mockEnums, WhenLambda[] whenLambdas, VerifyLambda[] verifyLambdas) {
        mockEnumMap = new HashMap<>();

        containsMoreThanOneMockEnum = mockEnums.length > 1;

        isMockEnumsInClosedCurve = mockEnums[0] == mockEnums[mockEnums.length - 1];

        int lengthOfMocksToCheck = isMockEnumsInClosedCurve && containsMoreThanOneMockEnum ? mockEnums.length - 1 : mockEnums.length;
        for (int i = 0; i < lengthOfMocksToCheck; i++) {
            Object potentiallyDuplicateMock = mockEnumMap.put(mockEnums[i], i);
            boolean isDuplicateMock = potentiallyDuplicateMock != null;
            if (isDuplicateMock) {
                throw new IllegalArgumentException(String.format("m%d cannot be the same as a previous mock in mocks!", i + 1));
            }
        }

        this.mockEnums = mockEnums;
        this.whenLambdas = whenLambdas;
        this.verifyLambdas = verifyLambdas;
    }

    /**
     * Runs ALL or REMAINING whens.
     *
     * @param aor ALL or REMAINING enum.
     * @throws IllegalArgumentException Not calling with ALL or REMAINING enum.
     */
    public void when(AllOrRemaining aor) {
        if (aor == AllOrRemaining.ALL) {
            for (int i = 0; i < this.mockEnums.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mockEnums.length;
            return;
        }

        if (aor == AllOrRemaining.REMAINING) {
            for (int i = remainingWhenIndex; i < this.mockEnums.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mockEnums.length;
            return;
        }

        throw new IllegalArgumentException("aor must be ALL or REMAINING.");
    }

    /**
     * Runs all whens up to, <STRONG>not</STRONG> including, the MockEnum.
     *
     * @param mockEnum Any MockEnum within MockEnums. Note: Excludes ambiguous first/last MockEnum in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with MockEnum not in MockEnums.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve {@code A -> B -> A}, when calling with A, do you mean the first or lack MockEnum? Instead of passing A, Use FIRST or LAST instead.
     */
    public void whenBefore(Enum<?> mockEnum) {
        if (mockEnum == FirstOrLast.FIRST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingWhenIndex = 1;
            return;
        }

        if (mockEnum == FirstOrLast.LAST) {
            int indexOfLastMock = this.mockEnums.length - 1;
            for (int i = 0; i < indexOfLastMock; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mockEnums.length;
            return;
        }

        if (containsMoreThanOneMockEnum && isMockEnumsInClosedCurve && mockEnum == mockEnums[0]) {
            throw new IllegalArgumentException("Cannot call whenBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenBefore(FIRST) or whenBefore(LAST).");
        }

        Integer objectIndexOfMock = mockEnumMap.get(mockEnum);

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
     * Runs all whens after, <STRONG>not</STRONG> including, the MockEnum.
     *
     * @param mockEnum Any MockEnum within MockEnums. Note: Excludes ambiguous first/last MockEnum in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with MockEnum not in MockEnums.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve {@code A -> B -> A}, when calling with A, do you mean the first or lack MockEnum? Instead of passing A, Use FIRST or LAST instead.
     */
    public void whenAfter(Enum<?> mockEnum) {
        if (mockEnum == FirstOrLast.FIRST) {
            for (int i = 1; i < this.mockEnums.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            remainingWhenIndex = this.mockEnums.length;
            return;
        }

        if (mockEnum == FirstOrLast.LAST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingWhenIndex = this.mockEnums.length;
            return;
        }

        if (containsMoreThanOneMockEnum && isMockEnumsInClosedCurve && mockEnum == mockEnums[0]) {
            throw new IllegalArgumentException("Cannot call whenAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use whenAfter(FIRST) or whenAfter(LAST).");
        }

        Integer objectIndexOfMock = mockEnumMap.get(mockEnum);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call whenAfter(Object mock) for mock not in mocks!");
        }

        for (int i = objectIndexOfMock + 1; i < this.mockEnums.length; i++) {
            try {
                whenLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
            }
        }

        remainingWhenIndex = this.mockEnums.length;
    }

    /**
     * Runs ALL or REMAINING verifies.
     *
     * @param aor ALL or REMAINING enum.
     * @throws IllegalArgumentException Not calling with ALL or REMAINING enum.
     */
    public void verify(AllOrRemaining aor) {
        if (aor == AllOrRemaining.ALL) {
            for (int i = 0; i < this.mockEnums.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mockEnums.length;
            return;
        }

        if (aor == AllOrRemaining.REMAINING) {
            for (int i = remainingVerifyIndex; i < this.mockEnums.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mockEnums.length;
            return;
        }

        throw new IllegalArgumentException("aor must be ALL or REMAINING.");
    }

    /**
     * Runs all verifies up to, and including, the MockEnum.
     *
     * @param mockEnum Any MockEnum within MockEnums. Note: Excludes ambiguous first/last MockEnum in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with MockEnum not in MockEnums.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve {@code A -> B -> A}, when calling with A, do you mean the first or lack MockEnum? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyThrough(Enum<?> mockEnum) {
        if (mockEnum == FirstOrLast.FIRST) {
            try {
                verifyLambdas[0].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", 1), e);
            }
            remainingVerifyIndex = 1;
            return;
        }

        if (mockEnum == FirstOrLast.LAST) {
            for (int i = 0; i < this.mockEnums.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mockEnums.length;
            return;
        }

        if (containsMoreThanOneMockEnum && isMockEnumsInClosedCurve && mockEnum == mockEnums[0]) {
            throw new IllegalArgumentException("Cannot call verifyThrough(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyThrough(FIRST) or verifyThrough(LAST).");
        }

        Integer objectIndexOfMock = mockEnumMap.get(mockEnum);

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
     * Runs all verifies up to, <STRONG>not</STRONG> including, the MockEnum.
     *
     * @param mockEnum Any MockEnum within MockEnums. Note: Excludes ambiguous first/last MockEnum in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with MockEnum not in MockEnums.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve {@code A -> B -> A}, when calling with A, do you mean the first or lack MockEnum? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyBefore(Enum<?> mockEnum) {
        if (mockEnum == FirstOrLast.FIRST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingVerifyIndex = 1;
            return;
        }

        if (mockEnum == FirstOrLast.LAST) {
            for (int i = 0; i < this.mockEnums.length - 1; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mockEnums.length;
            return;
        }

        if (containsMoreThanOneMockEnum && isMockEnumsInClosedCurve && mockEnum == mockEnums[0]) {
            throw new IllegalArgumentException("Cannot call verifyBefore(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyBefore(FIRST) or verifyBefore(LAST).");
        }

        Integer objectIndexOfMock = mockEnumMap.get(mockEnum);

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
     * Runs all verifies after, <STRONG>not</STRONG> including, the MockEnum
     *
     * @param mockEnum Any MockEnum within MockEnums. Note: Excludes ambiguous first/last MockEnum in a Simple Closed Curve (In case of ambiguity, use FIRST or LAST enum).
     * @throws IllegalArgumentException Calling with MockEnum not in MockEnums.
     *                                  Calling with ambiguous first or last mock. Example: In a Simple Closed Curve {@code A -> B -> A}, when calling with A, do you mean the first or lack MockEnum? Instead of passing A, Use FIRST or LAST instead.
     */
    public void verifyAfter(Enum<?> mockEnum) {
        if (mockEnum == FirstOrLast.FIRST) {
            for (int i = 1; i < this.mockEnums.length; i++) {
                try {
                    verifyLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
                }
            }
            remainingVerifyIndex = this.mockEnums.length;
            return;
        }

        if (mockEnum == FirstOrLast.LAST) {
            // Note: This flow exists, because it creates a better user experience when refactoring between simple closed and simple open curves.
            remainingVerifyIndex = this.mockEnums.length;
            return;
        }

        if (containsMoreThanOneMockEnum && isMockEnumsInClosedCurve && mockEnum == mockEnums[0]) {
            throw new IllegalArgumentException("Cannot call verifyAfter(Object mock) for ambiguous first/last mock in a simple closed curve! For mocks in a simple closed curve, use verifyAfter(FIRST) or verifyAfter(LAST).");
        }

        Integer objectIndexOfMock = mockEnumMap.get(mockEnum);

        if (objectIndexOfMock == null) {
            throw new IllegalArgumentException("Cannot call verifyAfter(Object mock) for mock not in mocks!");
        }

        for (int i = objectIndexOfMock + 1; i < this.mockEnums.length; i++) {
            try {
                verifyLambdas[i].run();
            } catch (Exception e) {
                throw new RuntimeException(String.format("v%d throws an exception! Please check your verifies.", i + 1), e);
            }
        }

        remainingVerifyIndex = this.mockEnums.length;
    }

    public static class Builder {

        private List<Enum<?>> mockEnums;
        private List<WhenLambda> whens;
        private List<VerifyLambda> verifies;

        private NoInteractionLambda verifyNoInteractionLambda;

        /**
         * Creates a builder for Nof.
         */
        public Builder() {
            mockEnums = new ArrayList<>();
            whens = new ArrayList<>();
            verifies = new ArrayList<>();
        }

        private Builder(List<Enum<?>> mockEnums, List<WhenLambda> whens, List<VerifyLambda> verifies) {
            this.mockEnums = mockEnums;
            this.whens = whens;
            this.verifies = verifies;
        }

        /**
         * Adds to end of builder.
         *
         * @param mE MockEnum
         * @param w When Lambda
         * @param v Verify Lambda
         * @return Builder
         */
        public Builder add(Enum<?> mE, WhenLambda w, VerifyLambda v) {
            if (mE == null) {
                throw new IllegalArgumentException("Cannot add null Mock to Mof Builder!");
            }

            if (w == null) {
                throw new IllegalArgumentException("Cannot add null WhenLambda to Mof Builder!");
            }

            if (v == null) {
                throw new IllegalArgumentException("Cannot add null VerifyLambda to Mof Builder!");
            }

            mockEnums.add(mE);
            whens.add(w);
            verifies.add(v);
            return this;
        }

        /**
         * Returns a copy of the builder for Nof.
         *
         * @return Builder
         */
        public Builder copy() {
            return new Builder(this.mockEnums, this.whens, this.verifies);
        }

        /**
         * Returns a new Nof.
         *
         * @return Nof
         */
        public Nof build() {
            if (mockEnums.size() == 0) {
                throw new IllegalStateException("Must add at least one mock before calling build on Mof Builder!");
            }

            return new Nof(
                    mockEnums.toArray(new Enum<?>[0]),
                    whens.toArray(new WhenLambda[0]),
                    verifies.toArray(new VerifyLambda[0])
            );
        }
    }

    /**
     * Creates a builder for Nof.
     *
     * @return Builder
     */
    public static Builder builder() {
        return new Builder();
    }
}
