package ar.edu.itba.paw.servicesInterface;

public interface RecipeLikeService {
    Boolean isRecipeLikedById(long userId, long recipeId);
    Boolean isRecipeDislikedById(long userId, long recipeId);

    void makeRecipeLiked(long userId, long recipeId);

    void makeRecipeDisliked(long userId, long recipeId);

    void removeRecipeRating(long userId, long recipeId);
}
