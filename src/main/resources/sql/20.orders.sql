CREATE TABLE orders (
    order_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    store_id BIGINT NOT NULL,
    orderer_name VARCHAR(20) NOT NULL,
    orderer_phone CHAR(11),
    order_type VARCHAR(20) NOT NULL,
    visit_date DATETIME(6),
    delivery_charge INT,
    delivery_street_address VARCHAR(20),
    delivery_detailed_address VARCHAR(40),
    payment_type VARCHAR(20) NOT NULL,
    order_price INT NOT NULL,
    used_point INT,
    coupon_id BIGINT,
    final_price INT NOT NULL,
    order_status VARCHAR(20) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE orders
ADD CONSTRAINT fk__orders__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE orders
ADD CONSTRAINT fk__orders__store_id
FOREIGN KEY (store_id) REFERENCES store (store_id);

ALTER TABLE orders
ADD CONSTRAINT fk__orders__coupon_id
FOREIGN KEY (coupon_id) REFERENCES coupon (coupon_id);


