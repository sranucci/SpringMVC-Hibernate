package ar.edu.itba.paw.webapp.validators;

import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.servicesInterface.ingredient.IngredientsService;
import ar.edu.itba.paw.webapp.annotations.ValidIngredientArray;
import org.springframework.beans.factory.annotation.Autowired;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;

public class IngredientArrayValidator implements ConstraintValidator<ValidIngredientArray, String[]> {

    private HashSet<String> set;

    @Autowired
    public IngredientArrayValidator(IngredientsService is) {
        List<Ingredient> l = is.getAllIngredients();
        set = new HashSet<>();
        l.forEach(i -> set.add(i.getName()));
    }

    @Override
    public boolean isValid(String[] ingredients, ConstraintValidatorContext constraintValidatorContext) {
        if (ingredients == null || ingredients.length == 0) {
            return false;
        }
        return Arrays.stream(ingredients).filter(Objects::nonNull).allMatch(set::contains);
    }
}
