package pl.design.patterns.winter.exceptions;

public class NoIdFieldException extends RuntimeException {
    public NoIdFieldException(String message) {
        super(message);
    }

    public NoIdFieldException() {
    }
}
