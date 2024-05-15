package dev.olatejulian.javaapiexample;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.List;
import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import dev.olatejulian.javaapiexample.account.application.AccountController;
import dev.olatejulian.javaapiexample.account.application.dto.CreateAccountDto;
import dev.olatejulian.javaapiexample.account.domain.AccountUtils;
import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;
import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;
import dev.olatejulian.javaapiexample.shared.application.dto.ResponseErrorDto;
import dev.olatejulian.javaapiexample.shared.application.factory.ResponseDtoFactory;
import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class JavaApiExampleApplicationTests {
	@LocalServerPort
	int port;

	@Autowired
	TestRestTemplate restTemplate;

	@Autowired
	AccountController accountController;

	ObjectMapper mapper = new ObjectMapper();

	String getUrl(String path) {
		return "http://localhost:" + port + path;
	}

	@Test
	void contextLoads() {
		assertNotNull(accountController);
	}

	@Test
	void testSignup() throws JsonProcessingException {
		var url = getUrl("/signup");

		var account = AccountUtils.createRandomAccount();

		var requestBody = new CreateAccountDto(account.getName().getValue(), account.getEmailAddress().getValue(),
				account.getPassword().getValue());

		var headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		var requestEntity = new HttpEntity<CreateAccountDto>(requestBody, headers);

		var response = this.restTemplate.exchange(
				url,
				HttpMethod.POST,
				requestEntity,
				String.class);

		var responseDto = ResponseDtoFactory.success();

		var responseBodyExpected = this.mapper.writeValueAsString(responseDto);

		assertEquals(responseBodyExpected, response.getBody());

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}

	@Test
	void testSignupWhenTheRequestBodyIsInvalid() throws JsonProcessingException {
		var url = getUrl("/signup");

		var requestBody = new CreateAccountDto("", "", "");

		var headers = new HttpHeaders();

		headers.setContentType(MediaType.APPLICATION_JSON);

		var requestEntity = new HttpEntity<CreateAccountDto>(requestBody, headers);

		var response = this.restTemplate.exchange(
				url,
				HttpMethod.POST,
				requestEntity,
				String.class);

		assertEquals(HttpStatus.UNPROCESSABLE_ENTITY, response.getStatusCode());

		var NAME_MIN_LENGTH = 3;

		var PASSWORD_MIN_LENGTH = 8;

		var invalidAccountNameExceptionMessage = String.format(CustomExceptionMessages
				.getMessage("account.account_name.name_must_be_longer_than_x_characters", Locale.getDefault()),
				NAME_MIN_LENGTH);

		var invalidEmailAddressExceptionMessage = CustomExceptionMessages
				.getMessage("shared.email_address.invalid_format", Locale.getDefault());

		var invalidPasswordExceptionMessage = String.format(CustomExceptionMessages
				.getMessage("account.password.password_must_be_longer_than_x_characters", Locale.getDefault()),
				PASSWORD_MIN_LENGTH);

		var listOfResponseErrorDto = List.of(
				new ResponseErrorDto(InvalidAccountNameException.class.getSimpleName(),
						invalidAccountNameExceptionMessage),
				new ResponseErrorDto(InvalidaEmailAddressException.class.getSimpleName(),
						invalidEmailAddressExceptionMessage),
				new ResponseErrorDto(InvalidPasswordException.class.getSimpleName(), invalidPasswordExceptionMessage));

		var responseDto = ResponseDtoFactory.error(listOfResponseErrorDto);

		var responseBodyExpected = this.mapper.writeValueAsString(responseDto);

		assertEquals(responseBodyExpected, response.getBody());
	}
}
