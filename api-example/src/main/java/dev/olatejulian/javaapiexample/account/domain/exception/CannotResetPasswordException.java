package dev.olatejulian.javaapiexample.account.domain.exception;

public final class CannotResetPasswordException extends Exception {
    public CannotResetPasswordException(String message) {
        super(message);
    }
}
