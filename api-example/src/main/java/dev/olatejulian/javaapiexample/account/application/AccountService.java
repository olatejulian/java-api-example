package dev.olatejulian.javaapiexample.account.application;

import java.util.List;
import java.util.Locale;

import dev.olatejulian.javaapiexample.account.application.dto.CreateAccountDto;
import dev.olatejulian.javaapiexample.account.domain.Account;
import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;
import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;
import dev.olatejulian.javaapiexample.account.domain.gateway.AccountRepository;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountName;
import dev.olatejulian.javaapiexample.account.domain.valueobject.Password;
import dev.olatejulian.javaapiexample.shared.application.ExceptionService;
import dev.olatejulian.javaapiexample.shared.application.exception.SchemaValidationComposeException;
import dev.olatejulian.javaapiexample.shared.domain.exception.CannotSaveEntityException;
import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.EmailAddress;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public final class AccountService {
        private final ExceptionService exceptionService = new ExceptionService();

        private final AccountRepository repository;

        public void createAccount(CreateAccountDto createAccountDto, Locale locale)
                        throws SchemaValidationComposeException, CannotSaveEntityException {

                var name = this.exceptionService
                                .executeOrAddException(() -> new AccountName(createAccountDto.name(), locale));

                var emailAddress = this.exceptionService
                                .executeOrAddException(() -> new EmailAddress(createAccountDto.email(), locale));

                var password = this.exceptionService
                                .executeOrAddException(() -> new Password(createAccountDto.password(), locale));

                this.exceptionService.throwComposeException(SchemaValidationComposeException::new,
                                List.of(InvalidAccountNameException.class, InvalidaEmailAddressException.class,
                                                InvalidPasswordException.class));

                var account = Account.create(name, emailAddress, password);

                account.setLocale(locale);

                this.repository.save(account);
        }
}
