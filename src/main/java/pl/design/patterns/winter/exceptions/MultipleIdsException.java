package pl.design.patterns.winter.exceptions;

public class MultipleIdsException extends RuntimeException {
    public MultipleIdsException(String s) {
        super(s);
    }

    public MultipleIdsException() {
    }
}
