CREATE TABLE accounting (
    accounting_id BIGINT NOT NULL AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    amount INT NOT NULL,
    description TEXT,
    date DATETIME(6) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (accounting_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE accounting
ADD CONSTRAINT fk__accounting__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);
