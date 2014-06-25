CREATE SCHEMA IF NOT EXISTS isucon2;

CREATE TABLE IF NOT EXISTS isucon2.artist (
  id   INT          NOT NULL AUTO_INCREMENT,
  name VARCHAR(255) NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS isucon2.ticket (
  id        INT          NOT NULL AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  artist_id INT          NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS isucon2.variation (
  id        INT          NOT NULL AUTO_INCREMENT,
  name      VARCHAR(255) NOT NULL,
  ticket_id INT          NOT NULL,
  PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS isucon2.stock (
  id           INT                            NOT NULL AUTO_INCREMENT,
  variation_id INT                            NOT NULL,
  seat_id      VARCHAR(255)                   NOT NULL,
  order_id     INT DEFAULT NULL,
  updated_at   TIMESTAMP AS CURRENT_TIMESTAMP NOT NULL,
  PRIMARY KEY (id)
);
ALTER TABLE isucon2.stock ADD CONSTRAINT IF NOT EXISTS isucon2.variation_seat UNIQUE (variation_id, seat_id);

CREATE TABLE IF NOT EXISTS isucon2.order_request (
  id        INT         NOT NULL AUTO_INCREMENT,
  member_id VARCHAR(32) NOT NULL,
  PRIMARY KEY (id)
);