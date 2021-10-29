CREATE TABLE user_point (
	user_id BIGINT NOT NULL,
	store_id BIGINT NOT NULL,
	point INT NOT NULL,
	created_at DATETIME(6) NOT NULL,
	updated_at DATETIME(6) NOT NULL
	PRIMARY KEY (user_id, store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE user_point
ADD CONSTRAINT fk__user_point__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_point
ADD CONSTRAINT fk__user_point__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

CREATE INDEX idx__user_point__store ON user_point (store_id);
