package ar.edu.itba.persistenceInterface;

public interface SavedRecipesDao {
    Boolean isRecipeSavedByUser(long userId, long recipeId);

    void saveRecipe(long userId, long recipeId);

    Boolean deleteSavedRecipe(long userId, long recipeId);

}
