package com.health.bmi_checker.controller.exceptionHandler;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
