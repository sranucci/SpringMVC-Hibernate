package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.enums.AvailableDifficulties;
import ar.edu.itba.paw.servicesInterface.RecipeService;
import ar.edu.itba.paw.webapp.annotations.DifficultyIdValid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;


@Component
public class DifficultyIdValidator implements ConstraintValidator<DifficultyIdValid, Long> {

    private final AvailableDifficulties[] difficulties;

    public DifficultyIdValidator() {
        difficulties = AvailableDifficulties.values();
    }

    @Override
    public boolean isValid(Long id, ConstraintValidatorContext constraintValidatorContext) {
        if (id == null || id <= 0)
            return false;
        for (AvailableDifficulties difficulty : difficulties) {
            if (difficulty.getDifficultyId() == id) {
                return true;
            }
        }
        return false;
    }
}
