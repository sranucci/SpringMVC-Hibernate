package ar.edu.itba.paw.services;

import ar.edu.itba.paw.models.recipe.RecipeImage;
import ar.edu.itba.paw.servicesInterface.RecipeImageService;
import ar.edu.itba.persistenceInterface.RecipeImageDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class RecipeImageServiceImpl implements RecipeImageService {
    private final RecipeImageDao recipeImageDao;

    @Autowired
    public RecipeImageServiceImpl(final RecipeImageDao recipeImageDao) {
        this.recipeImageDao = recipeImageDao;
    }


    @Transactional(readOnly = true)
    @Override
    public Optional<RecipeImage> getImage(long imageId) {
        return recipeImageDao.getImage(imageId);
    }

}
