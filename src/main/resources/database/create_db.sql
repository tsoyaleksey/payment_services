CREATE DATABASE payservice CHARACTER SET utf8 COLLATE utf8_general_ci;
USE payservice;


CREATE TABLE regions
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  region_name   VARCHAR(100)          NOT NULL
);

CREATE TABLE wallets
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  balance       DOUBLE DEFAULT '0.0'
);

CREATE TABLE roles
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  role_name     VARCHAR(100)          NOT NULL
);

CREATE TABLE categories
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  category_name VARCHAR(100)          NOT NULL
);

CREATE TABLE providers
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  provider_name VARCHAR(100)          NOT NULL,
  logotype      MEDIUMBLOB,
  category_id   INT(11)               NOT NULL,
  CONSTRAINT providers_categories_id_fk FOREIGN KEY (category_id) REFERENCES categories (id)
);
CREATE INDEX providers_categories_id_fk ON providers (category_id);

CREATE TABLE providers_regions
(
  provider_id   INT(11)               NOT NULL,
  region_id     INT(11)               NOT NULL,
  CONSTRAINT providers_regions_providers_id_fk FOREIGN KEY (provider_id) REFERENCES providers (id),
  CONSTRAINT providers_regions_regions_id_fk   FOREIGN KEY (region_id)   REFERENCES regions (id)
);
CREATE INDEX providers_regions_providers_id_fk ON providers_regions (provider_id);
CREATE INDEX providers_regions_regions_id_fk ON providers_regions (region_id);

CREATE TABLE users
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  phone_number  VARCHAR(18)           NOT NULL,
  user_name     VARCHAR(100)          NOT NULL,
  date_of_birth DATE                  NOT NULL,
  password      VARCHAR(255)          NOT NULL,
  wallet_id     INT(11)               NOT NULL,
  region_id     INT(11) DEFAULT '1',
  role_id       INT(11) DEFAULT '2',
  CONSTRAINT users_wallets_id_fk FOREIGN KEY (wallet_id) REFERENCES wallets (id),
  CONSTRAINT users_regions_id_fk FOREIGN KEY (region_id) REFERENCES regions (id),
  CONSTRAINT users_roles_id_fk   FOREIGN KEY (role_id)   REFERENCES roles (id)
);
CREATE INDEX users_wallets_id_fk ON users (wallet_id);
CREATE INDEX users_regions_id_fk ON users (region_id);
CREATE INDEX users_roles_id_fk   ON users (role_id);

CREATE TABLE payment_history
(
  id            INT(11) PRIMARY KEY   NOT NULL AUTO_INCREMENT,
  number        VARCHAR(20)           NOT NULL,
  sum           DOUBLE                NOT NULL,
  date          DATETIME              NOT NULL,
  user_id       INT(11)               NOT NULL,
  provider_id   INT(11)               NOT NULL,
  CONSTRAINT payment_history_users_id_fk FOREIGN KEY (user_id) REFERENCES users (id),
  CONSTRAINT payment_history_providers_id_fk FOREIGN KEY (provider_id) REFERENCES providers (id)
);
CREATE INDEX payment_history_users_id_fk ON payment_history (user_id);
CREATE INDEX payment_history_providers_id_fk ON payment_history (provider_id);