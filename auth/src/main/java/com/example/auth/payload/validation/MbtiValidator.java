package com.example.auth.payload.validation;

import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

import com.example.auth.domain.Mbti;

public class MbtiValidator implements ConstraintValidator<ValidMbti, Mbti> {

    @Override
    public boolean isValid(Mbti mbti, ConstraintValidatorContext context) {
        if (mbti == null) {
            return false;
        }

        for (Mbti name : Mbti.values()) {
            if (mbti.name().equals(name)) {
                return true;
            }
        }
        return false;
    }
}