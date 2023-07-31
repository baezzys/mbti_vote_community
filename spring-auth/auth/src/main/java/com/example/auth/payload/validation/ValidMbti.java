package com.example.auth.payload.validation;

import jakarta.validation.Constraint;
import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = {MbtiValidator.class})
@Documented
public @interface ValidMbti {
    String message() default "Invalid MBTI type";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default {};
}

