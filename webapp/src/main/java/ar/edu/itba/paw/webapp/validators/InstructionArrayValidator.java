package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidInstructionArray;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.Objects;


@Component
public class InstructionArrayValidator implements ConstraintValidator<ValidInstructionArray, String[]> {

    @Override
    public boolean isValid(String[] strings, ConstraintValidatorContext constraintValidatorContext) {
        if (strings == null || strings.length == 0) {
            return false;
        }
        //using database numbers, check it doesnt has the serializing token!.
        return Arrays.stream(strings).filter(Objects::nonNull).allMatch(e -> e.length() > 0 && e.length() < 4096 && !e.contains("#"));
    }
}
