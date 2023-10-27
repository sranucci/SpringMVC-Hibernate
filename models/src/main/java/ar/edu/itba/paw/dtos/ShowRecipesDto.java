package ar.edu.itba.paw.dtos;

import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.SortOptions;
import ar.edu.itba.paw.models.recipe.Recipe;
import lombok.Getter;
import ar.edu.itba.paw.enums.LanguagesForSort;


import java.util.List;

@Getter
public class ShowRecipesDto extends BaseShowRecipeDataDto {

    // sort parameters
    private AvailableDifficultiesForSort difficulty;
    private SortOptions sort;
    private List<String> selectedCategories;
    private String selectedIngredients;
    private LanguagesForSort language; //"en" or "es"

    // searchbar parameters
    private String searchBarQuery;


    public ShowRecipesDto(String headTitle, String pageTitle, List<Recipe> recipeList, long pageNumber, int pageSize, long totalRecipes, AvailableDifficultiesForSort difficulty, SortOptions sort,
                          List<String> selectedCategories, String selectedIngredients, String searchBarQuery, LanguagesForSort language) {
        super(headTitle, pageTitle, recipeList, pageNumber, pageSize, totalRecipes);
        this.difficulty = difficulty;
        this.sort = sort;
        this.selectedCategories = selectedCategories;
        this.selectedIngredients = selectedIngredients;
        this.searchBarQuery = searchBarQuery;
        this.language=language;
    }
}
