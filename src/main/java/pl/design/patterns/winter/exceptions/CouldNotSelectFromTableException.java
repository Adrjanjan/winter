package pl.design.patterns.winter.exceptions;

public class CouldNotSelectFromTableException extends RuntimeException {
    public CouldNotSelectFromTableException(String message, Throwable cause) {
        super(message, cause);
    }

}