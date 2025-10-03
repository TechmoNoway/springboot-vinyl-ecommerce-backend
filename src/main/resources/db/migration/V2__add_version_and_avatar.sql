-- Flyway V2: add version and stock_quantity to product, add avatar to user
-- This migration is written to be safe on MySQL 8+ (uses IF NOT EXISTS for columns)

ALTER TABLE `product`
  ADD COLUMN IF NOT EXISTS `stock_quantity` BIGINT NOT NULL DEFAULT 0,
  ADD COLUMN IF NOT EXISTS `version` BIGINT NOT NULL DEFAULT 0;

ALTER TABLE `user`
  ADD COLUMN IF NOT EXISTS `avatar` VARCHAR(1024);

-- Ensure orders/order_item tables exist (no-op if already created in V1)
CREATE TABLE IF NOT EXISTS `orders` (
  `id` VARCHAR(64) PRIMARY KEY,
  `customer_id` BIGINT,
  `fullname` VARCHAR(255),
  `email` VARCHAR(255),
  `customer_address` VARCHAR(512),
  `customer_phone` VARCHAR(50),
  `note` TEXT,
  `total_price` BIGINT,
  `status` VARCHAR(50),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE IF NOT EXISTS `order_item` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `order_id` VARCHAR(64),
  `product_id` BIGINT,
  `quantity` INT,
  `price` BIGINT
);
