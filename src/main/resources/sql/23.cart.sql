CREATE TABLE cart (
    cart_id BIGINT NOT NULL AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    item_id BIGINT NOT NULL,
    quantity INTEGER NOT NULL,
    created_at DATETIME(6) NOT NULL,
    updated_at DATETIME(6) NOT NULL,
    PRIMARY KEY (cart_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

ALTER TABLE cart
ADD CONSTRAINT fk__cart__user_id
FOREIGN KEY (user_id) REFERENCES users (user_id);

ALTER TABLE cart
ADD CONSTRAINT fk__cart__item_id
FOREIGN KEY (item_id) REFERENCES item (item_id);

ALTER TABLE cart
ADD CONSTRAINT uk__user_id__item_id
UNIQUE (user_id, item_id);


