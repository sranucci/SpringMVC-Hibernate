package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.LanguagesForSort;
import ar.edu.itba.paw.enums.ShowRecipePages;
import ar.edu.itba.paw.enums.SortOptions;
import ar.edu.itba.paw.models.category.RecipeCategory;
import ar.edu.itba.paw.models.ingredient.RecipeIngredient;
import ar.edu.itba.paw.models.recipe.RecipeImage;
import ar.edu.itba.paw.models.recipe.Recipe;

import java.util.List;
import java.util.Optional;

public interface RecipeDao {
    //Notar que no pongo id de dificultad porque esto es lo que se muestra!, importa para crear en base de datos
    //me interesa mostrar la dificultad
    Recipe create(String language, String title, String description, long userId, boolean isPrivate, int totalMinutes, long difficultyId, int servings, String[] instructions);

    Optional<Recipe> getRecipe(long recipeId);

    Optional<Recipe> getRecipe(long recipeId, Optional<Integer> commentsToBring);


    void removeRecipe(Recipe recipe);

    boolean removeRecipe(long recipeId, long userId);

    List<Recipe> getRecipesByFilter(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients, List<Integer> categories,
                                    Optional<SortOptions> sort, Optional<String> searchQuery, ShowRecipePages pageToShow,
                                    Optional<Long> userId, Optional<Long> page, Optional<Integer> limit, Optional<LanguagesForSort> language);

    long getTotalNumberRecipesByFilterForPagination(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients, List<Integer> categories, Optional<SortOptions> sort, Optional<String> query, ShowRecipePages pageToShow, Optional<Long> userId, Optional<Long> page, Optional<Integer> pageSize, Optional<LanguagesForSort> language);


    boolean updateRecipe(String language, String title, String description, boolean isPrivate, int totalMinutes, long difficultyId,
                         int servings, String[] instructions, List<RecipeIngredient> ings,
                         List<RecipeCategory> cats, List<RecipeImage> imgs, RecipeImage mainImage , Recipe recipe);

    List<Recipe> getRecipesForFeed(Long currentUserId, Optional<Integer> page, Optional<Integer> pageSize);

    long getNumberOfRecipesForFeed(Long currentUserId);

    List<Long> getRecipesLiked(Optional<Long> currentUserId);

    List<Long> getRecipesDisliked(Optional<Long> currentUserId);

    List<Recipe> getRecipesForProfile(Long currentUserId, Optional<Integer> page, Optional<Integer> pageSize);

    long getNumberOfRecipesForProfile(Long currentUserId);
}
