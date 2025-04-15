-- V4__create_reset_token_table.sql

CREATE TABLE reset_token (
    id VARCHAR(24) PRIMARY KEY,
    token VARCHAR(255) NOT NULL UNIQUE,
    user_id VARCHAR(24) NOT NULL REFERENCES users(id) ON DELETE CASCADE,
    used BOOLEAN NOT NULL DEFAULT FALSE,
    expires_at TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE INDEX idx_reset_token_user_id ON reset_token(user_id);
CREATE INDEX idx_reset_token_expires_at ON reset_token(expires_at);
CREATE INDEX idx_reset_token_used ON reset_token(used);