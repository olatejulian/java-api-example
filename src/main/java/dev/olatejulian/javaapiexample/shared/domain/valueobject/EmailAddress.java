package dev.olatejulian.javaapiexample.shared.domain.valueobject;

import java.util.Locale;

import org.apache.commons.validator.routines.EmailValidator;

import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import dev.olatejulian.javaapiexample.shared.domain.exception.InvalidaEmailAddressException;
import lombok.Value;

@Value
public final class EmailAddress {
    private static final String INVALID_FORMAT = "shared.email_address.invalid_format";

    private final String value;

    public EmailAddress(String address, Locale locale) throws InvalidaEmailAddressException {
        validate(address, locale);

        this.value = address;
    }

    public EmailAddress(String address) throws InvalidaEmailAddressException {
        validate(address, Locale.getDefault());

        this.value = address;
    }

    private static void validate(String emailAddress, Locale locale) throws InvalidaEmailAddressException {
        var validator = EmailValidator.getInstance();

        if (!validator.isValid(emailAddress)) {
            var message = CustomExceptionMessages.getMessage(INVALID_FORMAT, locale);

            throw new InvalidaEmailAddressException(message);
        }
    }
}
