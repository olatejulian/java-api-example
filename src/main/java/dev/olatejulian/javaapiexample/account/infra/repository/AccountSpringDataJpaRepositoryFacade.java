package dev.olatejulian.javaapiexample.account.infra.repository;

import java.util.UUID;

import dev.olatejulian.javaapiexample.account.domain.Account;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotSaveAccountException;
import dev.olatejulian.javaapiexample.account.domain.gateway.AccountRepository;

public final class AccountSpringDataJpaRepositoryFacade implements AccountRepository {
    private final AccountSpringDataJpaRepository repository;

    public AccountSpringDataJpaRepositoryFacade(AccountSpringDataJpaRepository repository) {
        this.repository = repository;
    }

    @Override
    public void save(Account account) throws CannotSaveAccountException {
        try {
            this.repository.save(toPersistenceModel(account));

        } catch (Exception e) {
            throw new CannotSaveAccountException(e);
        }
    }

    private static AccountSpringDataJpaModel toPersistenceModel(Account account) {
        return new AccountSpringDataJpaModel(
                UUID.fromString(account.getId().getValue()),
                account.getName().getValue(),
                account.getEmailAddress().getValue(),
                account.getEmailVerificationToken() == null
                        ? null
                        : account.getEmailVerificationToken().getValue(),
                account.getEmailVerificationTokenSentAt(),
                account.getEmailAddressVerified(),
                account.getEmailVerifiedAt(),
                account.getPassword().getValue(),
                account.getPasswordResetToken() == null
                        ? null
                        : account.getPasswordResetToken().getValue(),
                account.getPasswordResetTokenSentAt(),
                account.getActive(),
                account.getActivatedAt(),
                account.getCreatedAt(),
                account.getUpdatedAt(),
                account.getLocale());
    }
}
