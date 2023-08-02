package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.Locale;
import java.util.UUID;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountIdException;
import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import lombok.Value;

@Value
public final class AccountId {
    private static final String ID_MUST_BE_A_VALID_UUID_STRING = "account.account_id.id_must_be_a_valid_uuid_string";

    public static AccountId generateId() {
        return new AccountId(UUID.randomUUID().toString());
    }

    private final String value;

    public AccountId(String id, Locale locale) throws InvalidAccountIdException {
        validate(id, locale);

        this.value = id;
    }

    private AccountId(final String id) {
        this.value = id;
    }

    private static boolean isValidUUID(String id) {
        var isValid = false;

        try {
            UUID.fromString(id);

            isValid = true;
        } catch (IllegalArgumentException e) {
            isValid = false;
        }

        return isValid;
    }

    private static void validate(String id, Locale locale) throws InvalidAccountIdException {
        if (!isValidUUID(id)) {
            var message = CustomExceptionMessages.getMessage(ID_MUST_BE_A_VALID_UUID_STRING,
                    locale);

            throw new InvalidAccountIdException(message);
        }
    }
}
