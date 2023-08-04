package dev.olatejulian.javaapiexample.shared.application.factory;

import dev.olatejulian.javaapiexample.shared.application.dto.ResponseErrorDto;

public class ResponseErrorDtoFactory {
    public static ResponseErrorDto fromException(Exception e) {
        return new ResponseErrorDto(e.getClass().getSimpleName(), e.getMessage());
    }

    private ResponseErrorDtoFactory() {
    }
}
