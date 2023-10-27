package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidMinutes;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class MinutesValidator implements ConstraintValidator<ValidMinutes, Integer> {


    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer == null || (integer >= 0 && integer <= 59);
    }
}
