package dev.olatejulian.javaapiexample.account.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;

public class AccountNameTest {
    @Test
    void testConstructor() {
        var name = "test";

        Assertions.assertDoesNotThrow(() -> new AccountName(name));

    }

    @Test
    void testGetName() throws InvalidAccountNameException {
        var name = "test";

        var value = new AccountName(name);

        Assertions.assertEquals(name, value.getName());
    }

    @Test
    void testEquals() throws InvalidAccountNameException {
        var name = "test";

        var value = new AccountName(name);

        Assertions.assertTrue(value.equals(value));

        var anotherName = "test2";

        var anotherAccountName = new AccountName(anotherName);

        Assertions.assertFalse(value.equals(anotherAccountName));
    }

}
