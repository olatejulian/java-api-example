package dev.olatejulian.javaapiexample.account.application.dto;

public record CreateAccountDto(
        String name, String email, String password) {
}
