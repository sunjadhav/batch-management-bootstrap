USE `batch_management`;

CREATE TABLE IF NOT EXISTS `user` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `user_name`  varchar(128) NOT NULL,
  `password` varchar(128) NOT NULL,
   PRIMARY KEY (`id`)
  );

CREATE TABLE IF NOT EXISTS `batch` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_id` varchar(128) NOT NULL,
  `name` varchar(128) NOT NULL, 
  `timing` varchar(128) NOT NULL,
  `duration` varchar(128) NOT NULL,
  `started_at` bigint(20) NOT NULL,
  `is_active` varchar(128) NOT NULL,
  `is_archived` TINYINT(1) DEFAULT FALSE,
   PRIMARY KEY (`id`)
);

CREATE TABLE IF NOT EXISTS `student` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `batch_id` bigint(20) NOT NULL,
  `name` varchar(128) NOT NULL, 
  `surname` varchar(128) NOT NULL,
  `phone_number`  varchar(255) NOT NULL,
  `address` varchar(255) NOT NULL, 
  `college` varchar(255) DEFAULT NULL,
  `education`  varchar(128) DEFAULT NULL,
  `paid_amount` varchar(128) DEFAULT NULL,
  `pending_amount`  varchar(128) DEFAULT NULL,
   PRIMARY KEY (`id`),
   FOREIGN KEY (`batch_id`) REFERENCES  batch (`id`)
);