package dev.olatejulian.javaapiexample.shared.application.dto;

public record ResponseDto<D, E>(boolean success, D data, E error) {
}
