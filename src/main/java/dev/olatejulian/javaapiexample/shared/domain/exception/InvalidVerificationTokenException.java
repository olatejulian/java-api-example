package dev.olatejulian.javaapiexample.shared.domain.exception;

public class InvalidVerificationTokenException extends Exception {
    public InvalidVerificationTokenException(String message) {
        super(message);
    }
}
