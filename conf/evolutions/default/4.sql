# --- Deck schema

# --- !Ups

CREATE TABLE review_statistics (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    card_id bigint(20) NOT NULL REFERENCES card(id),
    user_id bigint(20) NOT NULL REFERENCES user(id),
    next_review TIMESTAMP NOT NULL,
    level tinyint NOT NULL,
    max_level tinyint NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    updated_at TIMESTAMP NOT NULL DEFAULT NOW() ON UPDATE NOW(),
    PRIMARY KEY (id),
    UNIQUE (card_id, user_id)
);

# --- !Downs

DROP TABLE review_statistics;
