package dev.olatejulian.javaapiexample.account.domain.exception;

public final class AccountEmailMustBeVerifiedException extends Exception {
    public AccountEmailMustBeVerifiedException(String message) {
        super(message);
    }
}
