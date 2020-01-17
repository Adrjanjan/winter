package pl.design.patterns.winter.exceptions;

public class InvalidIdFieldTypeException extends RuntimeException {
    public InvalidIdFieldTypeException(String message) {
        super(message);
    }

    public InvalidIdFieldTypeException() {
    }
}

