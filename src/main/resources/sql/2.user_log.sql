CREATE TABLE user_log (
    user_log_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    active VARCHAR(20) NOT NULL,
    description TEXT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (user_log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE user_log
ADD CONSTRAINT fk__user_log__user_id
FOREIGN KEY(user_id) REFERENCES users(user_id);
