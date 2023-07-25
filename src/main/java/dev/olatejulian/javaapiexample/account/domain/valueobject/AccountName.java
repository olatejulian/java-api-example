package dev.olatejulian.javaapiexample.account.domain.valueobject;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountNameException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class AccountName {
    @Getter
    private String name;

    public AccountName(String name) throws InvalidAccountNameException {
        validate(name);

        this.name = name;
    }

    private static void validate(String name) throws InvalidAccountNameException {
        if (name == null || name.isEmpty()) {
            throw new InvalidAccountNameException("Account name cannot be empty");
        }

        if (name.length() <= 3) {
            throw new InvalidAccountNameException("Account name must be longer than 3 characters");
        }

        if (name.length() > 20) {
            throw new InvalidAccountNameException("Account name cannot be longer than 20 characters");
        }
    }

}
