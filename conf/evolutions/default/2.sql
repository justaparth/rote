# --- Card and Deck Schemas

# --- !Ups

CREATE TABLE deck (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    user_id bigint(20) NOT NULL,
    name varchar(255) NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE card (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    deck_id bigint(20) NOT NULL,
    japanese varchar(255) NOT NULL,
    furigana varchar(255) NOT NULL,
    english text NOT NULL,
    notes text NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id),
    FOREIGN KEY (deck_id) REFERENCES deck(id)
);

# --- !Downs

DROP TABLE deck;
DROP TABLE card;