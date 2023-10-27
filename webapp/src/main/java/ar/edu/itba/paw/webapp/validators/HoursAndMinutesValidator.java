package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.webapp.annotations.ValidHoursAndMinutes;
import ar.edu.itba.paw.webapp.forms.AbstractRecipeFormTemplate;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class HoursAndMinutesValidator implements ConstraintValidator<ValidHoursAndMinutes, AbstractRecipeFormTemplate> {
    @Override
    public boolean isValid(AbstractRecipeFormTemplate recipeForm, ConstraintValidatorContext constraintValidatorContext) {
        if (recipeForm == null || (recipeForm.getTotalHours() == null && recipeForm.getTotalMinutes() == null))
            return false;
        if (recipeForm.getTotalHours() == null && recipeForm.getTotalMinutes() != null && recipeForm.getTotalMinutes() > 0)
            return true;
        if (recipeForm.getTotalHours() != null && recipeForm.getTotalHours() > 0 && recipeForm.getTotalMinutes() == null) {
            return true;
        }
        return recipeForm.getTotalHours() > 0 || recipeForm.getTotalMinutes() > 0;

    }
}
