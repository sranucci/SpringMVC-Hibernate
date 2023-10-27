package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.comments.CommentLikeData;

import java.util.Optional;

public interface CommentsLikesDao {
    void setCommentLikeStatus(CommentLikeData data , boolean liked);

    Optional<CommentLikeData> getCommentUser(long commentId, long userId);

    CommentLikeData createLikeStatus(long commentId, long userId, boolean status);

    void deleteUserLikeEntry(CommentLikeData data);
}
