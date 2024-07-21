package com.health.bmi_checker.controller.ExceptionHandler;

public class DuplicateNameException extends RuntimeException {
    public DuplicateNameException(String message) {
        super(message);
    }
}
