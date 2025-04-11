-- ===================================
-- V2__schema_auth.sql
-- Auth schema: role, users, user_role, profile, token
-- ===================================

CREATE TABLE role (
    id VARCHAR(24) PRIMARY KEY,
    name VARCHAR(64) NOT NULL UNIQUE,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE users (
    id VARCHAR(24) PRIMARY KEY,
    username VARCHAR(64) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL
);

CREATE TABLE user_role (
    user_id VARCHAR(24) NOT NULL,
    role_id VARCHAR(24) NOT NULL,
    PRIMARY KEY (user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE
);

CREATE TABLE profile (
    id VARCHAR(24) PRIMARY KEY,
    user_id VARCHAR(24) NOT NULL UNIQUE,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone VARCHAR(32),
    avatar_url TEXT,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_profile_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

CREATE TABLE token (
    id VARCHAR(24) PRIMARY KEY,
    user_id VARCHAR(24) NOT NULL,
    token VARCHAR(512) NOT NULL UNIQUE,
    type VARCHAR(16) NOT NULL,
    expired BOOLEAN NOT NULL,
    revoked BOOLEAN NOT NULL,
    expiry_date TIMESTAMP WITH TIME ZONE NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE NOT NULL,
    updated_at TIMESTAMP WITH TIME ZONE NOT NULL,
    CONSTRAINT fk_token_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- === Roles ===
INSERT INTO role (id, name, created_at, updated_at) VALUES
  ('c7f0e0a1e9r0vlr3', 'ADMIN', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('c7f0e0a1e9r0vlr4', 'USER', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('c7f0e0a1e9r0vlr5', 'MANAGER', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('c7f0e0a1e9r0vlr6', 'OPERATOR', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('c7f0e0a1e9r0vlr7', 'REPORTER', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('c7f0e0a1e9r0vlr8', 'FINANCIER', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC');

-- === Users (admin + basilex) ===
INSERT INTO users (id, username, email, password, created_at, updated_at) VALUES
  ('c7f0e0a1e9r0vlu1', 'admin', 'admin@sandbrick.com', '$2a$10$szngJ1FYvX1UvBoGCUYLM.XU7Ia6yCKkctLjYOZHXBrC0Ye7Dbqre', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('c7f0e0a1e9r0vlu2', 'basilex', 'basilex@sandbrick.com', '$2a$10$uxfxiZhM/s99JtZJtfr6I.t61ntFz8zxEMKxVc7qN66xN6VJwvErq', CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC');

-- === User-Role Mapping ===
INSERT INTO user_role (user_id, role_id) VALUES
  ('c7f0e0a1e9r0vlu1', 'c7f0e0a1e9r0vlr3'), -- admin -> ADMIN
  ('c7f0e0a1e9r0vlu2', 'c7f0e0a1e9r0vlr4'); -- basilex -> USER
