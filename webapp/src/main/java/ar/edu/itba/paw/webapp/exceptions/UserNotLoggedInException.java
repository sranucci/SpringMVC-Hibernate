package ar.edu.itba.paw.webapp.exceptions;

public class UserNotLoggedInException extends RuntimeException {
    public UserNotLoggedInException() {
    }

    public UserNotLoggedInException(String message) {
        super(message);
    }
}
