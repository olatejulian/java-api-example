CREATE TABLE IF NOT EXISTS accounts (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    email_address VARCHAR(255) UNIQUE NOT NULL,
    email_verification_token VARCHAR(255),
    email_verification_token_sent_at TIMESTAMP,
    email_address_verified BOOLEAN NOT NULL,
    email_verified_at TIMESTAMP,
    password VARCHAR(255) NOT NULL,
    password_reset_token VARCHAR(255),
    password_reset_token_sent_at TIMESTAMP,
    active BOOLEAN NOT NULL,
    activated_at TIMESTAMP,
    created_at TIMESTAMP,
    updated_at TIMESTAMP
);