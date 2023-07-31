package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;
import lombok.Value;
import org.apache.commons.validator.routines.EmailValidator;

@Value
public class EmailAddress {
    private final String address;

    public EmailAddress(String address) throws InvalidaEmailAddressException {
        validate(address);

        this.address = address;
    }

    private static void validate(String emailAddress) throws InvalidaEmailAddressException {
        var validator = EmailValidator.getInstance();

        if (!validator.isValid(emailAddress)) {
            throw new InvalidaEmailAddressException("Invalid email address.");
        }
    }

}
