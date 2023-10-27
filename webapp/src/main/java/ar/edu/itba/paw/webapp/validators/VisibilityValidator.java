package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidVisibility;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class VisibilityValidator implements ConstraintValidator<ValidVisibility, String> {

    private static final String PATTERN = "^(Private|Public)$";

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        return s != null && s.matches(PATTERN);
    }
}
