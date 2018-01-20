CREATE TABLE DISCORD_USER (
  id               BIGINT       NOT NULL AUTO_INCREMENT PRIMARY KEY,
  discord_id       BIGINT       NOT NULL,
  discord_username VARCHAR(255) NOT NULL
);