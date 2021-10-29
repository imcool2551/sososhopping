CREATE TABLE payment (
    order_id BIGINT NOT NULL,
    pg VARCHAR(255) NOT NULL,
    pay_method VARCHAR(20) NOT NULL,
    pay_datetime DATETIME(6) NOT NULL,
    pay_amount INT NOT NULL,
    pay_ip VARCHAR(255) NOT NULL,
    receipt_url VARCHAR(512) NOT NULL,
    pay_token VARCHAR(512) NOT NULL,
    pay_status VARCHAR(20),
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL
    PRIMARY KEY (order_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE payment
ADD CONSTRAINT fk__payment__order_id
FOREIGN KEY (order_id) references orders (order_id);

ALTER TABLE payment
ADD CONSTRAINT uk__payment__pay_token
UNIQUE (pay_token);

ALTER TABLE payment
ADD CONSTRAINT uk__payment__receipt_url
UNIQUE (receipt_url);