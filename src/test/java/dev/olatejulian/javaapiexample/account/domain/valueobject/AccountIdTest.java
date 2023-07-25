package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.UUID;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountIdException;

public class AccountIdTest {
    @Test
    void testConstructorWithValidValue() {
        var validValue = UUID.randomUUID().toString();

        Assertions.assertDoesNotThrow(() -> {
            new AccountId(validValue);
        });
    }

    @Test
    void testConstructorWithInvalidValue() {
        var invalidValue = "invalid-value";

        Assertions.assertThrows(InvalidAccountIdException.class, () -> {
            new AccountId(invalidValue);
        });
    }

    @Test
    void testGenerateId() {
        Assertions.assertNotNull(AccountId.generateId());
    }

    @Test
    void testGetId() {
        var accountId = AccountId.generateId();

        Assertions.assertNotNull(accountId.getId());
    }

    @Test
    void testEquals() {
        var accountId = AccountId.generateId();
        var anotherAccountId = AccountId.generateId();

        Assertions.assertTrue(accountId.equals(accountId));

        Assertions.assertFalse(accountId.equals(anotherAccountId));
    }
}
