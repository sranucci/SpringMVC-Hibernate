package ar.edu.itba.paw.services.comments;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.models.comments.CommentLikeData;
import ar.edu.itba.paw.servicesInterface.comments.CommentsLikesService;
import ar.edu.itba.persistenceInterface.CommentsLikesDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class CommentsLikesServiceImpl implements CommentsLikesService {
    private final CommentsLikesDao commentsLikesDao;

    @Autowired
    public CommentsLikesServiceImpl(final CommentsLikesDao commentsLikesDao) {
        this.commentsLikesDao = commentsLikesDao;
    }

    private static final Logger LOGGER = LoggerFactory.getLogger(CommentsLikesServiceImpl.class);

    @Transactional
    @Override
    public Comment setCommentLikeStatus(long commentId, long userId, boolean likeStatus) {
        Optional<CommentLikeData> data = commentsLikesDao.getCommentUser(commentId, userId);
        if (data.isPresent()) {
            CommentLikeData commentData = data.get();
            if (likeStatus != commentData.isLiked()){
                commentsLikesDao.setCommentLikeStatus(commentData,likeStatus);
                LOGGER.info("User {} changing rating to \"{}\" on comment {}", userId, likeStatus ? "like" : "dislike", commentId);
            }
            else {
                commentsLikesDao.deleteUserLikeEntry(commentData);
                LOGGER.info("User {} deleting rating on comment {}", userId, commentId);
            }
            return commentData.getComment();
        }
        LOGGER.info("User {} adding rating \"{}\" on comment {}", userId, likeStatus ? "like" : "dislike", commentId);
        return commentsLikesDao.createLikeStatus(commentId, userId, likeStatus).getComment();
    }
}
