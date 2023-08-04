package dev.olatejulian.javaapiexample.account.application;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.Locale;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import dev.olatejulian.javaapiexample.account.application.dto.CreateAccountDto;
import dev.olatejulian.javaapiexample.account.domain.gateway.AccountRepository;

@ExtendWith(MockitoExtension.class)
class AccountServiceTest {
    @Mock
    AccountRepository accountRepository;

    @InjectMocks
    AccountService accountService;

    @Test
    void testCreateAccount() {
        assertDoesNotThrow(
                () -> {
                    var dto = new CreateAccountDto("John Doe", "john.doe@email.com", "JoeDoePassword1@");

                    accountService.createAccount(dto, Locale.getDefault());
                });
    }
}
