package ar.edu.itba.persistenceInterface;

import ar.edu.itba.paw.models.comments.Comment;

import java.util.Optional;

public interface CommentsDao {

    Comment createComment(long recipeId, long userId, String commentContent);

    void deleteComment(Comment comment);

    Optional<Comment> getComment(long commentId);

}
