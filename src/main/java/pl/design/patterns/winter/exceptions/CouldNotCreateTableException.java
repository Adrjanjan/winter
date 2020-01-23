package pl.design.patterns.winter.exceptions;

public class CouldNotCreateTableException extends RuntimeException {

    public CouldNotCreateTableException(String message, Throwable cause) {
        super(message, cause);
    }
}
