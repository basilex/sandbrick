CREATE TABLE email_verification_token (
    id VARCHAR(24) PRIMARY KEY,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id VARCHAR(24) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    confirmed BOOLEAN NOT NULL DEFAULT FALSE
);

CREATE INDEX idx_email_verification_user_id ON email_verification_token(user_id);
CREATE INDEX idx_email_verification_expires_at ON email_verification_token(expires_at);
CREATE INDEX idx_email_verification_confirmed ON email_verification_token(confirmed);
