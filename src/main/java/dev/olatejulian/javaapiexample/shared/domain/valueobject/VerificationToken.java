package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidVerificationTokenException;
import lombok.Value;
import org.apache.commons.lang3.RandomStringUtils;

@Value
public class VerificationToken {
    private static final Integer VERIFICATION_TOKEN_LENGTH = 64;

    private final String token;

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
