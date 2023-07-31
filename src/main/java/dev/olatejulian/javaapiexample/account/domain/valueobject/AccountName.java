package dev.olatejulian.javaapiexample.account.domain.valueobject;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;
import lombok.Value;

@Value
public final class AccountName {
    private static final Integer NAME_MIN_LENGTH = 3;

    private static final Integer NAME_MAX_LENGTH = 100;

    private final String name;

    public AccountName(final String name) throws InvalidAccountNameException {
        validate(name);

        this.name = name;
    }

    private static void validate(final String name) throws InvalidAccountNameException {
        Object[][] errors = {
                {
                        name == null || name.length() <= NAME_MIN_LENGTH,
                        String.format("Account name must be longer than %d characters", NAME_MIN_LENGTH)
                },
                {
                        name == null || name.length() > NAME_MAX_LENGTH,
                        String.format("Account name cannot be longer than %d characters", NAME_MAX_LENGTH)
                }
        };

        for (Object[] error : errors) {
            if ((boolean) error[0]) {
                throw new InvalidAccountNameException((String) error[1]);
            }
        }
    }

}
