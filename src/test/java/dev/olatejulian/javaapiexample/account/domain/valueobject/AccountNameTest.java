package dev.olatejulian.javaapiexample.account.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;

class AccountNameTest {
    @Test
    void testConstructor() {
        var name = "test";

        assertDoesNotThrow(() -> new AccountName(name));

    }

    @Test
    void testConstructorWhenNameIsNull() {
        String name = null;

        assertThrows(InvalidAccountNameException.class, () -> new AccountName(name));
    }

    @Test
    void testGetName() throws InvalidAccountNameException {
        var name = "test";

        var value = new AccountName(name);

        assertEquals(name, value.getValue());
    }

    @Test
    void testEquals() throws InvalidAccountNameException {
        var name = "test";

        var value = new AccountName(name);

        var sameValue = value.equals(value);

        assertTrue(sameValue);

        var anotherName = "test2";

        var anotherAccountName = new AccountName(anotherName);

        var diffValue = value.equals(anotherAccountName);

        assertFalse(diffValue);
    }
}
