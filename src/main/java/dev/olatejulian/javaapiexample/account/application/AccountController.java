package dev.olatejulian.javaapiexample.account.application;

import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import dev.olatejulian.javaapiexample.account.application.dto.CreateAccountDto;
import dev.olatejulian.javaapiexample.shared.application.SpringBootBaseController;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseDto;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseErrorDto;
import dev.olatejulian.javaapiexample.shared.application.exception.SchemaValidationComposeException;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseDtoFactory;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseErrorDtoFactory;
import dev.olatejulian.javaapiexample.shared.domain.exception.CannotSaveEntityException;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.Value;

@RestController
@Tag(name = "Account")
public class AccountController extends SpringBootBaseController {
    @Autowired
    private AccountService service;

    @PostMapping("signup")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Account created", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = SignupExampleResponseBody.EXAMPLE_SUCCESS_201)
                    }, schema = @Schema(implementation = SignupExampleResponseBody.class)) }),
            @ApiResponse(responseCode = "400", description = "Bad request", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = SignupExampleResponseBody.EXAMPLE_ERROR_400) }, schema = @Schema(implementation = SignupExampleResponseBody.class)) }),
            @ApiResponse(responseCode = "422", description = "Account schema error", content = {
                    @Content(mediaType = "application/json", examples = {
                            @ExampleObject(value = Signup422ExampleResponseBody.EXAMPLE_ERROR_422)
                    }, schema = @Schema(implementation = Signup422ExampleResponseBody.class)) }) })
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

    @Value
    private static class SignupExampleResponseBody {
        private static final String EXAMPLE_SUCCESS_201 = """
                {
                    "success": true,
                    "data": null,
                    "error": null
                }
                """;
        private static final String EXAMPLE_ERROR_400 = """
                {
                    "success": false,
                    "data": null,
                    "error": {
                        "message": "CannotSaveAccount",
                        "details": "Database error explanation"
                    }
                }
                """;

        private boolean success;

        private String data;

        private ResponseErrorDto error;
    }

    @Value
    private static class Signup422ExampleResponseBody {
        private static final String EXAMPLE_ERROR_422 = """
                {
                    "success": false,
                    "data": null,
                    "error": [
                        {
                            "message": "Error message 01",
                            "details": "Details 01"
                        },
                        {
                            "message": "Error message 02",
                            "details": "Details 02"
                        }
                    ]
                }
                """;

        private boolean success;

        private String data;

        private List<ResponseErrorDto> error;
    }
}
