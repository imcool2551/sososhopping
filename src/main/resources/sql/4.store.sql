CREATE TABLE store (
    store_id BIGINT NOT NULL AUTO_INCREMENT,
    owner_id BIGINT NOT NULL,
    name VARCHAR(40) NOT NULL,
    store_type VARCHAR(20) NOT NULL,
    phone CHAR(11) NOT NULL,
    lat decimal(11, 8) NOT NULL,
    lng decimal(10, 8) NOT NULL,
    street_address VARCHAR(40) NOT NULL,
    detailed_address VARCHAR(20) NOT NULL,
    store_status VARCHAR(20) NOT NULL,
    is_open TINYINT NOT NULL,
    pickup_status TINYINT NOT NULL,
    delivery_status TINYINT NOT NULL,
    point_policy_status TINYINT NOT NULL,
    img_url VARCHAR(512),
    description TEXT,
    extra_business_day VARCHAR(255),
    minimum_order_price INTEGER,
    save_rate DECIMAL(5, 2),
    delivery_charge INTEGER,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store
ADD CONSTRAINT fk__store__owner_id
FOREIGN KEY (owner_id) REFERENCES owner (owner_id);
