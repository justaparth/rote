# --- Review Schema

# --- !Ups

CREATE TABLE review (
    id bigint(20) NOT NULL AUTO_INCREMENT,
    card_id bigint(20) NOT NULL,
    user_id bigint(20) NOT NULL,
    english_response varchar(255) NOT NULL,
    japanese_response varchar(255) NOT NULL,
    english_correct BOOL NOT NULL,
    japanese_correct BOOL NOT NULL,
    created_at TIMESTAMP NOT NULL DEFAULT NOW(),
    PRIMARY KEY (id),
    FOREIGN KEY (card_id) REFERENCES card(id),
    FOREIGN KEY (user_id) REFERENCES user(id)
);


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
    FOREIGN KEY (card_id) REFERENCES card(id),
    FOREIGN KEY (user_id) REFERENCES user(id),
    UNIQUE (card_id, user_id)
);

# --- !Downs

DROP TABLE review;
DROP TABLE review_statistics;