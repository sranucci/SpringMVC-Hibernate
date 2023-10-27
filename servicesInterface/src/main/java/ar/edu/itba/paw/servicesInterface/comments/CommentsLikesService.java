package ar.edu.itba.paw.servicesInterface.comments;

import ar.edu.itba.paw.models.comments.Comment;

public interface CommentsLikesService {
    Comment setCommentLikeStatus(long commentId, long userId, boolean likeStatus);
}
