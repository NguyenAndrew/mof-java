package com.andyln;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class WhenLambdaTest {

    @Test
    void canThrowException() {
        String expectedExceptionMessage = "Expected Exception Message";

        VerifyLambda whenLambda = () -> {
            throw new Exception(expectedExceptionMessage);
        };

        Exception actualException = assertThrows(
                Exception.class,
                whenLambda::run
        );

        assertEquals(expectedExceptionMessage, actualException.getMessage());
    }

}