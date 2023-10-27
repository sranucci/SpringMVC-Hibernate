package ar.edu.itba.paw.services;

import ar.edu.itba.paw.servicesInterface.SavedRecipesService;
import ar.edu.itba.persistenceInterface.SavedRecipesDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SavedRecipesServiceImpl implements SavedRecipesService {
    private final SavedRecipesDao savedRecipesDao;

    @Autowired
    public SavedRecipesServiceImpl(final SavedRecipesDao savedRecipesDao) {
        this.savedRecipesDao = savedRecipesDao;
    }


    @Transactional(readOnly = true)
    @Override
    public Boolean isRecipeSavedByUser(long userId, long recipeId) {
        return savedRecipesDao.isRecipeSavedByUser(userId, recipeId);
    }

    @Transactional
    @Override
    public void saveRecipe(long userId, long recipeId) {
        savedRecipesDao.saveRecipe(userId, recipeId);
    }

    @Transactional
    @Override
    public void deleteSavedRecipe(long userId, long recipeId) {
        savedRecipesDao.deleteSavedRecipe(userId, recipeId);
    }
}
