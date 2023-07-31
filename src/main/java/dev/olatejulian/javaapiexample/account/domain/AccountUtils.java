package dev.olatejulian.javaapiexample.account.domain;

import org.apache.commons.lang3.RandomStringUtils;

import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountName;
import dev.olatejulian.javaapiexample.account.domain.valueobject.Password;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.EmailAddress;

public class AccountUtils {
    private AccountUtils() {
    }

    public static final Account createRandomAccount() {
        try {
            var name = new AccountName(RandomStringUtils.randomAlphabetic(10));

            var emailAddress = new EmailAddress(RandomStringUtils.randomAlphabetic(10).concat("@email.com"));

            var password = new Password(RandomStringUtils.randomAlphanumeric(10));

            return Account.create(name, emailAddress, password);

        } catch (Exception e) {
            return null;
        }
    }
}
