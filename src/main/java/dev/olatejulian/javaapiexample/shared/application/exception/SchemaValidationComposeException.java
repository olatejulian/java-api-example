package dev.olatejulian.javaapiexample.shared.application.exception;

import java.util.List;

public final class SchemaValidationComposeException extends ComposeException {
    public SchemaValidationComposeException(String message, List<Exception> exceptions) {
        super(message, exceptions);
    }
}
