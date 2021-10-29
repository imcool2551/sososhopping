CREATE TABLE writing (
    writing_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    title VARCHAR(40) NOT NULL,
    content TEXT NOT NULL,
    writing_type VARCHAR(20) NOT NULL,
    img_url VARCHAR(512),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (writing_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE writing
ADD CONSTRAINT fk__writing__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);