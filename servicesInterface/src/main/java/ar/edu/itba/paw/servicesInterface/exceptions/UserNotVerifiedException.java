package ar.edu.itba.paw.servicesInterface.exceptions;

public class UserNotVerifiedException extends RuntimeException {

    public UserNotVerifiedException() {
    }

    public UserNotVerifiedException(String message) {
        super(message);
    }
}
