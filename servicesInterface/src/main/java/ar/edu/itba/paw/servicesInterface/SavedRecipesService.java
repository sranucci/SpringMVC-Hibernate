package ar.edu.itba.paw.servicesInterface;

public interface SavedRecipesService {
    Boolean isRecipeSavedByUser(long userId, long recipeId);
    void deleteSavedRecipe(long userId, long recipeId);
    void saveRecipe(long userId, long recipeId);
}
