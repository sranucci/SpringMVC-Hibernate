
package ar.edu.itba.paw.persistence;
import ar.edu.itba.paw.models.recipe.SavedRecipe;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.SavedRecipesDao;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;
import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;


import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:savedRecipesDaoImplTest.sql"})
public class SavedRecipesDaoImplTest{

    @Autowired
    private SavedRecipesDao savedRecipesDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testIsRecipeSavedByUser() {
        boolean isSaved = savedRecipesDao.isRecipeSavedByUser(USER_ID, RECIPE_ID);
        assertTrue(isSaved);
    }

    @Test
    public void testSaveRecipe() {
        savedRecipesDao.saveRecipe(USER_ID_2, RECIPE_ID_2);
        assertNotNull(em.find(SavedRecipe.class, new SavedRecipe.SavedRecipeId(RECIPE_ID_2, USER_ID_2)));
    }

    @Test
    public void testDeleteSavedRecipe() {
        Boolean wasDeleted = savedRecipesDao.deleteSavedRecipe(USER_ID, RECIPE_ID);
        assertTrue( wasDeleted);
    }

}

