package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;

class EmailAddressTest {
    @Test
    void testConstructorWithValidValue() throws InvalidaEmailAddressException {
        var validEmailAddressString = "john.doe@email.com";

        var emailAddress = new EmailAddress(validEmailAddressString);

        assertNotNull(emailAddress);
    }

    @Test
    void testConstructorWithInvalidValue() {
        var invalidEmailAddressString = "invalid-email-address";

        assertThrows(InvalidaEmailAddressException.class, () -> {
            new EmailAddress(invalidEmailAddressString);
        });
    }

    @Test
    void testEquals() throws InvalidaEmailAddressException {
        var johnDoeEmailAddressString = "john.doe@email.com";

        var carlJacksonEmailAddressString = "carl.jackson@email.com";

        var johnDoeEmailAddress = new EmailAddress(johnDoeEmailAddressString);

        var carlJacksonEmailAddress = new EmailAddress(carlJacksonEmailAddressString);

        var sameValue = johnDoeEmailAddress.equals(johnDoeEmailAddress);

        assertTrue(sameValue);

        var diffValue = johnDoeEmailAddress.equals(carlJacksonEmailAddress);

        assertFalse(diffValue);
    }

    @Test
    void testGetEmailAddress() throws InvalidaEmailAddressException {
        var emailAddressString = "john.doe@email.com";

        var emailAddress = new EmailAddress(emailAddressString);

        assertEquals(emailAddressString, emailAddress.getValue());
    }
}
