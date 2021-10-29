CREATE TABLE review (
    store_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    content TEXT,
    img_url VARCHAR(512),
    score DECIMAL(2, 1) not null,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (store_id, user_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE review
ADD CONSTRAINT fk__review__user_id
FOREIGN KEY (user_id) references users (user_id);

ALTER TABLE review
ADD CONSTRAINT fk__review__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

CREATE INDEX idx__review__user_id ON review (user_id);
