CREATE TABLE order_item (
    order_item_id BIGINT NOT NULL AUTO_INCREMENT,
    order_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price INT NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (order_item_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE order_item
ADD CONSTRAINT fk__order_item__item_id
FOREIGN KEY (item_id) REFERENCES item (item_id);

ALTER TABLE order_item
ADD CONSTRAINT fk__order_item__order_id
FOREIGN KEY (order_id) REFERENCES orders (order_id);

ALTER TABLE order_item
ADD CONSTRAINT uk__order_item
UNIQUE (item_id, order_id);
