package ar.edu.itba.paw.persistence.comments;


import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.comments.CommentLikeData;
import ar.edu.itba.paw.models.user.User;
import ar.edu.itba.persistenceInterface.CommentsLikesDao;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
public class CommentsLikesDaoImpl implements CommentsLikesDao {


    @PersistenceContext
    private EntityManager em;


    @Override
    public void setCommentLikeStatus(CommentLikeData data, boolean liked) {
        data.setLiked(liked);
        em.merge(data);
    }

    @Override
    public Optional<CommentLikeData> getCommentUser(long commentId, long userId) {
        CommentLikeData.CommentLikeId key = new CommentLikeData.CommentLikeId(userId,commentId);
        CommentLikeData comment = em.find(CommentLikeData.class,key);
        return Optional.ofNullable(comment);
    }

    @Override
    public CommentLikeData createLikeStatus(long commentId, long userId, boolean status) {
        User userReference = em.getReference(User.class,userId);
        Comment commentReference = em.getReference(Comment.class,commentId);
        CommentLikeData data = new CommentLikeData(commentReference,userReference,status);
        em.persist(data);
        return data;

    }

    @Override
    public void deleteUserLikeEntry(CommentLikeData data) {
        em.remove(data);
    }
}
