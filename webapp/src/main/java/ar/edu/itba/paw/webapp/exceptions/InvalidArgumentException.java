package ar.edu.itba.paw.webapp.exceptions;

public class InvalidArgumentException extends RuntimeException {
    public InvalidArgumentException() {
        super();
    }

    public InvalidArgumentException(String message) {
        super(message);
    }
}
