package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidQuantityArray;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;

public class QuantityArrayValidator implements ConstraintValidator<ValidQuantityArray, Float[]> {


    @Override
    public boolean isValid(Float[] numbers, ConstraintValidatorContext constraintValidatorContext) {
        if (numbers == null || numbers.length == 0) {
            return false;
        }

        return Arrays.stream(numbers).filter(Objects::nonNull).allMatch(num -> num > 0);

    }
}
