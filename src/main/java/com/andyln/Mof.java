package com.andyln;

import java.util.ArrayList;
import java.util.List;

public class Mof {

    private Mof() { }

    public void when(Aor mocks) {}
    public void whenBefore(Object mock) {}
    public void whenAfter(Object mock) {}

    public void verify(Aor mocks) {}
    public void verifyThrough(Object mock) {}
    public void verifyBefore(Object mock) {}
    public void verifyAfter(Object mock) {}

    public void verifyNoInteractions(Aor mocks) {}
    public void verifyNoInteractionsAfter(Object mock) {}

    public static class Builder {

        public Builder() { }

        public Mof.Builder add(Object m, WhenLambda w, VerifyLambda v) { return null; }

        public Mof.Builder enableVerifyNoInteractions(NoInteractionLambda verifyNoInteractionLambda) { return this; }

        public MockCoach build() { return null; }
    }

    public static Builder builder() {
        return new Builder();
    }

}
