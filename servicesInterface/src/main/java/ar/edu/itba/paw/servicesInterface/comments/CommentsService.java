package ar.edu.itba.paw.servicesInterface.comments;

import ar.edu.itba.paw.models.comments.Comment;
import ar.edu.itba.paw.servicesInterface.exceptions.CommentNotFoundException;

public interface CommentsService {

    Comment createComment(long recipeId, long userId, String commentContent);

    void removeCommentNotifyingUser(long commentId, String deletionMotive) throws CommentNotFoundException;
}
