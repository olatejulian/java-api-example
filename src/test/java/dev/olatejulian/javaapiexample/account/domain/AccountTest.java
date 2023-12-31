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

class AccountTest {
    @Test
    void testAccount() {
        assertDoesNotThrow(() -> {
            new Account(
                    AccountId.generateId(),
                    new AccountName("John Doe"),
                    new EmailAddress("john.doe@email.com"),
                    false,
                    new Password("JoeDoePassword@1"),
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
                                new Password("JoeDoePassword@1")));

    }

    @Test
    void testUpdateDateTime() {
        var account = AccountUtils.createRandomAccount();

        var updatedAt = account.getUpdatedAt();

        account.updateDateTime();

        var newUpdatedAt = account.getUpdatedAt();

        assertNotEquals(updatedAt, newUpdatedAt);

        assertTrue(Duration.between(updatedAt, newUpdatedAt).toNanos() > 0);

    }

    @Test
    void testChangeName() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var newName = new AccountName(RandomStringUtils.randomAlphabetic(10));

            account.changeName(newName);

            assertEquals(newName, account.getName());
        });
    }

    @Test
    void testChangeEmail() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var newEmailAddress = new EmailAddress("john.doe@email.com");

            account.changeEmail(newEmailAddress);

            assertEquals(newEmailAddress, account.getEmailAddress());
        });
    }

    @Test
    void testGenerateEmailVerificationToken() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            assertNotNull(emailVerificationToken);
        });
    }

    @Test
    void testVerifyEmail() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            account.verifyEmail(emailVerificationToken);
        });
    }

    @Test
    void testVerifyEmailWhenTokenIsInvalid() {
        var account = AccountUtils.createRandomAccount();

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
        var account = AccountUtils.createRandomAccount();

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
            var account = AccountUtils.createRandomAccount();

            var passwordResetToken = account.generatePasswordResetToken();

            assertNotNull(passwordResetToken);
        });
    }

    @Test
    void testResetPassword() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var passwordResetToken = account.generatePasswordResetToken();

            var stringNewPassword = "newPassword1@";

            var newPassword = new Password(stringNewPassword);

            assertFalse(account.comparePassword(stringNewPassword));

            account.resetPassword(newPassword, passwordResetToken);

            assertTrue(account.comparePassword(stringNewPassword));
        });
    }

    @Test
    void testComparePassword() {
        assertDoesNotThrow(() -> {
            var passwordString = "passwordA1@";

            var account = Account.create(new AccountName("John Doe"), new EmailAddress("john.doe@email.com"),
                    new Password(passwordString));

            assertTrue(account.comparePassword(passwordString));

            assertFalse(account.comparePassword("wrongPassword"));
        });
    }

    @Test
    void testResetPasswordWhenTokenIsInvalid() {
        assertThrows(CannotResetPasswordException.class, () -> {
            var account = AccountUtils.createRandomAccount();

            account.generatePasswordResetToken();

            var stringNewPassword = "newPassword1@";

            var newPassword = new Password(stringNewPassword);

            assertFalse(account.comparePassword(stringNewPassword));

            account.resetPassword(newPassword, VerificationToken.generateVerificationToken());

            assertTrue(account.comparePassword(stringNewPassword));
        });
    }

    @Test
    void testActivate() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            account.verifyEmail(emailVerificationToken);

            account.activate();
        });
    }

    @Test
    void testActivateWhenEmailIsNotVerified() {
        assertThrows(AccountEmailMustBeVerifiedException.class, () -> {
            var account = AccountUtils.createRandomAccount();

            account.activate();
        });
    }

    @Test
    void testDeactivate() {
        assertDoesNotThrow(() -> {
            var account = AccountUtils.createRandomAccount();

            var emailVerificationToken = account.generateEmailVerificationToken();

            account.verifyEmail(emailVerificationToken);

            account.activate();

            account.deactivate();
        });
    }
}
