package dev.olatejulian.javaapiexample.account.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;

public class PasswordTest {
    @Test
    void testPassword() {
        var validValue = "JohnDoePassword123";

        Assertions.assertDoesNotThrow(() -> new Password(validValue));
    }

    @Test
    void testPasswordWhenInvalid() {
        var invalidValue = "123";

        Assertions.assertThrows(InvalidPasswordException.class, () -> new Password(invalidValue));
    }

    @Test
    void testFromHashedString() {
        Assertions.assertDoesNotThrow(() -> {
            var hashedString = new Password("JohnDoePassword123").getPassword();

            Password.fromHashedString(hashedString);
        });

    }

    @Test
    void testFromHashedStringWhenInvalid() {
        Assertions.assertThrows(InvalidPasswordException.class, () -> {
            var invalidHashedString = "123";

            Password.fromHashedString(invalidHashedString);
        });

    }

    @Test
    void testCompare() throws InvalidPasswordException {
        var passwordString = "JohnDoePassword123";

        var wrongPasswordString = "JohnDoePassword1234";

        var password = new Password(passwordString);

        Assertions.assertTrue(password.compare(passwordString));

        Assertions.assertFalse(password.compare(wrongPasswordString));
    }

    @Test
    void testGetPassword() throws InvalidPasswordException {
        var passwordString = "JohnDoePassword123";

        var password = new Password(passwordString);

        Assertions.assertNotNull(password.getPassword());
    }
}
