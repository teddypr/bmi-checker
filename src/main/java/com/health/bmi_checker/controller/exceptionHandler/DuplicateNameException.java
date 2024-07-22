package com.health.bmi_checker.controller.exceptionHandler;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String message) {
        super(message);
    }
}
