package com.health.bmi_checker.controller.ExceptionHandler;

public class DataNotFoundException extends RuntimeException {

    public DataNotFoundException(String message) {
        super(message);
    }
}
