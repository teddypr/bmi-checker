package com.health.bmi_checker.controller.validationConstraint;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = RoundDownValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface RoundDownConstraint {

    String message() default "小数点第 {decimalPoint} 位までを入力してください";

    int decimalPoint() default 1;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
