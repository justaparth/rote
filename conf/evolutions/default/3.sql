# --- Deck schema

# --- !Ups

CREATE TABLE deck (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    name varchar(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id)
);

ALTER TABLE card
ADD COLUMN deck_id bigint(20) NOT NULL DEFAULT 1;

# --- !Downs

DROP TABLE deck;

ALTER TABLE card
DROP COLUMN deck_id;