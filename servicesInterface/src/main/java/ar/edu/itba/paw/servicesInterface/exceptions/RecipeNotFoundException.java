package ar.edu.itba.paw.servicesInterface.exceptions;

public class RecipeNotFoundException extends RuntimeException {
    public RecipeNotFoundException() {
    }

    public RecipeNotFoundException(String message) {
        super(message);
    }
}
