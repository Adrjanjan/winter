package pl.design.patterns.winter.exceptions;

public class NonNullableFieldIsNull extends RuntimeException {
    public NonNullableFieldIsNull(String message) {
        super(message);
    }
}
