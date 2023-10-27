package ar.edu.itba.paw.servicesInterface;

import ar.edu.itba.paw.dtos.RecipesAndSize;
import ar.edu.itba.paw.dtos.UploadedRecipeFormDto;
import ar.edu.itba.paw.enums.AvailableDifficultiesForSort;
import ar.edu.itba.paw.enums.ShowRecipePages;
import ar.edu.itba.paw.enums.SortOptions;
import ar.edu.itba.paw.models.recipe.RecipeImage;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.enums.LanguagesForSort;
import ar.edu.itba.paw.servicesInterface.exceptions.RecipeNotFoundException;

import java.util.List;
import java.util.Optional;

public interface RecipeService {

    boolean updateRecipe(UploadedRecipeFormDto rf, long userId, long recipeId);

    Optional<RecipeImage> getImage(long imageId);

    void removeRecipeNotifyingUser(long recipeId, String deletionMotive) throws RecipeNotFoundException;

    long create(UploadedRecipeFormDto rf, long userId);

    boolean removeRecipe(long recipeId, long userId);

    Optional<Recipe> getRecipe(long recipeId);

    Optional<Recipe> recoverUserRecipe(long recipeId, long userId);

    Optional<Recipe> getFullRecipe(long recipeId, Optional<Integer> commentsToBring);

    RecipesAndSize getRecipesByFilter(Optional<AvailableDifficultiesForSort> difficulty, Optional<String> ingredients, List<Integer> categories, Optional<SortOptions> sort, Optional<String> query, ShowRecipePages pageToShow, Optional<Long> userId, Optional<Long> page, Optional<Integer> pageSize, Optional<LanguagesForSort> language);

    RecipesAndSize getRecipesForFeed(Long currentUserId, Optional<Integer> page, Optional<Integer> pageSize);

    List<Long> getRecipesLiked(Optional<Long> currentUserId);

    List<Long> getRecipesDisliked(Optional<Long> currentUserId);

    RecipesAndSize getRecipesForProfile(Long userId, Optional<Integer> page, Optional<Integer> pageSize);
}
