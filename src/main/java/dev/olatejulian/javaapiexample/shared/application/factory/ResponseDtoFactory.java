package dev.olatejulian.javaapiexample.shared.application.factory;

import dev.olatejulian.javaapiexample.shared.application.dto.ResponseDto;

public class ResponseDtoFactory {
    public static <D, E> ResponseDto<D, E> success(D data) {
        return new ResponseDto<>(true, data, null);
    }

    public static <D, E> ResponseDto<D, E> success() {
        return new ResponseDto<>(true, null, null);
    }

    public static <D, E> ResponseDto<D, E> error(E error) {
        return new ResponseDto<>(false, null, error);
    }

    private ResponseDtoFactory() {
    }
}
