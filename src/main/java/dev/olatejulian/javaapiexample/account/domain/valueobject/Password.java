package dev.olatejulian.javaapiexample.account.domain.valueobject;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import dev.olatejulian.javaapiexample.account.domain.exception.InvalidPasswordException;
import lombok.Getter;

public class Password {
    @Getter
    private String password;

    public Password(String password, Boolean hashed) throws InvalidPasswordException {
        if (hashed) {
            this.password = password;
        } else {
            validate(password);

            this.password = hash(password);
        }
    }

    private static Integer passwordMinLength() {
        return 8;
    }

    private static Integer passwordMaxLength() {
        return 255;
    }

    private static void validate(String password) throws InvalidPasswordException {
        if (password.length() < passwordMinLength() || password.length() > passwordMaxLength()) {
            throw new InvalidPasswordException("Invalid password length");
        }
    }

    private static String hash(String password) {
        var passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.encode(password);
    }

    public Boolean compare(String rawPassword) {
        var passwordEncoder = new BCryptPasswordEncoder();

        return passwordEncoder.matches(rawPassword, this.password);
    }
}
