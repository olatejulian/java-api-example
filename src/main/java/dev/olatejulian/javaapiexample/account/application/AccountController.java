package dev.olatejulian.javaapiexample.account.application;

import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.olatejulian.javaapiexample.account.application.dto.CreateAccountDto;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseDto;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseErrorDto;
import dev.olatejulian.javaapiexample.shared.application.exception.SchemaValidationComposeException;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseDtoFactory;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseErrorDtoFactory;
import dev.olatejulian.javaapiexample.shared.domain.exception.CannotSaveEntityException;

@RestController
public class AccountController {
    @Autowired
    private AccountService service;

    @PostMapping("signup")
    public ResponseEntity<ResponseDto<String, ResponseErrorDto>> createAccount(
            @RequestBody CreateAccountDto createAccountRequestBody, Locale locale)
            throws SchemaValidationComposeException {
        try {
            this.service.createAccount(createAccountRequestBody, locale);

            return ResponseEntity.status(HttpStatus.CREATED).body(ResponseDtoFactory.success());
        } catch (CannotSaveEntityException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(ResponseDtoFactory.error(ResponseErrorDtoFactory.fromException(e)));
        }
    }
}
