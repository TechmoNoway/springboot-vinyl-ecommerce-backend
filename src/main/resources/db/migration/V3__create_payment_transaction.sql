-- Flyway V3: create payment_transaction table to record payment history
CREATE TABLE IF NOT EXISTS `payment_transaction` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `order_id` VARCHAR(64) NOT NULL,
  `provider` VARCHAR(50) NOT NULL,
  `provider_transaction_id` VARCHAR(128),
  `idempotency_key` VARCHAR(128),
  `amount` BIGINT NOT NULL,
  `currency` VARCHAR(10) NOT NULL DEFAULT 'VND',
  `status` VARCHAR(30) NOT NULL,
  `payment_method` VARCHAR(50),
  `request_payload` JSON,
  `response_payload` JSON,
  `callback_payload` JSON,
  `attempt_count` INT NOT NULL DEFAULT 0,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `paid_at` TIMESTAMP NULL,
  INDEX idx_payment_order (order_id),
  INDEX idx_payment_provider_tx (provider, provider_transaction_id),
  INDEX idx_payment_idempotency (idempotency_key)
);
