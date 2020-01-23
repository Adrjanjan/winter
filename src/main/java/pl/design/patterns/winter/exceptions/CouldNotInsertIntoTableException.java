package pl.design.patterns.winter.exceptions;

public class CouldNotInsertIntoTableException extends RuntimeException {
    public CouldNotInsertIntoTableException(String message, Throwable cause) {
        super(message, cause);
    }
}
