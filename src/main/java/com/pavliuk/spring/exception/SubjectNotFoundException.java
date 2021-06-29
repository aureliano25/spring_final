package com.pavliuk.spring.exception;

public class SubjectNotFoundException extends Exception {
    public SubjectNotFoundException() {
        super();
    }

    public SubjectNotFoundException(String message) {
        super(message);
    }
}
