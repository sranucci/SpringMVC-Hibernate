package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.recipe.SavedRecipe;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.persistenceInterface.SavedRecipesDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class SavedRecipeDaoImpl implements SavedRecipesDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public Boolean isRecipeSavedByUser(long userId, long recipeId) {
        Optional<SavedRecipe> savedRecipe = Optional.ofNullable(em.find(SavedRecipe.class, new SavedRecipe.SavedRecipeId(recipeId, userId)));
        return savedRecipe.isPresent();
    }

    @Override
    public void saveRecipe(long userId, long recipeId) {
        final SavedRecipe savedRecipe = new SavedRecipe(em.getReference(Recipe.class, recipeId), em.getReference(User.class, userId));
        em.persist(savedRecipe);
    }

    @Override
    public Boolean deleteSavedRecipe(long userId, long recipeId) {
        SavedRecipe savedRecipe = em.find(SavedRecipe.class, new SavedRecipe.SavedRecipeId(recipeId, userId));
        if (isRecipeSavedByUser(userId, recipeId)) {
            em.remove(savedRecipe);
            return true;
        } else {
            return false;
        }
    }
}
