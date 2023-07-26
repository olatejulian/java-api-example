package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;

public class EmailAddressTest {
    @Test
    void testConstructorWithValidValue() throws InvalidaEmailAddressException {
        var validEmailAddressString = "john.doe@email.com";

        var emailAddress = new EmailAddress(validEmailAddressString);

        Assertions.assertNotNull(emailAddress);
    }

    @Test
    void testConstructorWithInvalidValue() {
        var invalidEmailAddressString = "invalid-email-address";

        Assertions.assertThrows(InvalidaEmailAddressException.class, () -> {
            new EmailAddress(invalidEmailAddressString);
        });
    }

    @Test
    void testEquals() throws InvalidaEmailAddressException {
        var johnDoeEmailAddressString = "john.doe@email.com";

        var carlJacksonEmailAddressString = "carl.jackson@email.com";

        var johnDoeEmailAddress = new EmailAddress(johnDoeEmailAddressString);

        var carlJacksonEmailAddress = new EmailAddress(carlJacksonEmailAddressString);

        Assertions.assertTrue(johnDoeEmailAddress.equals(johnDoeEmailAddress));

        Assertions.assertFalse(johnDoeEmailAddress.equals(carlJacksonEmailAddress));
    }

    @Test
    void testGetEmailAddress() throws InvalidaEmailAddressException {
        var emailAddressString = "john.doe@email.com";

        var emailAddress = new EmailAddress(emailAddressString);

        Assertions.assertEquals(emailAddressString, emailAddress.getEmailAddress());
    }
}
