CREATE TABLE store_metadata (
    store_id BIGINT NOT NULL,
    business_number CHAR(12) NOT NULL,
    representative_name VARCHAR(20) NOT NULL,
    business_name VARCHAR(20) NOT NULL,
    opening_date DATETIME(6) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store_metadata
ADD CONSTRAINT uk__store_metadata__business_number
UNIQUE (business_number);

ALTER TABLE item
ADD CONSTRAINT fk__store_metadata__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

