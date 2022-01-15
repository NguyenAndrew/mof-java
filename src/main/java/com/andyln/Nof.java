package com.andyln;

public class Nof {
    private Nof(Enum<?> mockMarkers, WhenLambda[] whenLambdas, VerifyLambda[] verifyLambda) {}

    public void when(AllOrRemaining mocks) {}
    public void whenBefore(Enum<?> mockMarker) {}
    public void whenAfter(Enum<?> mockMarker) {}

    public void verify(AllOrRemaining mocks) {}
    public void verifyThrough(Enum<?> mockMarker) {}
    public void verifyBefore(Enum<?> mockMarker) {}
    public void verifyAfter(Enum<?> mockMarker) {}

    public static class Builder {

        public Builder() { }

        public Builder add(Enum<?> mm, WhenLambda w, VerifyLambda v) { return null; }

        public MockCoach build() { return null; }
    }

    public static Builder builder() {
        return new Builder();
    }
}
