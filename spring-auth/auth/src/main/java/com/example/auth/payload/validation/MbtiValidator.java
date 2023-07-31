package com.example.auth.payload.validation;

import com.example.auth.domain.Mbti;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class MbtiValidator implements ConstraintValidator<ValidMbti, Mbti> {

    @Override
    public boolean isValid(Mbti mbti, ConstraintValidatorContext context) {
        if (mbti == null) {
            return false;
        }
        // Validate that the input value matches one of the MBTI enum constants
        for (Mbti name : Mbti.values()) {
            if (mbti.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
