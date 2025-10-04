-- Flyway migration: create product and user tables with necessary columns

CREATE TABLE IF NOT EXISTS `product` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `title` VARCHAR(255) NOT NULL,
  `price` BIGINT NOT NULL DEFAULT 0,
  `stock_quantity` BIGINT NOT NULL DEFAULT 0,
  `poster_url` VARCHAR(512),
  `region` VARCHAR(100),
  `artist` VARCHAR(255),
  `release_year` VARCHAR(10),
  `status` VARCHAR(50),
  `platform` VARCHAR(50),
  `set` VARCHAR(50),
  `demo_audio_url` VARCHAR(512),
  `studio_name` VARCHAR(255),
  `manufacture_year` VARCHAR(10),
  `stock_status` VARCHAR(50),
  `description` TEXT,
  `mood` VARCHAR(100),
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  `updated_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `tracklist_id` BIGINT,
  `version` BIGINT NOT NULL DEFAULT 0
);

CREATE TABLE IF NOT EXISTS `user` (
  `id` BIGINT AUTO_INCREMENT PRIMARY KEY,
  `email` VARCHAR(255) UNIQUE,
  `fullname` VARCHAR(255),
  `avatar` VARCHAR(1024),
  `password` VARCHAR(255),
  `role_id` INT,
  `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- order and order_item tables (minimal schema)
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
