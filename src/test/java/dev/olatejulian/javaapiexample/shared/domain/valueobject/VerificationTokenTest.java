package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidVerificationTokenException;

public class VerificationTokenTest {
    @Test
    void testVerificationToken() {
        var validValue = RandomStringUtils.randomAlphanumeric(64);

        Assertions.assertDoesNotThrow(() -> new VerificationToken(validValue));
    }

    @Test
    void testVerificationTokenWhenIsInvalid() {
        var invalidValue = "invalid-value";

        Assertions.assertThrows(InvalidVerificationTokenException.class, () -> new VerificationToken(invalidValue));
    }

    @Test
    void testGenerateVerificationToken() {
        var verificationToken = VerificationToken.generateVerificationToken();

        Assertions.assertNotNull(verificationToken);
    }

    @Test
    void testEquals() throws InvalidVerificationTokenException {
        var verificationToken1 = VerificationToken.generateVerificationToken();

        var verificationToken2 = VerificationToken.generateVerificationToken();

        Assertions.assertTrue(verificationToken1.equals(verificationToken1));

        Assertions.assertFalse(verificationToken1.equals(verificationToken2));
    }

    @Test
    void testGetVerificationToken() {
        var verificationToken = VerificationToken.generateVerificationToken();

        Assertions.assertNotNull(verificationToken.getVerificationToken());

        Assertions.assertEquals(64, verificationToken.getVerificationToken().length());
    }
}
