package com.andyln.mockcoachmethodable;

class MethodableState {

    private static RuntimeException exception = null;
    private static final String EXCEPTION_MESSAGE = "Missing .in(MockCoach) at ";

    static void clear() {
        exception = null;
    }

    static String getExceptionMessage() {
        return EXCEPTION_MESSAGE + exception.getStackTrace()[2].toString();
    }

    static void inProgress() {
        if (exception != null) {
            RuntimeException thrownException = new RuntimeException(MethodableState.getExceptionMessage());
            MethodableState.clear();
            throw thrownException;
        }

        exception = new RuntimeException();
    }

}
