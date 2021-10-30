CREATE TABLE user_point_log (
    user_point_log_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    point_amount INT NOT NULL,
    result_amount INT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (user_point_log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE user_point_log
ADD CONSTRAINT fk__user_point_log__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_point_log
ADD CONSTRAINT fk__user_point_log__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);
