CREATE TABLE coupon (
    coupon_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    store_name VARCHAR(20),
    coupon_name VARCHAR(20),
    stock_quantity MEDIUMINT NOT NULL,
    coupon_code CHAR(10) NOT NULL,
    minimum_order_price INT NOT NULL,
    start_date DATETIME(6) NOT NULL,
    due_date DATETIME(6) NOT NULL,
    coupon_type VARCHAR(20) NOT NULL,
    fix_amount INT,
    rate_amount DECIMAL(5, 2),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (coupon_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE coupon 
ADD CONSTRAINT fk__coupon__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

ALTER TABLE coupon 
ADD CONSTRAINT uk__coupon__coupon_code
UNIQUE (coupon_code);