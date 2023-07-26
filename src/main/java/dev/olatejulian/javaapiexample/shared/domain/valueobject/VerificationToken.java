package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import org.apache.commons.lang3.RandomStringUtils;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidVerificationTokenException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class VerificationToken {
    @Getter
    private String verificationToken;

    public VerificationToken(String verificationToken) throws InvalidVerificationTokenException {
        validate(verificationToken);

        this.verificationToken = verificationToken;
    }

    public static VerificationToken generateVerificationToken() {
        var randomString = RandomStringUtils.randomAlphanumeric(verificationTokenLength());

        try {
            return new VerificationToken(randomString);
        } catch (InvalidVerificationTokenException e) {
            return null;
        }
    }

    private static Integer verificationTokenLength() {
        return 64;
    }

    private static void validate(String verificationToken) throws InvalidVerificationTokenException {
        if (verificationToken.length() != verificationTokenLength()) {
            throw new InvalidVerificationTokenException("Invalid verification token length");
        }

        if (!verificationToken.matches("[a-zA-Z0-9]+")) {
            throw new InvalidVerificationTokenException("Invalid verification token characters");
        }
    }

}
