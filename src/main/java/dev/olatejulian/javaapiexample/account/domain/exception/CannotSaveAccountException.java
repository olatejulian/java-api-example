package dev.olatejulian.javaapiexample.account.domain.exception;

import dev.olatejulian.javaapiexample.shared.domain.exception.CannotSaveEntityException;

public class CannotSaveAccountException extends CannotSaveEntityException {
    public CannotSaveAccountException(Exception e) {
        super(e);
    }

}
