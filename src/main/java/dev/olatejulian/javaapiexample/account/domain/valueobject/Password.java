package dev.olatejulian.javaapiexample.account.domain.valueobject;

import java.util.regex.Pattern;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;
import lombok.Getter;

public final class Password {
    private static final Integer PASSWORD_MIN_LENGTH = 8;

    private static final Integer PASSWORD_MAX_LENGTH = 255;

    @Getter
    private String passwordValue;

    public Password(String password) throws InvalidPasswordException {
        validate(password);

        this.passwordValue = hash(password);
    }

    private Password(HashedPassword hashedPassword) {
        this.passwordValue = hashedPassword.getPassword();
    }

    public static final Password fromHashedString(String password) throws InvalidPasswordException {
        var hashedPassword = HashedPassword.of(password);

        if (hashedPassword == null)
            throw new InvalidPasswordException("Hash does not match bcrypt regex");

        return new Password(hashedPassword);
    }

    private static void validate(String password) throws InvalidPasswordException {
        if (password.length() < PASSWORD_MIN_LENGTH || password.length() > PASSWORD_MAX_LENGTH) {
            throw new InvalidPasswordException("Invalid password length");
        }
    }

    private static String hash(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public final Boolean compare(String rawPassword) {
        var passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(rawPassword, this.passwordValue);
    }
}

final class HashedPassword {
    private static final String BCRYPT_REGEX = "^\\$2[aby]\\$\\d{2}\\$[./0-9A-Za-z]{53}$";

    @Getter
    private String password;

    private HashedPassword(String password) {
        this.password = password;
    }

    public static final HashedPassword of(String password) {
        HashedPassword hashedPassword = null;

        if (validate(password)) {
            hashedPassword = new HashedPassword(password);
        }

        return hashedPassword;
    }

    private static boolean validate(String password) {
        var pattern = Pattern.compile(BCRYPT_REGEX);

        var matcher = pattern.matcher(password);

        return matcher.matches();
    }
}
