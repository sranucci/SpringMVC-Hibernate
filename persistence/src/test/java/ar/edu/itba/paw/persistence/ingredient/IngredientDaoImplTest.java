package ar.edu.itba.paw.persistence.ingredient;

import ar.edu.itba.paw.models.ingredient.Ingredient;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.IngredientsDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;
import static org.junit.Assert.*;

import java.util.List;


@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateIngredientDaoImplTest.sql"})
public class IngredientDaoImplTest {

    @Autowired
    private IngredientsDao ingredientsDao;

    @Test
    public void testGetAllIngredients(){
        //retrieving the 2 ingredients that exist
        List<Ingredient> ingredients = ingredientsDao.getAllIngredients();
        assertEquals(2, ingredients.size());
    }

    @Test
    public void testGetAllIngredientsByName(){
        //retrieving the 2 ingredients that exist
        List<Ingredient> ingredients = ingredientsDao.getAllIngredientsByName(new String[]{"salt", "sugar"});
        assertEquals(2, ingredients.size());
        assertEquals(Integer.valueOf(1), ingredients.get(0).getIngredientId());
        assertEquals("salt", ingredients.get(0).getName());
        assertEquals("sugar", ingredients.get(1).getName());
        assertEquals(Integer.valueOf(2), ingredients.get(1).getIngredientId());
    }



}


