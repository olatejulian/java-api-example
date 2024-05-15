package dev.olatejulian.javaapiexample.account.domain;

import org.apache.commons.lang3.RandomStringUtils;

import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountName;
import dev.olatejulian.javaapiexample.account.domain.valueobject.Password;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.EmailAddress;

public class AccountUtils {
    public static final Account createRandomAccount() {
        var randomName = RandomStringUtils.randomAlphabetic(10);

        var randomEmailAddress = RandomStringUtils.randomAlphabetic(10).concat("@email.com");

        var randomPassword = RandomStringUtils.randomAlphanumeric(10) + "1@aA";

        try {
            var name = new AccountName(randomName);

            var emailAddress = new EmailAddress(randomEmailAddress);

            var password = new Password(randomPassword);

            return Account.create(name, emailAddress, password);

        } catch (Exception e) {
            return null;
        }
    }

    private AccountUtils() {
    }
}
