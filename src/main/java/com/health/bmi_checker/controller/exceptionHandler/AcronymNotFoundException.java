package com.health.bmi_checker.controller.exceptionHandler;

public class AcronymNotFoundException extends RuntimeException {
    public AcronymNotFoundException(String message) {
        super(message);
    }
}
