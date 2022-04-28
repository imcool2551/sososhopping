CREATE TABLE user_point_log (
    user_point_log_id BIGINT NOT NULL AUTO_INCREMENT,
    user_point_id BIGINT NOT NULL,
    used INT NOT NULL,
    result INT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (user_point_log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE user_point_log
ADD CONSTRAINT fk__user_point_id
FOREIGN KEY (user_point_id) REFERENCES user_point (user_point_id);