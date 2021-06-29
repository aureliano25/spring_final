package com.pavliuk.spring.exception;

public class TestNotFoundException extends Exception {
    public TestNotFoundException() {
        super();
    }

    public TestNotFoundException(String message) {
        super(message);
    }
}
