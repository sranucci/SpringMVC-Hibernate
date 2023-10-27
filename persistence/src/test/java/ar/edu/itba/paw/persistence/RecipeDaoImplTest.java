package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.RecipeDao;
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
import java.util.List;
import java.util.Optional;

import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;
import static org.junit.Assert.*;



@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:RecipeDaoImplTest.sql"})
public class RecipeDaoImplTest {

    @Autowired
    private RecipeDao recipeDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testCreate() {
        Recipe recipe = recipeDao.create(LANGUAGE_EN, RECIPE_TITLE, DESCRIPTION, USER_ID, IS_PRIVATE, TOTAL_MINUTES, DIFFICULTY, SERVINGS, INSTRUCTIONS_ARRAY );
        assertNotNull(recipe);
        assertEquals(RECIPE_ID, recipe.getRecipeId());
        assertEquals(LANGUAGE_EN, recipe.getLanguage());
        assertEquals(RECIPE_TITLE, recipe.getTitle());
        assertEquals(DESCRIPTION, recipe.getDescription());
    }

    @Test
    public void testRemoveRecipe(){
        recipeDao.removeRecipe(em.find(Recipe.class, RECIPE_ID));
        assertNull(em.find(Recipe.class, RECIPE_ID));

    }


    @Test
    public void testGetRecipeById(){
        Optional<Recipe> recipe = recipeDao.getRecipe(RECIPE_ID);

        assertTrue(recipe.isPresent());
        assertEquals(RECIPE_TITLE, recipe.get().getTitle());
        assertEquals(DESCRIPTION, recipe.get().getDescription());
        assertEquals(USER_ID, recipe.get().getUser().getId());
        assertEquals(IS_PRIVATE, recipe.get().getIsPrivate());
        assertEquals(MINUTES, recipe.get().getMinutes());
        assertEquals(HOURS, recipe.get().getHours());
        assertEquals(DIFFICULTY, recipe.get().getDifficulty());
        assertEquals(SERVINGS, recipe.get().getServings());
    }

    @Test
    public void testGetRecipeByIdWhenRecipeDoesNotExist(){
        Optional<Recipe> recipe = recipeDao.getRecipe(NON_EXISTENT_RECIPE_ID);
        assertFalse(recipe.isPresent());
    }

    @Test
    public void testGetRecipesForFeed(){
        List<Recipe> recipes = recipeDao.getRecipesForFeed(USER_ID_2, Optional.of(1), Optional.of(9));
        assertEquals(1, recipes.size());
        Recipe recipe = recipes.get(0);
        assertEquals(RECIPE_TITLE, recipe.getTitle());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(USER_ID, recipe.getUser().getId());
        assertEquals(IS_PRIVATE, recipe.getIsPrivate());
        assertEquals(MINUTES, recipe.getMinutes());
        assertEquals(HOURS, recipe.getHours());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(SERVINGS, recipe.getServings());

    }

    @Test
    public void testGetNumberOfRecipesForFeed(){
        assertEquals(1L, recipeDao.getNumberOfRecipesForFeed(USER_ID_2));
    }


    @Test
    public void testGetRecipesForProfile(){
        List<Recipe> recipes = recipeDao.getRecipesForProfile(USER_ID, Optional.of(1), Optional.of(9));
        assertEquals(1, recipes.size());
        Recipe recipe = recipes.get(0);
        assertEquals(RECIPE_TITLE, recipe.getTitle());
        assertEquals(DESCRIPTION, recipe.getDescription());
        assertEquals(USER_ID, recipe.getUser().getId());
        assertEquals(IS_PRIVATE, recipe.getIsPrivate());
        assertEquals(MINUTES, recipe.getMinutes());
        assertEquals(HOURS, recipe.getHours());
        assertEquals(DIFFICULTY, recipe.getDifficulty());
        assertEquals(SERVINGS, recipe.getServings());

    }

    @Test
    public void testGetNumberOfRecipesForProfile(){
        assertEquals(1L, recipeDao.getNumberOfRecipesForProfile(USER_ID));
    }

    @Test
    public void testGetRecipesForProfileWhenUSerHasNoRecipes(){
        List<Recipe> recipes = recipeDao.getRecipesForProfile(3L, Optional.of(1), Optional.of(9));
        assertEquals(0, recipes.size());
    }

    @Test
    public void testGetNumberOfRecipesForProfileWhenUSerHasNoRecipes(){
        assertEquals(0, recipeDao.getNumberOfRecipesForProfile(3L));
    }

    @Test
    public void getRecipesLiked(){
        assertEquals(1, recipeDao.getRecipesLiked(Optional.of(USER_ID)).size());
    }

    @Test
    public void getRecipesLikedWhenNoRecipeIsLikedByUser(){
        assertEquals(0, recipeDao.getRecipesLiked(Optional.of(USER_ID_2)).size());
    }

    @Test
    public void getRecipesDisliked(){
        assertEquals(1, recipeDao.getRecipesDisliked(Optional.of(USER_ID_2)).size());
    }

    @Test
    public void getRecipesDislikedWhenNoRecipesAreDislikedByUSer(){
        assertEquals(0, recipeDao.getRecipesDisliked(Optional.of(USER_ID)).size());
    }

}


