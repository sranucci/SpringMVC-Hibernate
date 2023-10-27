package ar.edu.itba.paw.persistence.comments;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.CommentsDao;
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
@Sql(scripts = {"classpath:populateCommentsDaoImplTest.sql"})
public class CommentsDaoImplTest{

    @Autowired
    private CommentsDao commentsDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testCreateComment() {
        String commentContent = "This is a test comment.";

        Comment comment = commentsDao.createComment(RECIPE_ID, USER_ID, commentContent);

        assertNotNull(comment);
        assertEquals(commentContent, comment.getCommentContent());
        assertEquals(RECIPE_ID, comment.getRecipe().getRecipeId());
        assertEquals(USER_ID, comment.getUser().getId());
    }

    @Test
    public void testDeleteComment() {
        Comment comment = em.find(Comment.class, 1L);
        commentsDao.deleteComment(comment);
        assertNull(em.find(Comment.class, 1L));
    }


    @Test
    public void testGetCommentWhenExists() {
        Optional<Comment> comment = commentsDao.getComment(1L);
        assertTrue(comment.isPresent());
        assertEquals(USER_ID, comment.get().getUser().getId());
        assertEquals(RECIPE_ID, comment.get().getRecipe().getRecipeId());
        assertEquals("Loved the recipe", comment.get().getCommentContent());
    }

    @Test
    public void testGetCommentWhenDoesNotExist() {
        Optional<Comment> comment = commentsDao.getComment(99L);
        assertFalse(comment.isPresent());
    }

}

