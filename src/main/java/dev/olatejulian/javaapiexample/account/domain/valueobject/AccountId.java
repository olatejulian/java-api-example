package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.UUID;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountIdException;
import lombok.Value;

@Value
public final class AccountId {
    private final String id;

    public AccountId(final String id) throws InvalidAccountIdException {
        validate(id);

        this.id = id;
    }

    private static void validate(final String id) throws InvalidAccountIdException {
        try {
            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountIdException("Invalid account id.");
        }
    }

    public static AccountId generateId() {
        try {
            return new AccountId(UUID.randomUUID().toString());
        } catch (InvalidAccountIdException e) {
            return null;
        }

    }
}
