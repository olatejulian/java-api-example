package dev.olatejulian.javaapiexample.account.domain;

import java.time.Duration;
import java.time.LocalDateTime;

import dev.olatejulian.javaapiexample.account.domain.exception.AccountEmailMustBeVerifiedException;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotResetPasswordException;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotVerifyAccountEmailException;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountId;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountName;
import dev.olatejulian.javaapiexample.account.domain.valueobject.Password;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.EmailAddress;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.VerificationToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public final class Account {
    private static final Integer VERIFICATION_TOKEN_EXPIRATION_TIME_IN_MINUTES = 15;

    @NonNull
    private AccountId id;

    @NonNull
    private AccountName name;

    @NonNull
    private EmailAddress emailAddress;

    private VerificationToken emailVerificationToken;

    private LocalDateTime emailVerificationTokenSentAt;

    @NonNull
    private Boolean emailAddressVerified;

    private LocalDateTime emailVerifiedAt;

    @NonNull
    private Password password;

    private VerificationToken passwordResetToken;

    private LocalDateTime passwordResetTokenSentAt;

    @NonNull
    private Boolean active;

    private LocalDateTime activatedAt;

    @NonNull
    private LocalDateTime createdAt;

    @NonNull
    private LocalDateTime updatedAt;

    public static Account create(final AccountName name, final EmailAddress emailAddress, final Password password) {
        return new Account(
                AccountId.generateId(),
                name,
                emailAddress,
                false,
                password,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());
    }

    public void updateDateTime() {
        this.updatedAt = LocalDateTime.now();
    }

    public void changeName(final AccountName name) {
        this.name = name;

        this.updateDateTime();
    }

    public void changeEmail(final EmailAddress emailAddress) {
        this.emailAddress = emailAddress;
        this.emailAddressVerified = false;
        this.emailVerificationToken = null;
        this.emailVerificationTokenSentAt = null;
        this.emailVerifiedAt = null;

        this.updateDateTime();
    }

    public VerificationToken generateEmailVerificationToken() {
        this.emailVerificationToken = VerificationToken.generateVerificationToken();

        this.emailVerificationTokenSentAt = LocalDateTime.now();

        this.updateDateTime();

        return this.emailVerificationToken;
    }

    public void verifyEmail(final VerificationToken token) throws CannotVerifyAccountEmailException {
        var isEmailVerificationTokenNull = this.emailVerificationToken == null;

        var isEmailVerificationTokenSentAtNull = this.emailVerificationTokenSentAt == null;

        if (isEmailVerificationTokenNull || isEmailVerificationTokenSentAtNull) {
            throw new CannotVerifyAccountEmailException("There is no verification token to verify");
        }

        var isEmailVerificationTokenExpired = Duration.between(this.emailVerificationTokenSentAt,
                LocalDateTime.now()).toMinutes() > VERIFICATION_TOKEN_EXPIRATION_TIME_IN_MINUTES;

        var isEmailVerificationTokenTheSame = this.emailVerificationToken.equals(token);

        if (isEmailVerificationTokenExpired || !isEmailVerificationTokenTheSame) {
            throw new CannotVerifyAccountEmailException("Invalid verification token");
        }

        this.emailAddressVerified = true;

        this.emailVerificationToken = null;

        this.emailVerifiedAt = LocalDateTime.now();

        this.updateDateTime();
    }

    public Boolean isEmailVerified() {
        return (this.emailAddressVerified && this.emailVerificationToken == null && this.emailVerifiedAt != null);
    }

    public VerificationToken generatePasswordResetToken() {
        this.passwordResetToken = VerificationToken.generateVerificationToken();

        this.passwordResetTokenSentAt = LocalDateTime.now();

        this.updateDateTime();

        return this.passwordResetToken;
    }

    public Boolean comparePassword(final String rawPassword) {
        return this.password.compare(rawPassword);
    }

    public void resetPassword(final Password password, final VerificationToken token)
            throws CannotResetPasswordException {
        var isPasswordResetTokenNull = this.passwordResetToken == null;

        var isPasswordResetTokenSentAtNull = this.passwordResetTokenSentAt == null;

        if (isPasswordResetTokenNull || isPasswordResetTokenSentAtNull) {
            throw new CannotResetPasswordException("There is no reset token to reset password");
        }

        var isPasswordResetTokenExpired = Duration.between(this.passwordResetTokenSentAt, LocalDateTime.now())
                .toMinutes() > VERIFICATION_TOKEN_EXPIRATION_TIME_IN_MINUTES;

        var isPasswordResetTokenTheSame = this.passwordResetToken.equals(token);

        if (isPasswordResetTokenExpired || !isPasswordResetTokenTheSame) {
            throw new CannotResetPasswordException("Invalid reset token");
        }

        this.password = password;

        this.passwordResetToken = null;

        this.updateDateTime();
    }

    public void activate() throws AccountEmailMustBeVerifiedException {
        if (Boolean.FALSE.equals(this.isEmailVerified())) {
            throw new AccountEmailMustBeVerifiedException("Email must be verified before activating account");
        }

        this.active = true;

        this.activatedAt = LocalDateTime.now();

        this.updateDateTime();
    }

    public Boolean isActivate() {
        return this.active && this.activatedAt != null;
    }

    public void deactivate() {
        if (Boolean.FALSE.equals(this.isActivate()))
            return;

        this.active = false;

        this.activatedAt = null;

        this.updateDateTime();
    }
}
