package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.Locale;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;
import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import lombok.Value;

@Value
public final class AccountName {
    private static final String NAME_MUST_BE_LONGER_THAN_X_CHARACTERS = "account.account_name.name_must_be_longer_than_x_characters";

    private static final String NAME_MUST_BE_SHORTER_THAN_X_CHARACTERS = "account.account_name.name_must_be_shorter_than_x_characters";

    private static final int NAME_MIN_LENGTH = 3;

    private static final int NAME_MAX_LENGTH = 100;

    private final String value;

    public AccountName(String name, Locale locale) throws InvalidAccountNameException {
        validate(name, locale);

        this.value = name;
    }

    public AccountName(String name) throws InvalidAccountNameException {
        this(name, Locale.getDefault());
    }

    private static void validate(String name, Locale locale) throws InvalidAccountNameException {
        if (name == null || name.length() <= NAME_MIN_LENGTH) {
            var message = String.format(
                    CustomExceptionMessages.getMessage(NAME_MUST_BE_LONGER_THAN_X_CHARACTERS, locale),
                    NAME_MIN_LENGTH);

            throw new InvalidAccountNameException(message);
        }

        if (name.length() > NAME_MAX_LENGTH) {
            var message = String.format(
                    CustomExceptionMessages.getMessage(NAME_MUST_BE_SHORTER_THAN_X_CHARACTERS, locale),
                    NAME_MAX_LENGTH);

            throw new InvalidAccountNameException(message);
        }

    }
}
