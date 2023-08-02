package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import java.util.Locale;
import java.util.regex.Pattern;

import org.apache.commons.lang3.RandomStringUtils;

import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidVerificationTokenException;
import lombok.Value;

@Value
public class VerificationToken {
    private static final String TOKEN_MUST_HAVE_X_CHARACTERS = "shared.verification_token.token_must_have_x_characters";

    private static final String TOKEN_MUST_HAVE_ONLY_ALPHANUMERIC_CHARACTERS = "shared.verification_token.token_must_have_only_alphanumeric_characters";

    private static final int VERIFICATION_TOKEN_LENGTH = 64;

    private static final String VERIFICATION_TOKEN_REGEX = "^[a-zA-Z0-9]*$";

    public static VerificationToken generateVerificationToken() {
        var randomString = RandomStringUtils.randomAlphanumeric(VERIFICATION_TOKEN_LENGTH);

        return new VerificationToken(randomString);
    }

    private final String value;

    public VerificationToken(String token, Locale locale) throws InvalidVerificationTokenException {
        validate(token, locale);

        this.value = token;
    }

    private VerificationToken(String token) {
        this.value = token;
    }

    private static void validate(String token, Locale locale) throws InvalidVerificationTokenException {
        if (token == null || token.length() != VERIFICATION_TOKEN_LENGTH) {
            var message = String.format(CustomExceptionMessages.getMessage(TOKEN_MUST_HAVE_X_CHARACTERS, locale),
                    VERIFICATION_TOKEN_LENGTH);

            throw new InvalidVerificationTokenException(message);
        }

        var pattern = Pattern.compile(VERIFICATION_TOKEN_REGEX);

        var matcher = pattern.matcher(token);

        if (!matcher.matches()) {
            var message = CustomExceptionMessages.getMessage(TOKEN_MUST_HAVE_ONLY_ALPHANUMERIC_CHARACTERS, locale);

            throw new InvalidVerificationTokenException(message);
        }
    }
}
