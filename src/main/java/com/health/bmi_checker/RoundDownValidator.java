package com.health.bmi_checker;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class RoundDownValidator implements ConstraintValidator<RoundDownConstraint, Double> {
    private int decimalPoint;

    @Override
    public void initialize(RoundDownConstraint constraintAnnotation) {
        this.decimalPoint = constraintAnnotation.decimalPoint();
    }

    @Override
    public boolean isValid(Double value, ConstraintValidatorContext context) {
        if (value == null) {
            return false;
        }
        // 小数点以下第一位までの桁数をチェック
        return Math.floor(value * 10) / 10 == value;
    }

}
