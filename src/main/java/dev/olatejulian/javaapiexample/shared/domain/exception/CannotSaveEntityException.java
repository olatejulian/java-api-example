package dev.olatejulian.javaapiexample.shared.domain.exception;

public class CannotSaveEntityException extends Exception {
    public CannotSaveEntityException(Exception e) {
        super(e);
    }
}
