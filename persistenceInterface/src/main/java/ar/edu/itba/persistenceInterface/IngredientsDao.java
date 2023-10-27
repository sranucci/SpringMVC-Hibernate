package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.ingredient.Ingredient;

import java.util.List;

public interface IngredientsDao {

    List<Ingredient> getAllIngredients();


    List<Ingredient> getAllIngredientsByName(String[] ingredientNames);

}
