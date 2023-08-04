package dev.olatejulian.javaapiexample.account.domain.gateway;

import dev.olatejulian.javaapiexample.account.domain.Account;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotSaveAccountException;

public interface AccountRepository {
    void save(Account account) throws CannotSaveAccountException;

}
