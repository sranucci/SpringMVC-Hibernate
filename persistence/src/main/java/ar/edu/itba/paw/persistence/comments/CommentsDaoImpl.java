package ar.edu.itba.paw.persistence.comments;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.recipe.Recipe;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.persistenceInterface.CommentsDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.Instant;
import java.util.*;

@Repository
public class CommentsDaoImpl implements CommentsDao {


    @PersistenceContext
    private EntityManager em;

    @Override
    public Comment createComment(long recipeId, long userId, String commentContent) {
        Comment comment = new Comment(commentContent,em.getReference(User.class,userId),em.getReference(Recipe.class,recipeId),Date.from(Instant.now()));
        em.persist(comment);
        return comment;
    }

    @Override
    public void deleteComment(Comment comment) {
        em.remove(comment);
    }

    @Override
    public Optional<Comment> getComment(long commentId) {
        return Optional.ofNullable(em.find(Comment.class,commentId));
    }

}
