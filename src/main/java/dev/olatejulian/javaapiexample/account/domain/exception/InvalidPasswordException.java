package dev.olatejulian.javaapiexample.account.domain.exception;

public final class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
