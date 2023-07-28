package dev.olatejulian.javaapiexample.account.domain;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.time.Duration;
import java.time.LocalDateTime;

import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import dev.olatejulian.javaapiexample.account.domain.exception.AccountEmailMustBeVerifiedException;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotResetPasswordException;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotVerifyAccountEmailException;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountId;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountName;
import dev.olatejulian.javaapiexample.account.domain.valueobject.Password;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.EmailAddress;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.VerificationToken;

public class AccountTest {
    private static Account createRandomAccount() {
        try {
            var name = new AccountName(RandomStringUtils.randomAlphabetic(10));

            var emailAddress = new EmailAddress(RandomStringUtils.randomAlphabetic(10).concat("@email.com"));

            var password = new Password(RandomStringUtils.randomAlphanumeric(10));

            return Account.create(name, emailAddress, password);

        } catch (Exception e) {
            return null;
        }
    }

    @Test
    void testAccount() {
        assertDoesNotThrow(() -> {
            new Account(
                    AccountId.generateId(),
                    new AccountName("John Doe"),
                    new EmailAddress("john.doe@email.com"),
                    false,
                    new Password("JoeDoePassword"),
                    false,
                    LocalDateTime.now(),
                    LocalDateTime.now());
        });
    }

    @Test
    void testCreate() {
        Assertions
                .assertDoesNotThrow(
                        () -> Account.create(new AccountName("John Doe"), new EmailAddress("john.doe@email.com"),
                                new Password("JoeDoePassword")));

    }

    @Test
    void testUpdateDateTime() {
        var account = createRandomAccount();

        var updatedAt = account.getUpdatedAt();

        account.updateDateTime();

        var newUpdatedAt = account.getUpdatedAt();

        assertNotEquals(updatedAt, newUpdatedAt);

        assertTrue(Duration.between(updatedAt, newUpdatedAt).toNanos() > 0);

    }

    @Test
    void testChangeName() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var newName = new AccountName(RandomStringUtils.randomAlphabetic(10));

            account.changeName(newName);

            assertEquals(newName, account.getName());
        });
    }

    @Test
    void testChangeEmail() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var newEmailAddress = new EmailAddress("john.doe@email.com");

            account.changeEmail(newEmailAddress);

            assertEquals(newEmailAddress, account.getEmailAddress());
        });
    }

    @Test
    void testGenerateEmailVerificationToken() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            assertNotNull(emailVerificationToken);
        });
    }

    @Test
    void testVerifyEmail() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            account.verifyEmail(emailVerificationToken);
        });
    }

    @Test
    void testVerifyEmailWhenTokenIsInvalid() {
        var account = createRandomAccount();

        account.generateEmailVerificationToken();

        var emailVerificationToken = VerificationToken.generateVerificationToken();

        assertThrows(CannotVerifyAccountEmailException.class, () -> {
            account.verifyEmail(emailVerificationToken);
        });

        assertThrows(CannotVerifyAccountEmailException.class, () -> {
            account.verifyEmail(null);
        });
    }

    @Test
    void testIsEmailVerified() {
        var account = createRandomAccount();

        assertFalse(account.isEmailVerified());

        var emailVerificationToken = account.generateEmailVerificationToken();

        assertDoesNotThrow(() -> {
            account.verifyEmail(emailVerificationToken);
        });

        assertTrue(account.isEmailVerified());
    }

    @Test
    void testGeneratePasswordResetToken() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var passwordResetToken = account.generatePasswordResetToken();

            assertNotNull(passwordResetToken);
        });
    }

    @Test
    void testResetPassword() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var passwordResetToken = account.generatePasswordResetToken();

            var stringNewPassword = "newPassword";

            var newPassword = new Password(stringNewPassword);

            assertFalse(account.comparePassword(stringNewPassword));

            account.resetPassword(newPassword, passwordResetToken);

            assertTrue(account.comparePassword(stringNewPassword));
        });
    }

    @Test
    void testComparePassword() {
        assertDoesNotThrow(() -> {
            var passwordString = "password";

            var account = Account.create(new AccountName("John Doe"), new EmailAddress("john.doe@email.com"),
                    new Password(passwordString));

            assertTrue(account.comparePassword(passwordString));

            assertFalse(account.comparePassword("wrongPassword"));
        });
    }

    @Test
    void testResetPasswordWhenTokenIsInvalid() {
        assertThrows(CannotResetPasswordException.class, () -> {
            var account = createRandomAccount();

            account.generatePasswordResetToken();

            var stringNewPassword = "newPassword";

            var newPassword = new Password(stringNewPassword);

            assertFalse(account.comparePassword(stringNewPassword));

            account.resetPassword(newPassword, VerificationToken.generateVerificationToken());

            assertTrue(account.comparePassword(stringNewPassword));
        });
    }

    @Test
    void testActivate() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            account.verifyEmail(emailVerificationToken);

            account.activate();
        });
    }

    @Test
    void testActivateWhenEmailIsNotVerified() {
        assertThrows(AccountEmailMustBeVerifiedException.class, () -> {
            var account = createRandomAccount();

            account.activate();
        });
    }

    @Test
    void testDeactivate() {
        assertDoesNotThrow(() -> {
            var account = createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            account.verifyEmail(emailVerificationToken);

            account.activate();

            account.deactivate();
        });
    }

}
