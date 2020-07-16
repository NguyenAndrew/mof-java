package com.andyln;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class VerifyLambdaTest {

    @Test
    void canThrowException() {
        String expectedExceptionMessage = "Expected Exception Message";

        VerifyLambda verifyLambda = () -> {
            throw new Exception(expectedExceptionMessage);
        };

        Exception actualException = assertThrows(
                Exception.class,
                verifyLambda::run
        );

        assertEquals(expectedExceptionMessage, actualException.getMessage());
    }

}