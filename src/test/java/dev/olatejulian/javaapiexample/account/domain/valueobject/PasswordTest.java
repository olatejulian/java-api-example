package dev.olatejulian.javaapiexample.account.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;

class PasswordTest {
    @Test
    void testPassword() {
        var validValue = "JohnDoePassword123";

        assertDoesNotThrow(() -> new Password(validValue));
    }

    @Test
    void testPasswordWhenInvalid() {
        var invalidValue = "123";

        assertThrows(InvalidPasswordException.class, () -> new Password(invalidValue));
    }

    @Test
    void testFromHashedString() {
        assertDoesNotThrow(() -> {
            var hashedString = new Password("JohnDoePassword123").getPasswordValue();

            Password.fromHashedString(hashedString);
        });

    }

    @Test
    void testFromHashedStringWhenInvalid() {
        assertThrows(InvalidPasswordException.class, () -> {
            var invalidHashedString = "123";

            Password.fromHashedString(invalidHashedString);
        });

    }

    @Test
    void testCompare() throws InvalidPasswordException {
        var passwordString = "JohnDoePassword123";

        var wrongPasswordString = "JohnDoePassword1234";

        var password = new Password(passwordString);

        assertTrue(password.compare(passwordString));

        assertFalse(password.compare(wrongPasswordString));
    }

    @Test
    void testGetPassword() throws InvalidPasswordException {
        var passwordString = "JohnDoePassword123";

        var password = new Password(passwordString);

        assertNotNull(password.getPasswordValue());
    }
}
