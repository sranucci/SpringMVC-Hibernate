package ar.edu.itba.paw.persistence.comments;

import ar.edu.itba.paw.models.comments.CommentLikeData;
import ar.edu.itba.paw.persistence.config.TestConfig;
import ar.edu.itba.persistenceInterface.CommentsLikesDao;
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
@Sql(scripts = { "classpath:populateCommentsLikesDaoImplTest.sql"})
public class CommentsLikesDaoImplTest{

    @Autowired
    private CommentsLikesDao commentsLikesDao;

    @PersistenceContext
    private EntityManager em;

    @Test
    public void testModifyCommentUserWhenCommentsExist(){
        CommentLikeData commentLikeData = em.find(CommentLikeData.class, new CommentLikeData.CommentLikeId(USER_ID, RECIPE_ID));
        commentsLikesDao.setCommentLikeStatus(commentLikeData, false);
        assertFalse(commentLikeData.isLiked());
    }


    @Test
    public void testGetCommentUserWhenCommentIsLiked(){
        Optional<CommentLikeData> clData = commentsLikesDao.getCommentUser(1, USER_ID);
        //the comment exists and is liked

        assertTrue(clData.isPresent());
        assertEquals(USER_ID, clData.get().getUser().getId());
        assertEquals(1, clData.get().getComment().getCommentId());
        assertTrue(clData.get().isLiked());

    }

    @Test
    public void testGetCommentUserWhenCommentIsDisliked() {
        Optional<CommentLikeData> clData = commentsLikesDao.getCommentUser(1, USER_ID_2);
        //the comment exists and is disliked

        assertTrue(clData.isPresent());
        assertEquals(USER_ID_2, clData.get().getUser().getId());
        assertEquals(1, clData.get().getComment().getCommentId());
        assertFalse(clData.get().isLiked());
    }

    @Test
    public void testGetCommentUserWhenCommentNotExists() {
        Optional<CommentLikeData> clData = commentsLikesDao.getCommentUser(99, USER_ID_2);;
        assertFalse(clData.isPresent());
    }

    @Test
    public void testGetCommentUserWhenUserDidntRate() {
        Optional<CommentLikeData> clData = commentsLikesDao.getCommentUser(3, USER_ID);
        assertFalse(clData.isPresent());
    }

    @Test
    public void testDeleteUserLikeEntryWhenCommentsExist(){
        CommentLikeData comment = em.find(CommentLikeData.class, new CommentLikeData.CommentLikeId(USER_ID, 1));
        //existing comment ratings are deleted
        commentsLikesDao.deleteUserLikeEntry(comment);
        assertNull(em.find(CommentLikeData.class, new CommentLikeData.CommentLikeId(USER_ID, 1)));
    }



    @Test
    public void testCreateLikeStatus(){
        //trying to rate a comment that isn't rated yet
        CommentLikeData commentLikeData = commentsLikesDao.createLikeStatus(1, 3, true);
        assertNotNull(commentLikeData);
        assertEquals(1, commentLikeData.getComment().getCommentId());
        assertEquals(Long.valueOf(3), commentLikeData.getUser().getId());
        assertTrue(commentLikeData.isLiked());

    }


}


