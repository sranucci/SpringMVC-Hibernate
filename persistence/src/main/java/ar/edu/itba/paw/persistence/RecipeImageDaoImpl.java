package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.recipe.RecipeImage;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class RecipeImageDaoImpl implements ar.edu.itba.persistenceInterface.RecipeImageDao {

    @PersistenceContext
    private EntityManager em;


    @Override
    public Optional<RecipeImage> getImage(long imageId) {
        return Optional.ofNullable(em.find(RecipeImage.class, imageId));
    }
}
