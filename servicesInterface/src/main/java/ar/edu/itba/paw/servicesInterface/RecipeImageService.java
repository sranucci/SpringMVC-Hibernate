package ar.edu.itba.paw.servicesInterface;

import ar.edu.itba.paw.models.recipe.RecipeImage;

import java.util.Optional;

public interface RecipeImageService {

    Optional<RecipeImage> getImage(long imageId);

}
