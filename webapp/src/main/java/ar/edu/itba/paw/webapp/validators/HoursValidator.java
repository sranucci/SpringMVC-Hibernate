package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidHours;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HoursValidator implements ConstraintValidator<ValidHours, Integer> {


    @Override
    public boolean isValid(Integer integer, ConstraintValidatorContext constraintValidatorContext) {
        return integer == null || integer >= 0;
    }
}
