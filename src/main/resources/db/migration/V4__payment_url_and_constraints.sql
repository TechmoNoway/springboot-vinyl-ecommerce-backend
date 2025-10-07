-- Flyway V4: add payment_url column and uniqueness constraints
ALTER TABLE payment_transaction
  ADD COLUMN IF NOT EXISTS payment_url VARCHAR(1024);

-- Ensure provider_transaction_id uniqueness per provider
CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_provider_tx ON payment_transaction (provider, provider_transaction_id);

-- Optionally ensure idempotency_key uniqueness per order (if desired)
CREATE UNIQUE INDEX IF NOT EXISTS idx_unique_idempotency ON payment_transaction (order_id, idempotency_key);
