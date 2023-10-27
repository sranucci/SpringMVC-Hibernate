package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidServings;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class ServingsValidator implements ConstraintValidator<ValidServings, Integer> {
    @Override
    public boolean isValid(Integer servings, ConstraintValidatorContext constraintValidatorContext) {
        return servings != null && servings > 0;
    }
}
