package dev.olatejulian.javaapiexample.account.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;
import java.util.UUID;

import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountIdException;

class AccountIdTest {
    @Test
    void testConstructorWithValidValue() {
        var validValue = UUID.randomUUID().toString();

        assertDoesNotThrow(() -> {
            new AccountId(validValue, Locale.getDefault());
        });
    }

    @Test
    void testConstructorWithInvalidValue() {
        var invalidValue = "invalid-value";

        assertThrows(InvalidAccountIdException.class, () -> {
            new AccountId(invalidValue, Locale.getDefault());
        });
    }

    @Test
    void testGenerateId() {
        assertNotNull(AccountId.generateId());
    }

    @Test
    void testGetId() {
        var accountId = AccountId.generateId();

        assertNotNull(accountId.getValue());
    }

    @Test
    void testEquals() {
        var accountId = AccountId.generateId();
        var anotherAccountId = AccountId.generateId();

        var sameValue = accountId.equals(accountId);

        assertTrue(sameValue);

        var diffValue = accountId.equals(anotherAccountId);

        assertFalse(diffValue);
    }
}
