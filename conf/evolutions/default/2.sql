# --- Card and Review Schema

# --- !Ups

CREATE TABLE card (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    japanese varchar(255) NOT NULL,
    furigana varchar(255) NOT NULL,
    english text NOT NULL,
    notes text NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id)
);

CREATE TABLE review (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    card_id bigint(20) NOT NULL REFERENCES card(id),
    user_id bigint(20) NOT NULL REFERENCES user(id),
    english_response varchar(255) NOT NULL,
    japanese_response varchar(255) NOT NULL,
    english_correct BOOL NOT NULL,
    japanese_correct BOOL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id)
);

# --- !Downs

DROP TABLE card;

DROP TABLE review;