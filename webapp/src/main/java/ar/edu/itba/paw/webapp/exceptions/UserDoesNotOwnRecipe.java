package ar.edu.itba.paw.webapp.exceptions;

public class UserDoesNotOwnRecipe extends RuntimeException{

    public UserDoesNotOwnRecipe() {
    }

    public UserDoesNotOwnRecipe(String message) {
        super(message);
    }

}
