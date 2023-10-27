package ar.edu.itba.paw.servicesInterface.ingredient;

import ar.edu.itba.paw.models.ingredient.Ingredient;

import java.util.List;

public interface IngredientsService {
    List<Ingredient> getAllIngredients();

    List<Ingredient> getAllIngredientsByName(String[] ingredientNames);



}
