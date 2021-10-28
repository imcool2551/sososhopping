CREATE TABLE item (
    item_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    name VARCHAR(20) NOT NULL,
    description VARCHAR(255),
    purchase_unit VARCHAR(20),
    img_url VARCHAR(255),
    price INT NOT NULL,
    sale_status TINYINT(1) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE item
ADD CONSTRAINT fk__item__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);