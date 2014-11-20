mysql -u root -p
CREATE DATABASE db_learnbyheart;
CREATE USER 'learnbyheart'@'localhost' IDENTIFIED BY 'lbhpwd';
GRANT ALL ON db_learnbyheart.* TO 'learnbyheart'@'localhost';
FLUSH PRIVILEGES;
mysql -u learnbyheart -p
use db_learnbyheart

CREATE TABLE IF NOT EXISTS `user_type` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `type` text NOT NULL,
  PRIMARY KEY (`_id`)
)

CREATE TABLE IF NOT EXISTS `user` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `login` text NOT NULL,
  `password` text NOT NULL,
  `email` text NOT NULL,
  `user_type` bigint(20) NOT NULL,
  PRIMARY KEY (`_id`),
  FOREIGN KEY (`user_type`) REFERENCES `user_type`(`_id`)
)


CREATE TABLE IF NOT EXISTS `language` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `description` text NOT NULL,
  `android_code` text NOT NULL,
  PRIMARY KEY (`_id`)
)

CREATE TABLE IF NOT EXISTS `dictionary` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `is_public` boolean NOT NULL,
  `user_id` bigint(20) NOT NULL,
  `language_id` bigint(20) NOT NULL,
  PRIMARY KEY (`_id`),
  FOREIGN KEY (`user_id`) REFERENCES `user`(`_id`),
  FOREIGN KEY (`language_id`) REFERENCES `language`(`_id`)
)

CREATE TABLE IF NOT EXISTS `word` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `name` text NOT NULL,
  `dictionary_id` bigint(20) NOT NULL,
  PRIMARY KEY (`_id`),
  FOREIGN KEY (`dictionary_id`) REFERENCES `dictionary`(`_id`)
)

CREATE TABLE IF NOT EXISTS `meaning` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `meaning` text NOT NULL,
  `word_id` bigint(20) NOT NULL,
  PRIMARY KEY (`_id`),
  FOREIGN KEY (`word_id`) REFERENCES `word`(`_id`)
)

CREATE TABLE IF NOT EXISTS `example` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `example` text NOT NULL,
  `meaning_id` bigint(20) NOT NULL,
  PRIMARY KEY (`_id`),
  FOREIGN KEY (`meaning_id`) REFERENCES `meaning`(`_id`)
)

CREATE TABLE IF NOT EXISTS `notification` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `notification` text NOT NULL,
  PRIMARY KEY (`_id`)
)

CREATE TABLE IF NOT EXISTS `log_example_notification` (
  `_id` bigint(20) NOT NULL AUTO_INCREMENT,
  `result` text NOT NULL,
  `date` datetime NOT NULL,
  `example_id` bigint(20) NOT NULL,
  `notification_id` bigint(20) NOT NULL,
  PRIMARY KEY (`_id`),
  FOREIGN KEY (`example_id`) REFERENCES `example`(`_id`),
  FOREIGN KEY (`notification_id`) REFERENCES `notification`(`_id`)
)

insert into user_type values(1, 'padrao');
INSERT INTO `db_learnbyheart`.`language` (`description`, `android_code`) VALUES ('English', 'en'), ('French', 'fr'), ('Portuguese', 'pt'), ('Spanish', 'es');
