package com.andyln.mockcoachmethodable;

import com.andyln.MockCoach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static com.andyln.mockcoachmethodable.MockCoachStatic.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class MockCoachStaticTest {

    private static MockCoach mockCoach = mock(MockCoach.class);

    private static Object mock = mock(Object.class);

    @Nested
    class VerifyAll {

        @Test
        public void success() {
            verifyAll().in(mockCoach);
            verify(mockCoach).verifyAll();
        }

    }

    @Nested
    class VerifyBefore {

        @Test
        public void success() {
            verifyBefore(mock).in(mockCoach);
            verify(mockCoach).verifyBefore(mock);
        }

    }

    @Nested
    class VerifyBeforeFirst {

        @Test
        public void success() {
            verifyBeforeFirst().in(mockCoach);
            verify(mockCoach).verifyBeforeFirst();
        }

    }

    @Nested
    class VerifyBeforeLast {

        @Test
        public void success() {
            verifyBeforeLast().in(mockCoach);
            verify(mockCoach).verifyBeforeLast();
        }

    }

    @Nested
    class VerifyNoInteractionsTheRest {

        @Test
        public void success() {
            verifyNoInteractionsTheRest().in(mockCoach);
            verify(mockCoach).verifyNoInteractionsTheRest();
        }

    }

    @Nested
    class VerifyNoInteractionsTheRestAfter {

        @Test
        public void success() {
            verifyNoInteractionsTheRestAfter(mock).in(mockCoach);
            verify(mockCoach).verifyNoInteractionsTheRestAfter(mock);
        }

    }

    @Nested
    class VerifyTheRest {

        @Test
        public void success() {
            verifyTheRest().in(mockCoach);
            verify(mockCoach).verifyTheRest();
        }

    }

    @Nested
    class VerifyTheRestAfter {

        @Test
        public void success() {
            verifyTheRestAfter(mock).in(mockCoach);
            verify(mockCoach).verifyTheRestAfter(mock);
        }

    }

    @Nested
    class VerifyThrough {

        @Test
        public void success() {
            verifyThrough(mock).in(mockCoach);
            verify(mockCoach).verifyThrough(mock);
        }

    }

    @Nested
    class VerifyThroughFirst {

        @Test
        public void success() {
            verifyThroughFirst().in(mockCoach);
            verify(mockCoach).verifyThroughFirst();
        }

    }

    @Nested
    class VerifyThroughLast {

        @Test
        public void success() {
            verifyThroughLast().in(mockCoach);
            verify(mockCoach).verifyThroughLast();
        }

    }

    @Nested
    class WhenAll {

        @Test
        public void success() {
            whenAll().in(mockCoach);
            verify(mockCoach).whenAll();
        }

    }

    @Nested
    class WhenBefore {

        @Test
        public void success() {
            whenBefore(mock).in(mockCoach);
            verify(mockCoach).whenBefore(mock);
        }

    }

    @Nested
    class WhenBeforeFirst {

        @Test
        public void success() {
            whenBeforeFirst().in(mockCoach);
            verify(mockCoach).whenBeforeFirst();
        }

    }

    @Nested
    class WhenBeforeLast {

        @Test
        public void success() {
            whenBeforeLast().in(mockCoach);
            verify(mockCoach).whenBeforeLast();
        }

    }

    @Nested
    class WhenTheRest {

        @Test
        public void success() {
            whenTheRest().in(mockCoach);
            verify(mockCoach).whenTheRest();
        }

    }

    @Nested
    class WhenTheRestAfter {

        @Test
        public void success() {
            whenTheRestAfter(mock).in(mockCoach);
            verify(mockCoach).whenTheRestAfter(mock);
        }

    }

}