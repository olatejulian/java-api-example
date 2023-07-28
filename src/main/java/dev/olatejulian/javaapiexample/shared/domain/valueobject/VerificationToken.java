package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import org.apache.commons.lang3.RandomStringUtils;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidVerificationTokenException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class VerificationToken {
    private static final Integer VERIFICATION_TOKEN_LENGTH = 64;

    @Getter
    private String token;

    public VerificationToken(String token) throws InvalidVerificationTokenException {
        validate(token);

        this.token = token;
    }

    public static VerificationToken generateVerificationToken() {
        var randomString = RandomStringUtils.randomAlphanumeric(VERIFICATION_TOKEN_LENGTH);

        try {
            return new VerificationToken(randomString);
        } catch (InvalidVerificationTokenException e) {
            return null;
        }
    }

    private static void validate(String verificationToken) throws InvalidVerificationTokenException {
        Object[][] errors = {
                {
                        verificationToken == null || verificationToken.length() != VERIFICATION_TOKEN_LENGTH,
                        String.format("Verification token must be %d characters long", VERIFICATION_TOKEN_LENGTH)
                },
                {
                        verificationToken == null || !verificationToken.matches("[a-zA-Z0-9]+"),
                        "Verification token must only contain alphanumeric characters"
                }
        };

        for (Object[] error : errors) {
            if ((boolean) error[0]) {
                throw new InvalidVerificationTokenException((String) error[1]);
            }
        }
    }
}
