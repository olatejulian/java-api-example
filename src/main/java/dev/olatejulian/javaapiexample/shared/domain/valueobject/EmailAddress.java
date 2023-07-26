package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import org.apache.commons.validator.routines.EmailValidator;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class EmailAddress {
    @Getter
    private String emailAddress;

    public EmailAddress(String emailAddress) throws InvalidaEmailAddressException {
        validate(emailAddress);

        this.emailAddress = emailAddress;
    }

    private static void validate(String emailAddress) throws InvalidaEmailAddressException {
        var validator = EmailValidator.getInstance();

        if (!validator.isValid(emailAddress)) {
            throw new InvalidaEmailAddressException("Invalid email address.");
        }
    }

}
