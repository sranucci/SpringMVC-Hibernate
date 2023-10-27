package ar.edu.itba.paw.persistence;

import ar.edu.itba.paw.models.RecipeLikeDislike;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.RecipeLikeDao;
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

import static ar.edu.itba.paw.persistence.GlobalTestVariables.*;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
@Transactional
@Rollback
@Sql(scripts = { "classpath:populateLikesDaoImplTest.sql"})
public class RecipeLikeDaoImplTest {

    @Autowired
    private RecipeLikeDao likesDao;

    @PersistenceContext
    private EntityManager em;


    @Test
    public void testIsRecipeLikedByIdWhenTrue() {
        assertTrue(likesDao.isRecipeLikedById(USER_ID, RECIPE_ID));
    }

    @Test
    public void testIsRecipeLikedByIdWhenFalse() {
        assertFalse(likesDao.isRecipeLikedById(USER_ID_2, RECIPE_ID));
    }

    @Test
    public void testIsRecipeDislikedByIdWhenTrue() {
        assertTrue(likesDao.isRecipeDislikedById(USER_ID_2, RECIPE_ID));
    }

    @Test
    public void testIsRecipeDislikedByIdWhenFalse() {
        assertFalse(likesDao.isRecipeDislikedById(USER_ID_2, 3));
    }

    @Test
    public void testMakeRecipeLiked(){
        likesDao.makeRecipeLiked(3, RECIPE_ID);
        assertNotNull(em.find(RecipeLikeDislike.class, new RecipeLikeDislike.LikeDislikeId(RECIPE_ID, 3L)));
    }


    @Test
    public void testMakeRecipeDisliked(){
        likesDao.makeRecipeDisliked(3, RECIPE_ID);
        assertNotNull(em.find(RecipeLikeDislike.class, new RecipeLikeDislike.LikeDislikeId(RECIPE_ID, 3L)));

    }

    @Test
    public void testRemoveRecipeRating(){
        likesDao.removeRecipeRating(USER_ID, RECIPE_ID);
        assertNull(em.find(RecipeLikeDislike.class, new RecipeLikeDislike.LikeDislikeId(RECIPE_ID, USER_ID)));

    }



}
