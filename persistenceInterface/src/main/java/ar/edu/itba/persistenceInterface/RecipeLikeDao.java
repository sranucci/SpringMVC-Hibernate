package ar.edu.itba.persistenceInterface;

public interface RecipeLikeDao {
    Boolean isRecipeLikedById(long userId, long recipeId);

    Boolean isRecipeDislikedById(long userId, long recipeId);

    void makeRecipeLiked(long userId, long recipeId);

    void makeRecipeDisliked(long userId, long recipeId);

    void removeRecipeRating(long userId, long recipeId);

}
