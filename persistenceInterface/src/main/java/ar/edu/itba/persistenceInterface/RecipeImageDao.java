package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.recipe.RecipeImage;

import java.util.Optional;

public interface RecipeImageDao {

    Optional<RecipeImage> getImage(long imageId);

}
