package dev.olatejulian.javaapiexample.account.domain;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Locale;

import dev.olatejulian.javaapiexample.account.domain.exception.AccountEmailMustBeVerifiedException;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotResetPasswordException;
import dev.olatejulian.javaapiexample.account.domain.exception.CannotVerifyAccountEmailException;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountId;
import dev.olatejulian.javaapiexample.account.domain.valueobject.AccountName;
import dev.olatejulian.javaapiexample.account.domain.valueobject.Password;
import dev.olatejulian.javaapiexample.shared.common.CustomExceptionMessages;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.EmailAddress;
import dev.olatejulian.javaapiexample.shared.domain.valueobject.VerificationToken;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public final class Account {
    private static final String EMAIL_ADDRESS_ALREADY_VERIFIED = "account.email_address_already_verified";

    private static final String THERE_IS_NO_TOKEN_TO_VERIFY = "account.there_is_no_token_to_verify";

    private static final String INVALID_TOKEN_OR_TOKEN_HAS_EXPIRED = "account.invalid_token_or_token_has_expired";

    private static final String ACCOUNT_EMAIL_MUST_BE_VERIFIED = "account.account_email_must_be_verified";

    private static final int VERIFICATION_TOKEN_EXPIRATION_TIME_IN_MINUTES = 15;

    public static Account create(final AccountName name, final EmailAddress emailAddress, final Password password) {
        var account = new Account(
                AccountId.generateId(),
                name,
                emailAddress,
                false,
                password,
                false,
                LocalDateTime.now(),
                LocalDateTime.now());

        account.setLocale(Locale.getDefault());

        return account;
    }

    @NonNull
    private final AccountId id;

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
    private final LocalDateTime createdAt;

    @NonNull
    private LocalDateTime updatedAt;

    @Setter
    private Locale locale;

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

    public boolean isEmailVerified() {
        return (this.emailAddressVerified && this.emailVerificationToken == null && this.emailVerifiedAt != null);
    }

    public void verifyEmail(final VerificationToken token) throws CannotVerifyAccountEmailException {
        if (this.isEmailVerified()) {
            var message = CustomExceptionMessages.getMessage(EMAIL_ADDRESS_ALREADY_VERIFIED, this.locale);

            throw new CannotVerifyAccountEmailException(message);
        }

        var isEmailVerificationTokenNull = this.emailVerificationToken == null;

        var isEmailVerificationTokenSentAtNull = this.emailVerificationTokenSentAt == null;

        if (isEmailVerificationTokenNull || isEmailVerificationTokenSentAtNull) {
            var message = CustomExceptionMessages.getMessage(THERE_IS_NO_TOKEN_TO_VERIFY, this.locale);

            throw new CannotVerifyAccountEmailException(message);
        }

        var isEmailVerificationTokenExpired = Duration.between(this.emailVerificationTokenSentAt,
                LocalDateTime.now()).toMinutes() > VERIFICATION_TOKEN_EXPIRATION_TIME_IN_MINUTES;

        var isEmailVerificationTokenTheSame = this.emailVerificationToken.equals(token);

        if (isEmailVerificationTokenExpired || !isEmailVerificationTokenTheSame) {
            var message = CustomExceptionMessages.getMessage(INVALID_TOKEN_OR_TOKEN_HAS_EXPIRED, locale);

            throw new CannotVerifyAccountEmailException(message);
        }

        this.emailAddressVerified = true;

        this.emailVerificationToken = null;

        this.emailVerifiedAt = LocalDateTime.now();

        this.updateDateTime();
    }

    public VerificationToken generatePasswordResetToken() {
        this.passwordResetToken = VerificationToken.generateVerificationToken();

        this.passwordResetTokenSentAt = LocalDateTime.now();

        this.updateDateTime();

        return this.passwordResetToken;
    }

    public boolean comparePassword(final String rawPassword) {
        return this.password.compare(rawPassword);
    }

    public void resetPassword(final Password password, final VerificationToken token)
            throws CannotResetPasswordException {
        var isPasswordResetTokenNull = this.passwordResetToken == null;

        var isPasswordResetTokenSentAtNull = this.passwordResetTokenSentAt == null;

        if (isPasswordResetTokenNull || isPasswordResetTokenSentAtNull) {
            var message = CustomExceptionMessages.getMessage(THERE_IS_NO_TOKEN_TO_VERIFY, this.locale);

            throw new CannotResetPasswordException(message);
        }

        var isPasswordResetTokenExpired = Duration.between(this.passwordResetTokenSentAt, LocalDateTime.now())
                .toMinutes() > VERIFICATION_TOKEN_EXPIRATION_TIME_IN_MINUTES;

        var isPasswordResetTokenTheSame = this.passwordResetToken.equals(token);

        if (isPasswordResetTokenExpired || !isPasswordResetTokenTheSame) {
            var message = CustomExceptionMessages.getMessage(INVALID_TOKEN_OR_TOKEN_HAS_EXPIRED, locale);

            throw new CannotResetPasswordException(message);
        }

        this.password = password;

        this.passwordResetToken = null;

        this.updateDateTime();
    }

    public void activate() throws AccountEmailMustBeVerifiedException {
        if (Boolean.FALSE.equals(this.isEmailVerified())) {
            var message = CustomExceptionMessages.getMessage(ACCOUNT_EMAIL_MUST_BE_VERIFIED, this.locale);

            throw new AccountEmailMustBeVerifiedException(message);
        }

        this.active = true;

        this.activatedAt = LocalDateTime.now();

        this.updateDateTime();
    }

    public boolean isActivate() {
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
