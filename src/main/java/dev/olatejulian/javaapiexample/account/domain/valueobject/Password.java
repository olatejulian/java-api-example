package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.Locale;
import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;
import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import lombok.Value;

@Value
public final class Password {
    private static final String PASSWORD_MUST_BE_LONGER_THAN_X_CHARACTERS = "account.password.password_must_be_longer_than_x_characters";

    private static final String PASSWORD_INVALID_FORMAT = "account.password.password_invalid_format";

    private static final int PASSWORD_MIN_LENGTH = 8;

    private static final String PASSWORD_REGEX = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&+=])(?=\\S+$).{8,}$";

    public static Password fromHashedString(String password) throws InvalidPasswordException {
        var hashedPassword = HashedPassword.of(password);

        if (hashedPassword == null)
            throw new InvalidPasswordException("Hash does not match bcrypt regex");

        return new Password(hashedPassword);
    }

    private final String value;

    public Password(String password, Locale locale) throws InvalidPasswordException {
        validate(password, locale);

        this.value = hash(password);
    }

    public Password(String password) throws InvalidPasswordException {
        this(password, Locale.getDefault());
    }

    private Password(HashedPassword hashedPassword) {
        this.value = hashedPassword.getPassword();
    }

    public boolean compare(String rawPassword) {
        var passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(rawPassword, this.value);
    }

    private static String hash(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    private static void validate(String password, Locale locale) throws InvalidPasswordException {
        if (password == null || password.length() <= PASSWORD_MIN_LENGTH) {
            var message = String.format(
                    CustomExceptionMessages.getMessage(PASSWORD_MUST_BE_LONGER_THAN_X_CHARACTERS, locale),
                    PASSWORD_MIN_LENGTH);

            throw new InvalidPasswordException(message);
        }

        var pattern = Pattern.compile(PASSWORD_REGEX);

        var matcher = pattern.matcher(password);

        if (!matcher.matches()) {
            var message = CustomExceptionMessages.getMessage(PASSWORD_INVALID_FORMAT, locale);

            throw new InvalidPasswordException(message);
        }
    }

    @Value
    private static final class HashedPassword {
        private static final String BCRYPT_REGEX = "^\\$2[aby]\\$\\d{2}\\$[./0-9A-Za-z]{53}$";

        private final String password;

        private HashedPassword(String password) {
            this.password = password;
        }

        public static final HashedPassword of(String password) {
            HashedPassword hashedPassword = null;

            if (validate(password)) {
                hashedPassword = new Password.HashedPassword(password);
            }

            return hashedPassword;
        }

        private static boolean validate(String password) {
            var pattern = Pattern.compile(BCRYPT_REGEX);

            var matcher = pattern.matcher(password);

            return matcher.matches();
        }
    }
}
