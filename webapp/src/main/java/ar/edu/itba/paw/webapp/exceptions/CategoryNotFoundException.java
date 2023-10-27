package ar.edu.itba.paw.webapp.exceptions;

public class CategoryNotFoundException extends RuntimeException {
    public CategoryNotFoundException() {
    }

    public CategoryNotFoundException(String message) {
        super(message);
    }
}