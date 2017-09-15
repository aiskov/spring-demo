/* Client Module */
CREATE TABLE IF NOT EXISTS `client` (
  `id`        VARCHAR(36),
  `state`     VARCHAR(7) NOT NULL,
  `updated`   TIMESTAMP(3) NOT NULL,
  `created`   TIMESTAMP(3) NOT NULL,

  `name`      VARCHAR(100) NOT NULL,

  `version`   BIGINT,

  CONSTRAINT pk_reader_id PRIMARY KEY (`id`)
) ENGINE=InnoDB;

CREATE INDEX client_state_ind ON `client`(`state`);
CREATE INDEX client_name_ind ON `client`(`name`);

CREATE TABLE IF NOT EXISTS `address` (
  `id`        VARCHAR(36),
  `state`     VARCHAR(7) NOT NULL,
  `updated`   TIMESTAMP(3) NOT NULL,
  `created`   TIMESTAMP(3) NOT NULL,

  `street`    VARCHAR(100),
  `index`     VARCHAR(100),
  `city`      VARCHAR(100),
  `country`   VARCHAR(100),

  `client_id` VARCHAR(36),

  `version`   BIGINT,

  CONSTRAINT pk_reader_id PRIMARY KEY (`id`),
  FOREIGN KEY (`client_id`) REFERENCES `client`(`id`)
) ENGINE=InnoDB;

CREATE INDEX address_client_and_state_ind ON `address`(`client_id`, `state`);

/* User Module */