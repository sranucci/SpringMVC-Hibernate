package ar.edu.itba.paw.webapp.exceptions;

public class CommentNotFoundForDeletionException extends RuntimeException {

    public CommentNotFoundForDeletionException(){}
    public CommentNotFoundForDeletionException(String message) {
        super(message);
    }
}
