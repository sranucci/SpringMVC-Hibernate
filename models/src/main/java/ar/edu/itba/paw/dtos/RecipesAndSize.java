package ar.edu.itba.paw.dtos;

import ar.edu.itba.paw.models.recipe.Recipe;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class RecipesAndSize {
    List<Recipe> recipeList;
    long totalRecipes;
}
