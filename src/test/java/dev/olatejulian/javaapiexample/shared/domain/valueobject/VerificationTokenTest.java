package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.Locale;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidVerificationTokenException;

class VerificationTokenTest {
    private static final int VERIFICATION_TOKEN_LENGTH = 64;

    @Test
    void testVerificationToken() {
        var validValue = RandomStringUtils.randomAlphanumeric(VERIFICATION_TOKEN_LENGTH);

        assertDoesNotThrow(() -> new VerificationToken(validValue, Locale.getDefault()));
    }

    @Test
    void testVerificationTokenWhenIsInvalid() {
        var invalidValue = "invalid-value";

        assertThrows(InvalidVerificationTokenException.class,
                () -> new VerificationToken(invalidValue, Locale.getDefault()));
    }

    @Test
    void testGenerateVerificationToken() {
        var verificationToken = VerificationToken.generateVerificationToken();

        assertNotNull(verificationToken);
    }

    @Test
    void testEquals() throws InvalidVerificationTokenException {
        var verificationToken1 = VerificationToken.generateVerificationToken();

        var verificationToken2 = VerificationToken.generateVerificationToken();

        var sameValue = verificationToken1.equals(verificationToken1);

        assertTrue(sameValue);

        var diffValue = verificationToken1.equals(verificationToken2);

        assertFalse(diffValue);
    }

    @Test
    void testGetVerificationToken() {
        var verificationToken = VerificationToken.generateVerificationToken();

        assertNotNull(verificationToken.getValue());

        assertEquals(VERIFICATION_TOKEN_LENGTH, verificationToken.getValue().length());
    }
}
