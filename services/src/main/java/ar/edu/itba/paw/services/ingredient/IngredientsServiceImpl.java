package ar.edu.itba.paw.services.ingredient;

import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.servicesInterface.ingredient.IngredientsService;
import ar.edu.itba.persistenceInterface.IngredientsDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
public class IngredientsServiceImpl implements IngredientsService {

    private final IngredientsDao ingredientsDao;

    @Autowired
    public IngredientsServiceImpl(final IngredientsDao ingredientsDao) {
        this.ingredientsDao = ingredientsDao;
    }


    @Transactional(readOnly = true)
    @Override
    public List<Ingredient> getAllIngredients() {
        return ingredientsDao.getAllIngredients();
    }


    @Transactional
    @Override
    public List<Ingredient> getAllIngredientsByName(String[] ingredientNames){
        return ingredientsDao.getAllIngredientsByName(ingredientNames);
    }

}
