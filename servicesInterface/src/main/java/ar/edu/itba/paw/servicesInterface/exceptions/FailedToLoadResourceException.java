package ar.edu.itba.paw.servicesInterface.exceptions;

public class FailedToLoadResourceException extends RuntimeException{
    public FailedToLoadResourceException(){
        super();
    }

    public FailedToLoadResourceException(String message){
        super(message);
    }
}
