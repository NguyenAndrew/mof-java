package com.andyln.mockcoachmethodable;

public class MockCoachStatic {

    public static VerifyAll verifyAll() {
        return new VerifyAll();
    }

    public static VerifyBefore verifyBefore(Object mock) {
        return new VerifyBefore(mock);
    }

    public static VerifyBeforeFirst verifyBeforeFirst() {
        return new VerifyBeforeFirst();
    }

    public static VerifyBeforeLast verifyBeforeLast() {
        return new VerifyBeforeLast();
    }

    public static VerifyNoInteractionsTheRest verifyNoInteractionsTheRest() {
        return new VerifyNoInteractionsTheRest();
    }

    public static VerifyNoInteractionsTheRestAfter verifyNoInteractionsTheRestAfter(Object mock) {
        return new VerifyNoInteractionsTheRestAfter(mock);
    }

    public static VerifyTheRest verifyTheRest() {
        return new VerifyTheRest();
    }

    public static VerifyThrough verifyThrough(Object mock) {
        return new VerifyThrough(mock);
    }

    public static VerifyThroughFirst verifyThroughFirst() {
        return new VerifyThroughFirst();
    }

    public static VerifyTheRestAfter verifyTheRestAfter(Object mock) {
        return new VerifyTheRestAfter(mock);
    }

    public static VerifyThroughLast verifyThroughLast() {
        return new VerifyThroughLast();
    }

    public static WhenAll whenAll() {
        return new WhenAll();
    }

    public static WhenBefore whenBefore(Object mock) {
        return new WhenBefore(mock);
    }

    public static WhenBeforeFirst whenBeforeFirst() {
        return new WhenBeforeFirst();
    }

    public static WhenBeforeLast whenBeforeLast() {
        return new WhenBeforeLast();
    }

    public static WhenTheRest whenTheRest() {
        return new WhenTheRest();
    }

    public static WhenTheRestAfter whenTheRestAfter(Object mock) {
        return new WhenTheRestAfter(mock);
    }

}
