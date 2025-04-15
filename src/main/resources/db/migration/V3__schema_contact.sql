-- ===================================
-- V3__schema_contact.sql
-- Contact schema: contact
-- ===================================

-- === 1. Create ENUM contact_type ===
DO $$ BEGIN
    CREATE TYPE contact_type AS ENUM (
        'EMAIL', 'PHONE', 'TELEGRAM', 'WHATSAPP', 'VIBER', 'SIGNAL', 'OTHER'
    );
EXCEPTION
    WHEN duplicate_object THEN NULL;
END $$;

-- === 2. Create contact table ===
CREATE TABLE contact (
    id VARCHAR(24) PRIMARY KEY,
    user_id VARCHAR(24) NOT NULL,
    type contact_type NOT NULL,
    content TEXT NOT NULL,
    preferrable BOOLEAN NOT NULL DEFAULT FALSE,
    created_at TIMESTAMPTZ NOT NULL,
    updated_at TIMESTAMPTZ NOT NULL,
    CONSTRAINT fk_contact_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);

-- === 3. Drop legacy fields ===
ALTER TABLE profile DROP COLUMN IF EXISTS phone;
ALTER TABLE users DROP COLUMN IF EXISTS email;

-- === 4. Indexes for performance and constraints ===

-- Fast lookups for preferred contacts
CREATE INDEX idx_contact_user_preferrable ON contact(user_id) WHERE preferrable = true;

-- Unique constraints by (user_id, type, content)
CREATE UNIQUE INDEX idx_contact_unique_user_email
  ON contact(user_id, type, content)
  WHERE type = 'EMAIL';

CREATE UNIQUE INDEX idx_contact_unique_user_phone
  ON contact(user_id, type, content)
  WHERE type = 'PHONE';

CREATE UNIQUE INDEX idx_contact_unique_user_telegram
  ON contact(user_id, type, content)
  WHERE type = 'TELEGRAM';

CREATE UNIQUE INDEX idx_contact_unique_user_whatsapp
  ON contact(user_id, type, content)
  WHERE type = 'WHATSAPP';

CREATE UNIQUE INDEX idx_contact_unique_user_viber
  ON contact(user_id, type, content)
  WHERE type = 'VIBER';

CREATE UNIQUE INDEX idx_contact_unique_user_signal
  ON contact(user_id, type, content)
  WHERE type = 'SIGNAL';

CREATE UNIQUE INDEX idx_contact_unique_user_other
  ON contact(user_id, type, content)
  WHERE type = 'OTHER';

-- === 5. Default contacts for initial users ===
INSERT INTO contact (id, user_id, type, content, preferrable, created_at, updated_at)
VALUES
  -- Admin email
  ('ct_admin_001', 'c7f0e0a1e9r0vlu1', 'EMAIL', 'admin@sandbrick.com.ua', true, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),

  -- Basilex email & phone
  ('ct_basilex_email_001', 'c7f0e0a1e9r0vlu2', 'EMAIL', 'alexander.vasilenko@gmail.com', true, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC'),
  ('ct_basilex_phone_001', 'c7f0e0a1e9r0vlu2', 'PHONE', '+380952066922', true, CURRENT_TIMESTAMP AT TIME ZONE 'UTC', CURRENT_TIMESTAMP AT TIME ZONE 'UTC');