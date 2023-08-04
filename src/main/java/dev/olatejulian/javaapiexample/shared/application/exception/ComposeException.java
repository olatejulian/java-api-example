package dev.olatejulian.javaapiexample.shared.application.exception;

import java.util.List;

import lombok.Getter;

public class ComposeException extends Exception {
    @Getter
    private final List<Exception> exceptions;

    public ComposeException(String message, List<Exception> exceptions) {
        super(message);

        this.exceptions = exceptions;
    }
}
