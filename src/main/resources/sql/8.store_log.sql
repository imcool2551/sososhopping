CREATE TABLE store_log (
    store_log_id BIGINT NOT NULL AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    store_status VARCHAR(20) NOT NULL,
    description TEXT,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (store_log_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store_log
ADD CONSTRAINT fk__store_log__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

