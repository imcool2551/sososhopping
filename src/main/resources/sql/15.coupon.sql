CREATE TABLE coupon (
    coupon_id BIGINT NOT NULL AUTO_INCREMENT,
    store_id BIGINT NOT NULL,
    coupon_name VARCHAR(20),
    stock_quantity INT NOT NULL,
    coupon_code CHAR(10) NOT NULL,
    minimum_order_price INT NOT NULL,
    issue_start_date DATETIME(6) NOT NULL,
    issue_due_date DATETIME(6) NOT NULL,
    expire_date DATETIME(6) NOT NULL,
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