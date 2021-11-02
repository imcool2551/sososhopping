CREATE TABLE store (
    store_id BIGINT NOT NULL AUTO_INCREMENT,
    owner_id BIGINT NOT NULL,
    store_type VARCHAR(20),
    name VARCHAR(40) NOT NULL,
    img_url VARCHAR(512),
    description TEXT,
    extra_business_day VARCHAR(255),
    phone CHAR(11) NOT NULL,
    location POINT NOT NULL SRID 0, // MySQL 8.0 이상부터 필요
    business_status TINYINT(1) NOT NULL,
    store_status VARCHAR(20) NOT NULL,
    local_currency_status TINYINT(1) NOT NULL,
    pickup_status TINYINT(1) NOT NULL,
    delivery_status TINYINT(1) NOT NULL,
    point_policy_status TINYINT(1) NOT NULL,
    minimum_order_price INT,
    save_rate DECIMAL(5, 2),
    street_address VARCHAR(40) NOT NULL,
    detailed_address VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (store_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE store
ADD CONSTRAINT fk__store__owner_id
FOREIGN KEY (owner_id) REFERENCES owner (owner_id);

CREATE SPATIAL INDEX idx__store__location ON store (location);
