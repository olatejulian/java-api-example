package dev.olatejulian.javaapiexample.account.domain.exception;

public final class CannotVerifyAccountEmailException extends Exception {
    public CannotVerifyAccountEmailException(String message) {
        super(message);
    }
}
