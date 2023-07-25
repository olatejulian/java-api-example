package dev.olatejulian.javaapiexample.account.domain.exception;

public class InvalidAccountNameException extends Exception {
    public InvalidAccountNameException(String message) {
        super(message);
    }
}
