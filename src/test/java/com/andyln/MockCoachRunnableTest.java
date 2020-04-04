package com.andyln;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class MockCoachRunnableTest {

    @Test
    void mockCoachRunnableImplementation_canThrowException() {
        String expectedExceptionMessage = "Expected Exception Message";

        MockCoachRunnable mockCoachRunnable = () -> {
            throw new Exception(expectedExceptionMessage);
        };

        Exception actualException = assertThrows(
                Exception.class,
                mockCoachRunnable::run
        );

        assertEquals(expectedExceptionMessage, actualException.getMessage());

    }
}