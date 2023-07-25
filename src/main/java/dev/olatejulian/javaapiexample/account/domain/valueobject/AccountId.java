package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.UUID;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidAccountIdException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class AccountId {
    @Getter
    private final String id;

    public AccountId(String id) throws InvalidAccountIdException {
        validate(id);

        this.id = id;
    }

    private static void validate(String id) throws InvalidAccountIdException {
        try {

            UUID.fromString(id);
        } catch (IllegalArgumentException e) {
            throw new InvalidAccountIdException("Invalid account id.");
        }
    }

    public static AccountId generateId() {
        UUID accountUUID = UUID.randomUUID();
        try {
            var accountId = new AccountId(accountUUID.toString());

            return accountId;
        } catch (InvalidAccountIdException e) {
            return null;
        }

    }
}
