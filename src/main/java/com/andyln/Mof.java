package com.andyln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mof {

    public static final AllOrRemaining ALL = AllOrRemaining.ALL;
    public static final AllOrRemaining REMAINING = AllOrRemaining.REMAINING;

    private Object[] mocks;
    private WhenLambda[] whenLambdas;
    private VerifyLambda[] verifyLambdas;

    private NoInteractionLambda verifyNoInteractionLambda;

    private Map<Object, Integer> mockMap;

    private boolean containsMoreThanOneMock;

    private boolean isMocksInCircleChain;

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

    public void when(AllOrRemaining aor) {
        if (aor == AllOrRemaining.ALL) {
            for (int i = 0; i < this.mocks.length; i++) {
                try {
                    whenLambdas[i].run();
                } catch (Exception e) {
                    throw new RuntimeException(String.format("w%d throws an exception! Please check your whens.", i + 1), e);
                }
            }
            return;
        }

        System.out.println("Remaining mocks");
    }

    public void whenBefore(Object mock) {
    }

    public void whenAfter(Object mock) {
    }

    public void verify(AllOrRemaining mocks) {
    }

    public void verifyThrough(Object mock) {
    }

    public void verifyBefore(Object mock) {
    }

    public void verifyAfter(Object mock) {
    }

    private Mof enableVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) {
        this.verifyNoInteractionLambda = verifyNoInteractionLambda;
        return this;
    }

    public void verifyNoInteractions(AllOrRemaining mocks) {
    }

    public void verifyNoInteractionsAfter(Object mock) {
    }

    public static class Builder {

        private List<Object> mocks;
        private List<WhenLambda> whens;
        private List<VerifyLambda> verifies;

        private NoInteractionLambda verifyNoInteractionLambda;

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

        public Builder enableVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) {
            this.verifyNoInteractionLambda = verifyNoInteractionLambda;
            return this;
        }

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

    public static Builder builder() {
        return new Builder();
    }

}
