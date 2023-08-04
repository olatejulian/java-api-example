package dev.olatejulian.javaapiexample.account.infra.repository;

import java.time.LocalDateTime;
import java.util.Locale;
import java.util.UUID;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "accounts")
@Entity(name = "accounts")
public class AccountSpringDataJpaModel {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private String name;

    @Column(unique = true)
    private String emailAddress;

    private String emailVerificationToken;

    private LocalDateTime emailVerificationTokenSentAt;

    private boolean emailAddressVerified;

    private LocalDateTime emailVerifiedAt;

    private String password;

    private String passwordResetToken;

    private LocalDateTime passwordResetTokenSentAt;

    private boolean active;

    private LocalDateTime activatedAt;

    private LocalDateTime createdAt;

    private LocalDateTime updatedAt;

    @Column(columnDefinition = "VARCHAR(20)")
    private Locale locale;
}
