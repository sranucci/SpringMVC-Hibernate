package ar.edu.itba.paw.persistence.ingredient;

import ar.edu.itba.paw.models.ingredient.*;
import ar.edu.itba.persistenceInterface.IngredientsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Arrays;
import java.util.List;

@Repository
public class IngredientDaoImpl implements IngredientsDao {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<Ingredient> getAllIngredients() {
        return em.createQuery("SELECT i FROM Ingredient i", Ingredient.class)
                .getResultList();
    }

    @Override
    public List<Ingredient> getAllIngredientsByName(String[] ingredientNames) {
        return em.createQuery("SELECT data FROM Ingredient data WHERE data.name IN (:names)", Ingredient.class)
                .setParameter("names", Arrays.asList(ingredientNames))
                .getResultList();
    }
}




