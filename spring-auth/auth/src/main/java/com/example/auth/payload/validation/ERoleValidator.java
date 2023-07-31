package com.example.auth.payload.validation;

import com.example.auth.domain.ERole;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class ERoleValidator implements ConstraintValidator<ValidERole, ERole> {
    @Override
    public boolean isValid(ERole value, ConstraintValidatorContext context) {
        return value != null && (value == ERole.ROLE_USER || value == ERole.ROLE_ADMIN);
    }
}
