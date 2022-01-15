package com.andyln;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Mof {

    private Object[] mocks;
    private WhenLambda[] whenLambdas;
    private VerifyLambda[] verifyLambdas;

    private NoInteractionLambda verifyNoInteractionLambda;

    private Map<Object, Integer> mockMap;

    private boolean containsMoreThanOneMock;

    private boolean isMocksInCircleChain;

    private Mof(Object[] mocks, WhenLambda[] whenLambdas, VerifyLambda[] verifyLambdas) {
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
            Object potentiallyDuplicateMock = mockMap.put(mocks[i], i);
            boolean isDuplicateMock = potentiallyDuplicateMock != null;
            if (isDuplicateMock) {
                throw new IllegalArgumentException(String.format("m%d cannot be the same as a previous mock in mocks!", i + 1));
            }
        }
    }

    public void when(Aor mocks) {
    }

    public void whenBefore(Object mock) {
    }

    public void whenAfter(Object mock) {
    }

    public void verify(Aor mocks) {
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

    public void verifyNoInteractions(Aor mocks) {
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
