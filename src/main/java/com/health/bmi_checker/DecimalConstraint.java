package com.health.bmi_checker;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = DecimalValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface DecimalConstraint {
    String message() default "小数点以下第一位までで入力してください";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
