package dev.olatejulian.javaapiexample;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import dev.olatejulian.javaapiexample.shared.application.ExceptionService;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseDto;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseErrorDto;
import dev.olatejulian.javaapiexample.shared.application.exception.SchemaValidationComposeException;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseDtoFactory;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(SchemaValidationComposeException.class)
    public ResponseEntity<ResponseDto<String, List<ResponseErrorDto>>> handleSchemaValidationComposeException(
            SchemaValidationComposeException composeException) {
        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ResponseDtoFactory
                        .error(ExceptionService.toListOfResponseErrorDto(composeException.getExceptions())));
    }
}
