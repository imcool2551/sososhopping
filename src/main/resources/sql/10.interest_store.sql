CREATE TABLE interest_store (
    user_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    primary key (user_id, store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE interest_store
ADD CONSTRAINT fk__interest_store__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE interest_store
ADD CONSTRAINT fk__interest_store__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

CREATE INDEX idx__interest_store__store_id ON interest_store (store_id);