
package ar.edu.itba.paw.persistence;


import ar.edu.itba.paw.models.recipe.RecipeImage;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.RecipeImageDao;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import java.util.Optional;

import static org.junit.Assert.*;
import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:recipeImageDaoImplTest.sql"})
public class RecipeImageDaoImpTest {

    @Autowired
    private RecipeImageDao recipeImageDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testGetImage(){
        Optional<RecipeImage> ri = recipeImageDao.getImage(RECIPE_ID);
        assertTrue(ri.isPresent());
    }
    @Test
    public void testGetImageWhenRecipeIdDoesNotExist(){
        Optional<RecipeImage> ri = recipeImageDao.getImage(0);
        assertFalse(ri.isPresent());
    }


}
