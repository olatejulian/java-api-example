package dev.olatejulian.javaapiexample.account.domain.exception;

public class InvalidPasswordException extends Exception {
    public InvalidPasswordException(String message) {
        super(message);
    }
}
