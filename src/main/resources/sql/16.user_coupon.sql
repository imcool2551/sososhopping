CREATE TABLE  user_coupon (
    user_coupon_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    coupon_id BIGINT NOT NULL,
    used TINYINT(1) NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (user_coupon_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE user_coupon
ADD CONSTRAINT fk__user_coupon__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE user_coupon
ADD CONSTRAINT fk__user_coupon__coupon_id
FOREIGN KEY (coupon_id) REFERENCES coupon (coupon_id);
