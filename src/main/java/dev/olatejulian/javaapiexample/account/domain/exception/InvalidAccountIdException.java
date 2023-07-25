package dev.olatejulian.javaapiexample.account.domain.exception;

public class InvalidAccountIdException extends Exception {
    public InvalidAccountIdException(String message) {
        super(message);
    }
}
